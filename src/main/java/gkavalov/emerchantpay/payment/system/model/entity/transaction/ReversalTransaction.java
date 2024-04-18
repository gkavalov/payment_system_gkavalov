package gkavalov.emerchantpay.payment.system.model.entity.transaction;

import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import gkavalov.emerchantpay.payment.system.model.entity.TransactionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.PostPersist;

@Entity
public class ReversalTransaction extends Transaction {

    @PostPersist
    private void updateTransactionAndPropagateSum() {
        setStatus(TransactionStatus.REVERSED);
    }
}
