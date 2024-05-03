package gkavalov.emerchantpay.payment.system.service;

import gkavalov.emerchantpay.payment.system.exception.InactiveMerchantException;
import gkavalov.emerchantpay.payment.system.exception.NonPayableTransactionException;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.ChargeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.ChargeTransaction;

import java.util.Set;
import java.util.UUID;

public interface TransactionService {

    Set<Transaction> getAllTransactions();

    Transaction getTransaction(final UUID uuid);

    Transaction createTransactionForMerchant(final TransactionDto transactionDto, final Merchant merchant);

    ChargeTransaction paymentForTransaction(final UUID uuid, final ChargeTransactionDto transactionDto) throws NonPayableTransactionException, InactiveMerchantException;

    long deleteOldTransactions(final int minutesLimit);
}
