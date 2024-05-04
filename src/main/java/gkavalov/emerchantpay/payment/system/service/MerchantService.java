package gkavalov.emerchantpay.payment.system.service;

import gkavalov.emerchantpay.payment.system.exception.ActiveMerchantException;
import gkavalov.emerchantpay.payment.system.exception.CorruptCsvFileException;
import gkavalov.emerchantpay.payment.system.exception.InactiveMerchantException;
import gkavalov.emerchantpay.payment.system.model.dto.CreateUpdateMerchantDto;
import gkavalov.emerchantpay.payment.system.model.dto.MerchantDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;

import java.io.InputStream;
import java.util.Set;

public interface MerchantService {

    Set<Merchant> getAllMerchants();

    Merchant getMerchant(final Long id);

    Merchant createMerchant(final MerchantDto merchant);

    Merchant isMerchantActive(final Long id) throws InactiveMerchantException;

    void deleteMerchant(final Long id) throws ActiveMerchantException;

    Merchant updateMerchant(final Long id, final CreateUpdateMerchantDto merchant);

    Merchant updateMerchant(final Merchant merchant);

    long bulkImport(final InputStream inputStream) throws CorruptCsvFileException;
}
