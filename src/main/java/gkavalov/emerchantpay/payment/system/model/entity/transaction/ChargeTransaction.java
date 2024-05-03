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

import static gkavalov.emerchantpay.payment.system.validation.TransactionSumValidator.validateTotalTransactionSum;

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
                chargeDto.getReferenceId(), new AuthorizeTransaction(), new Merchant(),
                chargeDto.getApprovedAmount());
    }

    @PostPersist
    public void validateTotalSum() throws InvalidTotalSumException {
        validateTotalTransactionSum(getMerchant());
    }
}
