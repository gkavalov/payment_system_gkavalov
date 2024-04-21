package gkavalov.emerchantpay.payment.system.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import gkavalov.emerchantpay.payment.system.model.entity.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class TransactionDto {

    @Positive
    @NotNull
    private BigDecimal amount;

    private TransactionStatus status;

    @Email
    private String customerEmail;

    private String customerPhone;

    private String referenceId;

    private TransactionDto belongsTo;

    private MerchantDto merchant;
}
