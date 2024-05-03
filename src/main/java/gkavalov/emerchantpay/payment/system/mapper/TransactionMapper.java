package gkavalov.emerchantpay.payment.system.mapper;

import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.AuthorizeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.ChargeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.RefundTransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.ReversalTransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.AuthorizeTransaction;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.ChargeTransaction;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.RefundTransaction;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.ReversalTransaction;
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

    default Set<TransactionDto> toDto(final Set<Transaction> transactions) {
        return transactions
                .stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    @Named("toDto")
    @Mapping(target = "merchant.transactions", ignore = true)
    @Mapping(target = "belongsTo", qualifiedByName = "toDto")
    TransactionDto toDto(final Transaction transaction);

    @Mapping(target = "merchant", ignore = true)
    Transaction toEntity(final TransactionDto transactionDto);

    @Mapping(target = "status", source = "transactionDto.status")
    @Mapping(target = "merchant", source = "merchant")
    AuthorizeTransaction toEntityWithMerchant(final AuthorizeTransactionDto transactionDto, final Merchant merchant);

    @Mapping(target = "status", source = "transactionDto.status")
    @Mapping(target = "merchant", source = "merchant")
    ChargeTransaction toEntityWithMerchant(final ChargeTransactionDto transactionDto, final Merchant merchant);

    @Mapping(target = "status", source = "transactionDto.status")
    @Mapping(target = "merchant", source = "merchant")
    RefundTransaction toEntityWithMerchant(final RefundTransactionDto transactionDto, final Merchant merchant);

    @Mapping(target = "status", source = "transactionDto.status")
    @Mapping(target = "merchant", source = "merchant")
    ReversalTransaction toEntityWithMerchant(final ReversalTransactionDto transactionDto, final Merchant merchant);
}
