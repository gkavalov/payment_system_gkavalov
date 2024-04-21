package gkavalov.emerchantpay.payment.system.service;

import gkavalov.emerchantpay.payment.system.exception.InactiveMerchantException;
import gkavalov.emerchantpay.payment.system.model.dto.MerchantDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;

import java.util.Set;

public interface MerchantService {

    Set<Merchant> getAllMerchants();

    Merchant getMerchant(final Long id);

    Merchant createMerchant(final MerchantDto merchant);

    Merchant isMerchantActive(final Long id) throws InactiveMerchantException;

    void deleteMerchant(final Long id);

    void updateMerchant(final Merchant merchant);
}
