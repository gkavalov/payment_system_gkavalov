package gkavalov.emerchantpay.payment.system;

import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.MerchantStatus;
import gkavalov.emerchantpay.payment.system.model.entity.TransactionStatus;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.AuthorizeTransaction;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.ChargeTransaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestConstants {

    public static final Merchant MOCK_MERCHANT_1 = new Merchant(1L, "test-merchant-1-name",
            "test-merchant-1-description", "test@merchant.one", MerchantStatus.ACTIVE,
            new BigDecimal("1.0"), new HashSet<>());

    public static final Merchant MOCK_MERCHANT_2 = new Merchant(2L, "test-merchant-2-name",
            "test-merchant-2-description", "test@merchant.two", MerchantStatus.INACTIVE,
            new BigDecimal("1.0"), new HashSet<>());

    public static final AuthorizeTransaction MOCK_TRANSACTION_1 =
            new AuthorizeTransaction(UUID.randomUUID(), null, new BigDecimal("1.0"), TransactionStatus.APPROVED,
                    "test@customer.email", "0123456789", UUID.randomUUID().toString(),
                    MOCK_MERCHANT_1, new BigDecimal("1.0"));

    public static final ChargeTransaction MOCK_TRANSACTION_2 =
            new ChargeTransaction(UUID.randomUUID(), null, new BigDecimal("1.0"), TransactionStatus.APPROVED,
                    "test@customer.email", "0123456789", UUID.randomUUID().toString(), MOCK_TRANSACTION_1,
                    MOCK_MERCHANT_1, new BigDecimal("1.0"));

    static {
        MOCK_MERCHANT_1.getTransactions().add(MOCK_TRANSACTION_1);
    }
}
