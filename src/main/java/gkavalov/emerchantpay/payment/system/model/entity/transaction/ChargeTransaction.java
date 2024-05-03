package gkavalov.emerchantpay.payment.system.model.entity.transaction;

import gkavalov.emerchantpay.payment.system.exception.InvalidTotalSumException;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.ChargeTransactionDto;
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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargeTransaction extends Transaction {

    @Column(name = "approved_amount")
    private BigDecimal approvedAmount;


    public ChargeTransaction(final UUID uuid, final ZonedDateTime timestamp, final BigDecimal amount, final TransactionStatus status,
                             final String customerEmail, final String customerPhone, final String referenceId,
                             final AuthorizeTransaction belongsTo, final Merchant merchant, final BigDecimal approvedAmount) {
        super(uuid, timestamp, amount, status, customerEmail, customerPhone, referenceId, belongsTo, merchant);
        this.approvedAmount = approvedAmount;
    }

    public ChargeTransaction(final ChargeTransactionDto chargeDto) {
        this(null, chargeDto.getTimestamp(), chargeDto.getAmount(), chargeDto.getStatus(), chargeDto.getCustomerEmail(), chargeDto.getCustomerPhone(),
                chargeDto.getReferenceId(),
                // TODO Map these correctly
                new AuthorizeTransaction(), new Merchant(),
                chargeDto.getApprovedAmount());
    }

    @PostPersist
    public void validateTotalSum() throws InvalidTotalSumException {
        // TODO Factor out buisness logic away from model
        BigDecimal expectedTotalTransactionSum = getMerchant().getTotalTransactionSum();
        BigDecimal actualChargedSum = getMerchant().getTransactions()
                .stream()
                .filter(ChargeTransaction.class::isInstance)
                .map(t -> ((ChargeTransaction) t).getApprovedAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (!expectedTotalTransactionSum.stripTrailingZeros().equals(actualChargedSum.stripTrailingZeros())) {
            throw new InvalidTotalSumException(getMerchant().getId());
        }
    }
}
