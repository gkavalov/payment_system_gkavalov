package gkavalov.emerchantpay.payment.system.model.entity.transaction;

import gkavalov.emerchantpay.payment.system.exception.InvalidTotalSumException;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.RefundTransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
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
import java.time.ZonedDateTime;
import java.util.UUID;

import static gkavalov.emerchantpay.payment.system.validation.TransactionSumValidator.validateTotalTransactionSum;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefundTransaction extends Transaction {

    @Column(name = "reversed_amount")
    private BigDecimal reversedAmount;

    public RefundTransaction(final UUID uuid, final ZonedDateTime timestamp, final BigDecimal amount, final TransactionStatus status,
                             final String customerEmail, final String customerPhone, final String referenceId,
                             final ChargeTransaction belongsTo, final Merchant merchant, final BigDecimal reversedAmount) {
        super(uuid, timestamp, amount, status, customerEmail, customerPhone, referenceId, belongsTo, merchant);
        this.reversedAmount = reversedAmount;
    }

    public RefundTransaction(final RefundTransactionDto refund) {
        this(null, refund.getTimestamp(), refund.getAmount(), refund.getStatus(), refund.getCustomerEmail(), refund.getCustomerPhone(),
                refund.getReferenceId(), new ChargeTransaction(), new Merchant(),
                refund.getReversedAmount());
    }

    @PostPersist
    private void updateTransactionAndPropagateSum() throws InvalidTotalSumException {
        setStatus(TransactionStatus.REFUNDED);
        validateTotalTransactionSum(getMerchant());
    }
}
