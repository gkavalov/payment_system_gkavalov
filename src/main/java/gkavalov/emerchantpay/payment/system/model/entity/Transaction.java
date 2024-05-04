package gkavalov.emerchantpay.payment.system.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @CreationTimestamp
    @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm:ssXXX")
    private ZonedDateTime timestamp = ZonedDateTime.now();

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Email
    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_phone")
    @Pattern(regexp = "\\d{10,11}")
    private String customerPhone;

    @Column(name = "reference_id", unique = true)
    private String referenceId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "belongs_to", unique = true)
    private Transaction belongsTo;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Merchant merchant;
}
