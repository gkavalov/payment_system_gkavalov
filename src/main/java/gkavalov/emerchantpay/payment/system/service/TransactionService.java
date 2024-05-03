package gkavalov.emerchantpay.payment.system.service;

import gkavalov.emerchantpay.payment.system.exception.InactiveMerchantException;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.AuthorizeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;

import java.util.Set;
import java.util.UUID;

public interface TransactionService {

    Set<Transaction> getAllTransactions();

    Transaction getTransaction(final UUID uuid);

    Transaction createTransactionForMerchant(final AuthorizeTransactionDto transactionDto, final Merchant merchant);

    Transaction referTransaction(final UUID uuid, final TransactionDto transactionDto) throws InactiveMerchantException;

    long deleteOldTransactions(final int minutesLimit);
}
