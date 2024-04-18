package gkavalov.emerchantpay.payment.system.service;

import gkavalov.emerchantpay.payment.system.exception.InactiveMerchant;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;

import java.util.Set;

public interface MerchantService {

    Set<Merchant> getAllMerchants();

    Merchant getMerchant(final Long id);

    void deleteMerchant(final Long id);

    Merchant isMerchantActive(Long id) throws InactiveMerchant;
}
