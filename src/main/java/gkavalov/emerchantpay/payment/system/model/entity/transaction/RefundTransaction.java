package gkavalov.emerchantpay.payment.system.model.entity.transaction;

import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import gkavalov.emerchantpay.payment.system.model.entity.TransactionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PostPersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefundTransaction extends Transaction {

    @Column(name = "reversed_amount")
    private BigDecimal reversedAmount;

    @PostPersist
    private void updateTransactionAndPropagateSum() {
        setStatus(TransactionStatus.REFUNDED);

        // TODO decrease the merchant's total transaction amount
    }
}
