package gkavalov.emerchantpay.payment.system.service.impl;

import gkavalov.emerchantpay.payment.system.exception.InactiveMerchant;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.repository.MerchantRepository;
import gkavalov.emerchantpay.payment.system.service.MerchantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static gkavalov.emerchantpay.payment.system.model.entity.MerchantStatus.ACTIVE;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;

    public Set<Merchant> getAllMerchants() {
        return Streamable.of(merchantRepository.findAll()).toSet();
    }

    public Merchant getMerchant(final Long id) {
        return merchantRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Merchant with id %d not found".formatted(id)));
    }

    public void deleteMerchant(final Long id) {
        merchantRepository.delete(getMerchant(id));
    }

    public Merchant isMerchantActive(Long id) throws InactiveMerchant {
        return Optional.of(getMerchant(id))
                .filter(merchant -> ACTIVE.equals(merchant.getStatus()))
                .orElseThrow(() -> new InactiveMerchant(id));
    }
}
