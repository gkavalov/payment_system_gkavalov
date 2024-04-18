package gkavalov.emerchantpay.payment.system.mapper;

import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Set;

import static org.mapstruct.NullValuePropertyMappingStrategy.SET_TO_DEFAULT;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = SET_TO_DEFAULT)
public interface TransactionMapper extends TransactionsObjectFactory {

    Set<TransactionDto> toDto(final Set<Transaction> transactions);

    @Mapping(target = "merchant", ignore = true)
    TransactionDto toDto(final Transaction transaction);

    @Mapping(target = "status", source = "transactionDto.status")
    @Mapping(target = "merchant", source = "merchant")
    Transaction toEntityWithMerchant(final TransactionDto transactionDto, final Merchant merchant);
}
