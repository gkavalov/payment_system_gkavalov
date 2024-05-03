package gkavalov.emerchantpay.payment.system.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import gkavalov.emerchantpay.payment.system.model.entity.MerchantStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantDto extends CreateUpdateMerchantDto {

    public MerchantDto(final String name, final String description, final String email, final MerchantStatus status,
                       final BigDecimal totalTransactionSum, final Set<TransactionDto> transactions) {
        super(name, description, email, status, totalTransactionSum);
        this.transactions = transactions;
    }

    public MerchantDto(final JsonNode node) {
        setName(node.has("name") ? node.get("name").asText() : null);
        setDescription(node.has("description") ? node.get("description").asText() : null);
        setEmail(node.has("email") ? node.get("email").asText() : null);
        setStatus(node.has("status") ? MerchantStatus.valueOf(node.get("status").asText()) : null);
        setTotalTransactionSum(node.has("totalTransactionSum") ? node.get("totalTransactionSum").decimalValue() : null);
        setTransactions(null);
    }

    private Set<TransactionDto> transactions = new HashSet<>();
}
