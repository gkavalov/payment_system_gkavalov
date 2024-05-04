package gkavalov.emerchantpay.payment.system.model.entity.transaction;

import gkavalov.emerchantpay.payment.system.model.dto.transaction.AuthorizeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import gkavalov.emerchantpay.payment.system.model.entity.TransactionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizeTransaction extends Transaction {

    @Column(name = "customer_amount")
    @Positive
    private BigDecimal customerAmount;

    public AuthorizeTransaction(final UUID uuid, final ZonedDateTime timestamp, final TransactionStatus status,
                                final String customerEmail, final String customerPhone, final String referenceId,
                                final Merchant merchant, final BigDecimal customerAmount) {
        super(uuid, timestamp, status, customerEmail, customerPhone, referenceId, null, merchant);
        this.customerAmount = customerAmount;
    }

    public AuthorizeTransaction(final AuthorizeTransactionDto authDto) {
        this(null, authDto.getTimestamp(), authDto.getStatus(), authDto.getCustomerEmail(), authDto.getCustomerPhone(),
                authDto.getReferenceId(), new Merchant(),
                authDto.getCustomerAmount());
    }
}
