package gkavalov.emerchantpay.payment.system.service.impl;

import gkavalov.emerchantpay.payment.system.exception.InactiveMerchantException;
import gkavalov.emerchantpay.payment.system.exception.NonPayableTransactionException;
import gkavalov.emerchantpay.payment.system.mapper.TransactionMapper;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.ChargeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.AuthorizeTransaction;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.ChargeTransaction;
import gkavalov.emerchantpay.payment.system.repository.TransactionRepository;
import gkavalov.emerchantpay.payment.system.service.MerchantService;
import gkavalov.emerchantpay.payment.system.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TransactionServiceImpl implements TransactionService {

    // TODO Only approved or refunded transactions can be referenced,
    // otherwise the status of the submitted transaction will be created with
    // error status

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final MerchantService merchantService;

    public Set<Transaction> getAllTransactions() {
        return Streamable.of(transactionRepository.findAll()).toSet();
    }

    @Override
    public Transaction getTransaction(UUID uuid) {
        return transactionRepository
                .findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Transaction with id %s not found".formatted(uuid)));
    }

    @Override
    public Transaction createTransactionForMerchant(final TransactionDto transactionDto, final Merchant merchant) {
        return transactionRepository.save(transactionMapper.toEntityWithMerchant(transactionDto, merchant));
    }

    @Override
    public ChargeTransaction paymentForTransaction(final UUID uuid, final ChargeTransactionDto transactionDto)
            throws NonPayableTransactionException, InactiveMerchantException {
        final Transaction transaction = getTransaction(uuid);
        final Merchant merchant = transaction.getMerchant();

        merchantService.isMerchantActive(merchant.getId());
        if (transaction instanceof AuthorizeTransaction) {
            final ChargeTransaction transactionForMerchant = (ChargeTransaction) createTransactionForMerchant(transactionDto, merchant);
            merchant.setTotalTransactionSum(merchant.getTotalTransactionSum().add(transactionForMerchant.getApprovedAmount()));
            merchantService.updateMerchant(merchant);
            return transactionForMerchant;
        } else {
            throw new NonPayableTransactionException(uuid.toString(), transaction.getClass().getSimpleName());
        }
    }
}
