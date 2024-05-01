package gkavalov.emerchantpay.payment.system.service.impl;

import gkavalov.emerchantpay.payment.system.exception.InactiveMerchantException;
import gkavalov.emerchantpay.payment.system.mapper.MerchantMapper;
import gkavalov.emerchantpay.payment.system.model.dto.CreateUpdateMerchantDto;
import gkavalov.emerchantpay.payment.system.model.dto.MerchantDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.repository.MerchantRepository;
import gkavalov.emerchantpay.payment.system.service.MerchantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Optional;
import java.util.Set;

import static gkavalov.emerchantpay.payment.system.model.entity.MerchantStatus.ACTIVE;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    private final MerchantMapper merchantMapper;

    @Override
    public Set<Merchant> getAllMerchants() {
        return Streamable.of(merchantRepository.findAll()).toSet();
    }

    @Override
    public Merchant getMerchant(final Long id) {
        return merchantRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Merchant with id %d not found".formatted(id)));
    }

    @Override
    public Merchant createMerchant(final MerchantDto merchant) {
        return updateMerchant(merchantMapper.toEntity(merchant));
    }

    @Override
    public Merchant updateMerchant(final Long id, final CreateUpdateMerchantDto updatedMerchant) {
        final Merchant merchant = getMerchant(id);
        merchantMapper.updateMerchant(merchant, updatedMerchant);
        return updateMerchant(merchant);
    }

    @Override
    public Merchant updateMerchant(final Merchant updatedMerchant) {
        return merchantRepository.save(updatedMerchant);
    }

    @Override
    public long bulkImport(final InputStream inputStream) {
        return 0;
    }

    @Override
    public void deleteMerchant(final Long id) {
        merchantRepository.delete(getMerchant(id));
    }


    @Override
    public Merchant isMerchantActive(Long id) throws InactiveMerchantException {
        return Optional.of(getMerchant(id))
                .filter(merchant -> ACTIVE.equals(merchant.getStatus()))
                .orElseThrow(() -> new InactiveMerchantException(id));
    }
}
