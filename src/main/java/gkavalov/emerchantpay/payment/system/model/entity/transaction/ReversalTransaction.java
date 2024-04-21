package gkavalov.emerchantpay.payment.system.model.entity.transaction;

import gkavalov.emerchantpay.payment.system.model.dto.transaction.ReversalTransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import gkavalov.emerchantpay.payment.system.model.entity.TransactionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.PostPersist;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class ReversalTransaction extends Transaction {

    public ReversalTransaction(final UUID uuid, final BigDecimal amount, final TransactionStatus status,
                               final String customerEmail, final String customerPhone, final String referenceId,
                               final AuthorizeTransaction belongsTo, final Merchant merchant) {
        super(uuid, amount, status, customerEmail, customerPhone, referenceId, belongsTo, merchant);
    }

    public ReversalTransaction(final ReversalTransactionDto reversal) {
        this(null, reversal.getAmount(), reversal.getStatus(), reversal.getCustomerEmail(), reversal.getCustomerPhone(),
                reversal.getReferenceId(),
                // TODO Map these correctly
                new AuthorizeTransaction(), new Merchant());
    }

    @PostPersist
    private void updateTransactionAndPropagateSum() {
        setStatus(TransactionStatus.REVERSED);
    }
}
