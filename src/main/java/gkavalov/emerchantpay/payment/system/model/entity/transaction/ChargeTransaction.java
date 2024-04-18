package gkavalov.emerchantpay.payment.system.model.entity.transaction;

import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import gkavalov.emerchantpay.payment.system.model.entity.TransactionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargeTransaction extends Transaction {

    @Column(name = "approved_amount")
    private BigDecimal approvedAmount;


    public ChargeTransaction(final UUID uuid, final BigDecimal amount, final TransactionStatus status,
                             final String customerEmail, final String customerPhone, final String referenceId,
                             final Transaction belongsTo, final Merchant merchant, final BigDecimal approvedAmount) {
        super(uuid, amount, status, customerEmail, customerPhone, referenceId, belongsTo, merchant);
        this.approvedAmount = approvedAmount;
    }

    // TODO validate the merchant's total transactions amount equal the sum of the approved Charge transactions
}
