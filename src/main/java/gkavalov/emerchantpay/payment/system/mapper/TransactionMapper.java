package gkavalov.emerchantpay.payment.system.mapper;

import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

import static org.mapstruct.NullValuePropertyMappingStrategy.SET_TO_DEFAULT;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = SET_TO_DEFAULT)
public interface TransactionMapper extends TransactionsFactory {

    default Set<TransactionDto> toNestedDto(final Set<Transaction> transactions) {
        return transactions
                .stream()
                .map(this::toNestedDto)
                .collect(Collectors.toSet());
    }

    @Named("toNestedDto")
    @Mapping(target = "merchant", ignore = true)
    @Mapping(target = "belongsTo", qualifiedByName = "toNestedDto")
    TransactionDto toNestedDto(final Transaction transaction);

    default Set<TransactionDto> toTopLevelDto(final Set<Transaction> transactions) {
        return transactions
                .stream()
                .map(this::toTopLevelDto)
                .collect(Collectors.toSet());
    }

    @Named("toTopLevelDto")
    @Mapping(target = "merchant.transactions", ignore = true)
    @Mapping(target = "belongsTo", qualifiedByName = "toTopLevelDto")
    TransactionDto toTopLevelDto(final Transaction transaction);

    @Mapping(target = "merchant", ignore = true)
    Transaction toEntity(final TransactionDto transactionDto);

    @Mapping(target = "status", source = "transactionDto.status")
    @Mapping(target = "merchant", source = "merchant")
    Transaction toEntityWithMerchant(final TransactionDto transactionDto, final Merchant merchant);
}
