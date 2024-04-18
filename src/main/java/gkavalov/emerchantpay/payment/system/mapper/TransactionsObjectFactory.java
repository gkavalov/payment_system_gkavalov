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
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;

@Mapper
public interface TransactionsObjectFactory {

    @ObjectFactory
    default <T extends TransactionDto> T toTransactionDto(final Transaction transaction,
                                                          @TargetType final Class<T> type) {

        return switch (transaction) {
            case AuthorizeTransaction at -> type.cast(new AuthorizeTransactionDto(at.getCustomerAmount()));
            case ChargeTransaction ct -> type.cast(new ChargeTransactionDto(ct.getApprovedAmount()));
            case RefundTransaction rt -> type.cast(new RefundTransactionDto(rt.getReversedAmount()));
            case ReversalTransaction ct -> type.cast(new ReversalTransactionDto());
            case null, default -> null;
        };
    }

    @ObjectFactory
    default <T extends Transaction> T toTransactionEntity(final TransactionDto transactionDto,
                                                          @TargetType final Class<T> type) {
        return switch (transactionDto) {
            case AuthorizeTransactionDto at -> type.cast(new AuthorizeTransaction(at.getCustomerAmount()));
            case ChargeTransactionDto ct -> type.cast(new ChargeTransaction(ct.getApprovedAmount()));
            case RefundTransactionDto rt -> type.cast(new RefundTransaction(rt.getReversedAmount()));
            case ReversalTransactionDto ct -> type.cast(new ReversalTransaction());
            case null, default -> null;
        };
    }
}
