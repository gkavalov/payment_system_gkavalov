package gkavalov.emerchantpay.payment.system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PostPersist;

@Entity
public class ReversalTransaction extends Transaction {

    @PostPersist
    private void updateTransactionAndPropagateSum() {
        setStatus(TransactionStatus.REVERSED);
    }
}
