package gkavalov.emerchantpay.payment.system.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantDto extends UpdateMerchantDto {

    public MerchantDto(final String name, final String description, final String email, final MerchantStatus status,
                       final BigDecimal totalTransactionSum, final Set<TransactionDto> transactions) {
        super(name, description, email, status, totalTransactionSum);
        this.transactions = transactions;
    }

    private Set<TransactionDto> transactions;
}
