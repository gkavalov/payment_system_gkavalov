package gkavalov.emerchantpay.payment.system.service;

import gkavalov.emerchantpay.payment.system.mapper.TransactionMapper;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import gkavalov.emerchantpay.payment.system.repository.TransactionRepository;
import gkavalov.emerchantpay.payment.system.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static gkavalov.emerchantpay.payment.system.TestConstants.MOCK_TRANSACTION_1;
import static gkavalov.emerchantpay.payment.system.TestConstants.MOCK_TRANSACTION_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImpl underTest;

    @Test
    void testGetAllTransactions() {
        // given
        final Set<Transaction> mockedTransactionSet = Set.of(MOCK_TRANSACTION_1, MOCK_TRANSACTION_2);
        when(transactionRepository.findAll()).thenReturn(mockedTransactionSet);

        // when
        final Set<Transaction> allTransactions = underTest.getAllTransactions();

        // then
        assertEquals(allTransactions.size(), mockedTransactionSet.size());
    }
}
