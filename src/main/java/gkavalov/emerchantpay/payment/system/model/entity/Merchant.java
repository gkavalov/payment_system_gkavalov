package gkavalov.emerchantpay.payment.system.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String email;

    @Enumerated(EnumType.STRING)
    private MerchantStatus status;

    @Column(name = "total_transaction_sum")
    private BigDecimal totalTransactionSum;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "merchant")
    private Set<Transaction> transactions = new HashSet<>();
}
