package gkavalov.emerchantpay.payment.system.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.opencsv.bean.CsvBindByName;
import gkavalov.emerchantpay.payment.system.model.entity.MerchantStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUpdateMerchantDto {

    @CsvBindByName
    @Size(max = 200)
    private String name;

    @CsvBindByName
    @Size(max = 200)
    private String description;

    @CsvBindByName
    @Email
    private String email;

    @CsvBindByName
    private MerchantStatus status;

    @CsvBindByName
    private BigDecimal totalTransactionSum = new BigDecimal("0.0");
}
