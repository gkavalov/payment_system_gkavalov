package gkavalov.emerchantpay.payment.system.model.dto;

import gkavalov.emerchantpay.payment.system.model.entity.MerchantStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MerchantDto {

    private String name;

    private String description;

    private String email;

    private MerchantStatus status;

    private BigDecimal totalTransactionSum;

    private Set<TransactionDto> transactions;
}
