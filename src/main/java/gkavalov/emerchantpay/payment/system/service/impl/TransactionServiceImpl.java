package gkavalov.emerchantpay.payment.system.service.impl;

import gkavalov.emerchantpay.payment.system.exception.InactiveMerchantException;
import gkavalov.emerchantpay.payment.system.mapper.TransactionMapper;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.AuthorizeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.ChargeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.RefundTransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.ReversalTransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import gkavalov.emerchantpay.payment.system.model.entity.TransactionStatus;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.AuthorizeTransaction;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.ChargeTransaction;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.RefundTransaction;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.ReversalTransaction;
import gkavalov.emerchantpay.payment.system.repository.TransactionRepository;
import gkavalov.emerchantpay.payment.system.service.MerchantService;
import gkavalov.emerchantpay.payment.system.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final MerchantService merchantService;

    public Set<Transaction> getAllTransactions() {
        return Streamable.of(transactionRepository.findAll()).toSet();
    }

    @Override
    public Transaction getTransaction(final UUID uuid) {
        return transactionRepository
                .findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Transaction with id %s not found".formatted(uuid)));
    }

    @Override
    public Transaction createTransactionForMerchant(final AuthorizeTransactionDto transactionDto, final Merchant merchant) {
        return transactionRepository.save(transactionMapper.toEntityWithMerchant(transactionDto, merchant));
    }

    @Override
    @Transactional
    public Transaction referTransaction(final UUID uuid, final TransactionDto transactionDto)
            throws InactiveMerchantException {
        Transaction transaction = getTransaction(uuid);
        final Merchant merchant = transaction.getMerchant();
        // if merchant is active
        merchantService.isMerchantActive(merchant.getId());

        return switch (transactionDto) {
            case AuthorizeTransactionDto at -> authorizeTransaction(merchant, at);
            case ChargeTransactionDto ct -> payTransaction(merchant, transaction, ct);
            case RefundTransactionDto rt -> refundTransaction(merchant, transaction, rt);
            case ReversalTransactionDto ret -> reverseTransaction(merchant, transaction, ret);
            case null, default -> null;
        };
    }

    private AuthorizeTransaction authorizeTransaction(final Merchant merchant, final AuthorizeTransactionDto auth) {
        AuthorizeTransaction authTransaction = transactionMapper.toEntityWithMerchant(auth, merchant);
        authTransaction.setStatus(TransactionStatus.ERROR);
        return transactionRepository.save(authTransaction);
    }

    private ChargeTransaction payTransaction(final Merchant merchant, final Transaction transaction,
                                             final ChargeTransactionDto charge) {
        // create charge transaction
        ChargeTransaction chargeTransaction = transactionMapper.toEntityWithMerchant(charge, merchant);

        if (transaction instanceof AuthorizeTransaction at && at.getStatus() != TransactionStatus.REVERSED) {
            chargeTransaction.setBelongsTo(at);
            chargeTransaction = transactionRepository.save(chargeTransaction);

            // update merchants total sum
            merchant.setTotalTransactionSum(merchant.getTotalTransactionSum().add(charge.getApprovedAmount()));
            merchantService.updateMerchant(merchant);
        } else {
            chargeTransaction.setStatus(TransactionStatus.ERROR);
            transactionRepository.save(chargeTransaction);
        }

        return chargeTransaction;
    }

    public RefundTransaction refundTransaction(final Merchant merchant, final Transaction transaction,
                                               final RefundTransactionDto refund) {
        RefundTransaction refundTransaction = transactionMapper.toEntityWithMerchant(refund, merchant);

        if (transaction instanceof ChargeTransaction ct) {
            refundTransaction.setBelongsTo(ct);
            refundTransaction = transactionRepository.save(refundTransaction);

            // update merchants total sum
            merchant.setTotalTransactionSum(merchant.getTotalTransactionSum().subtract(refund.getReversedAmount()));
            merchantService.updateMerchant(merchant);
        } else {
            refundTransaction.setStatus(TransactionStatus.ERROR);
            transactionRepository.save(refundTransaction);
        }

        return refundTransaction;
    }

    public ReversalTransaction reverseTransaction(final Merchant merchant, final Transaction transaction,
                                                  final ReversalTransactionDto reversal) {
        ReversalTransaction reversalTransaction = transactionMapper.toEntityWithMerchant(reversal, merchant);

        if (transaction instanceof AuthorizeTransaction at) {
            reversalTransaction.setBelongsTo(at);
        } else {
            reversalTransaction.setStatus(TransactionStatus.ERROR);
        }
        transactionRepository.save(reversalTransaction);

        return reversalTransaction;
    }

    @Override
    public long deleteOldTransactions(final int minutesLimit) {
        final ZonedDateTime timestamp = ZonedDateTime.now().minusMinutes(minutesLimit);
        final Set<Transaction> oldTransactions = transactionRepository.findByTimestampBefore(timestamp);
        transactionRepository.deleteAll(oldTransactions);
        return oldTransactions.size();
    }
}
