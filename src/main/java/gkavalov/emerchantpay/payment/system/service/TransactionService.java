package gkavalov.emerchantpay.payment.system.service;

import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;

import java.util.Set;

public interface TransactionService {

    Set<Transaction> getAllTransactions();

    void createTransactionForMerchant(final TransactionDto transactionDto, final Merchant merchant);
}
