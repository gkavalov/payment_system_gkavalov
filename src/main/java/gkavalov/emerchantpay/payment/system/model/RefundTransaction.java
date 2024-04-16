package gkavalov.emerchantpay.payment.system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PostPersist;

import java.math.BigDecimal;

@Entity
public class RefundTransaction extends Transaction {

    @Column(name = "reversed_amount")
    private BigDecimal reversedAmount;

    @PostPersist
    private void updateTransactionAndPropagateSum() {
        setStatus(TransactionStatus.REFUNDED);

        // TODO decrease the merchant's total transaction amount
    }
}
