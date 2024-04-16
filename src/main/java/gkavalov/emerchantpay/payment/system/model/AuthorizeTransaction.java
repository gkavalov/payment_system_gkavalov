package gkavalov.emerchantpay.payment.system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class AuthorizeTransaction extends Transaction {

    @Column(name = "customer_amount")
    private BigDecimal customerAmount;
}
