package gkavalov.emerchantpay.payment.system.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import gkavalov.emerchantpay.payment.system.model.entity.MerchantStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUpdateMerchantDto {

    private String name;

    private String description;

    private String email;

    private MerchantStatus status;

    private BigDecimal totalTransactionSum;
}
