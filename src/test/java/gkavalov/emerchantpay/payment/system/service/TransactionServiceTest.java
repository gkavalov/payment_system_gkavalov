package gkavalov.emerchantpay.payment.system.service;

import gkavalov.emerchantpay.payment.system.exception.InactiveMerchantException;
import gkavalov.emerchantpay.payment.system.exception.NonPayableTransactionException;
import gkavalov.emerchantpay.payment.system.mapper.TransactionMapperImpl;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.ChargeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.ChargeTransaction;
import gkavalov.emerchantpay.payment.system.repository.TransactionRepository;
import gkavalov.emerchantpay.payment.system.service.impl.TransactionServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static gkavalov.emerchantpay.payment.system.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository mockTransactionRepository;

    @Spy
    private TransactionMapperImpl mockTransactionMapper;

    @Mock
    private MerchantService mockMerchantService;

    @InjectMocks
    private TransactionServiceImpl underTest;

    @Captor
    private ArgumentCaptor<Transaction> transactionCaptor;

    @Test
    void testGetAllTransactions() {
        // given
        final Set<Transaction> mockedTransactionSet = Set.of(MOCK_TRANSACTION_1, MOCK_TRANSACTION_2);
        when(mockTransactionRepository.findAll()).thenReturn(mockedTransactionSet);

        // when
        final Set<Transaction> allTransactions = underTest.getAllTransactions();

        // then
        assertEquals(allTransactions.size(), mockedTransactionSet.size());
    }

    @Test
    void testGetTransaction() {
        // given
        when(mockTransactionRepository.findById(MOCK_TRANSACTION_1.getUuid())).thenReturn(Optional.of(MOCK_TRANSACTION_1));

        // when
        final Transaction transaction = underTest.getTransaction(MOCK_TRANSACTION_1.getUuid());

        // then
        assertEquals(transaction.getUuid(), MOCK_TRANSACTION_1.getUuid());
        assertEquals(transaction.getReferenceId(), MOCK_TRANSACTION_1.getReferenceId());
        assertEquals(transaction.getStatus(), MOCK_TRANSACTION_1.getStatus());
        assertEquals(transaction.getCustomerEmail(), MOCK_TRANSACTION_1.getCustomerEmail());
        assertEquals(transaction.getBelongsTo(), MOCK_TRANSACTION_1.getBelongsTo());
        assertEquals(transaction.getAmount(), MOCK_TRANSACTION_1.getAmount());
        assertEquals(transaction.getCustomerPhone(), MOCK_TRANSACTION_1.getCustomerPhone());
        assertEquals(transaction.getMerchant().getId(), MOCK_TRANSACTION_1.getMerchant().getId());
    }

    @Test
    void testNoTransactionFound() {
        // given
        when(mockTransactionRepository.findById(MOCK_TRANSACTION_1.getUuid())).thenReturn(Optional.empty());

        // when and then
        assertThrows(EntityNotFoundException.class, () -> underTest.getTransaction(MOCK_TRANSACTION_1.getUuid()));
    }

    @Test
    void testCreateTransaction() {
        // given
        TransactionDto transactionDto = mockTransactionMapper.toTopLevelDto(MOCK_TRANSACTION_1);
        when(mockTransactionRepository.save(any())).thenReturn(MOCK_TRANSACTION_1);

        // when
        Transaction transactionForMerchant = underTest.createTransactionForMerchant(transactionDto, MOCK_MERCHANT_1);

        //then
        verify(mockTransactionRepository).save(transactionCaptor.capture());
        assertEquals(MOCK_TRANSACTION_1.getMerchant().getId(), transactionCaptor.getValue().getMerchant().getId());
        assertEquals(MOCK_TRANSACTION_1.getUuid(), transactionForMerchant.getUuid());
    }

    @Test
    void testPayTransaction() throws NonPayableTransactionException, InactiveMerchantException {
        // given
        final ChargeTransactionDto chargeDto = (ChargeTransactionDto) mockTransactionMapper.toTopLevelDto(MOCK_TRANSACTION_2);
        when(mockTransactionRepository.findById(MOCK_TRANSACTION_1.getUuid())).thenReturn(Optional.of(MOCK_TRANSACTION_1));
        when(mockTransactionRepository.save(any())).thenReturn(MOCK_TRANSACTION_2);

        // when
        final ChargeTransaction chargeTransaction = underTest.paymentForTransaction(MOCK_TRANSACTION_1.getUuid(), chargeDto);

        // then
        verify(mockTransactionRepository).save(transactionCaptor.capture());
        assertEquals(MOCK_TRANSACTION_1.getMerchant().getId(), transactionCaptor.getValue().getMerchant().getId());
        assertEquals(MOCK_TRANSACTION_2.getUuid(), chargeTransaction.getUuid());
    }

    @Test
    void testPayIncorrectTransactionType() {
        // given
        final ChargeTransactionDto chargeDto = (ChargeTransactionDto) mockTransactionMapper.toTopLevelDto(MOCK_TRANSACTION_2);
        when(mockTransactionRepository.findById(MOCK_TRANSACTION_2.getUuid())).thenReturn(Optional.of(MOCK_TRANSACTION_2));

        // when and then
        assertThrows(NonPayableTransactionException.class, () -> underTest.paymentForTransaction(MOCK_TRANSACTION_2.getUuid(), chargeDto));
    }
}
