package gkavalov.emerchantpay.payment.system.mapper;

import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.AuthorizeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.ChargeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.RefundTransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.ReversalTransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.AuthorizeTransaction;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.ChargeTransaction;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.RefundTransaction;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.ReversalTransaction;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;

public interface TransactionsFactory {

    @ObjectFactory
    default <T extends TransactionDto> T toTransactionDto(final Transaction transaction,
                                                         @TargetType final Class<T> type) {

        return switch (transaction) {
            case AuthorizeTransaction at -> type.cast(new AuthorizeTransactionDto(at));
            case ChargeTransaction ct -> type.cast(new ChargeTransactionDto(ct));
            case RefundTransaction rt -> type.cast(new RefundTransactionDto(rt));
            case ReversalTransaction ret -> type.cast(new ReversalTransactionDto(ret));
            case null, default -> null;
        };
    }

    @ObjectFactory
    default <T extends Transaction> T toTransactionEntity(final TransactionDto transactionDto,
                                                         @TargetType final Class<T> type) {
        return switch (transactionDto) {
            case AuthorizeTransactionDto at -> type.cast(new AuthorizeTransaction(at));
            case ChargeTransactionDto ct -> type.cast(new ChargeTransaction(ct));
            case RefundTransactionDto rt -> type.cast(new RefundTransaction(rt));
            case ReversalTransactionDto ret -> type.cast(new ReversalTransaction(ret));
            case null, default -> null;
        };
    }
}
