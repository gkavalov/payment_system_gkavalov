package gkavalov.emerchantpay.payment.system.model.entity.transaction;

import gkavalov.emerchantpay.payment.system.model.dto.transaction.ReversalTransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import gkavalov.emerchantpay.payment.system.model.entity.TransactionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.PostPersist;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
public class ReversalTransaction extends Transaction {

    public ReversalTransaction(final UUID uuid, final ZonedDateTime timestamp, final TransactionStatus status,
                               final String customerEmail, final String customerPhone, final String referenceId,
                               final AuthorizeTransaction belongsTo, final Merchant merchant) {
        super(uuid, timestamp, status, customerEmail, customerPhone, referenceId, belongsTo, merchant);
    }

    public ReversalTransaction(final ReversalTransactionDto reversal) {
        this(null, reversal.getTimestamp(), reversal.getStatus(), reversal.getCustomerEmail(), reversal.getCustomerPhone(),
                reversal.getReferenceId(), new AuthorizeTransaction(), new Merchant());
    }

    @PostPersist
    private void updateTransactionAndPropagateSum() {
        setStatus(TransactionStatus.REVERSED);
    }
}
