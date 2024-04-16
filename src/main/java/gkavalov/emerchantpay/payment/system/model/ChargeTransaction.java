package gkavalov.emerchantpay.payment.system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class ChargeTransaction extends Transaction {

    @Column(name = "approved_amount")
    private BigDecimal approvedAmount;

    // TODO validate the merchant's total transactions amount equal the sum of the approved Charge transactions
}
