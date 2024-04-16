package gkavalov.emerchantpay.payment.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Positive
    @NotNull
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Email
    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "reference_id")
    private String referenceId;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "belongs_to")
    private Transaction belongsTo;

    @ManyToOne
    private Merchant merchant;
}
