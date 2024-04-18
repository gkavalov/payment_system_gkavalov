package gkavalov.emerchantpay.payment.system.service.impl;

import gkavalov.emerchantpay.payment.system.mapper.TransactionMapper;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import gkavalov.emerchantpay.payment.system.repository.TransactionRepository;
import gkavalov.emerchantpay.payment.system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TransactionServiceImpl implements TransactionService {

    // TODO Only approved or refunded transactions can be referenced,
    // otherwise the status of the submitted transaction will be created with
    // error status

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public Set<Transaction> getAllTransactions() {
        return Streamable.of(transactionRepository.findAll()).toSet();
    }

    public void createTransactionForMerchant(final TransactionDto transactionDto, final Merchant merchant) {
        transactionRepository.save(transactionMapper.toEntityWithMerchant(transactionDto, merchant));
    }
}
