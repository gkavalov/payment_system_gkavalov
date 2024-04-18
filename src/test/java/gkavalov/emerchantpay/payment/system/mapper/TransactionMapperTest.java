package gkavalov.emerchantpay.payment.system.mapper;

import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static gkavalov.emerchantpay.payment.system.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TransactionMapperTest {

    private final TransactionMapper mapperToTest = Mappers.getMapper(TransactionMapper.class);

    @Test
    void testDtoConversion() {
        final TransactionDto mappedDto = mapperToTest.toDto(Set.of(MOCK_TRANSACTION_2)).toArray(new TransactionDto[]{})[0];

        assertEquals(mappedDto.getStatus(), MOCK_TRANSACTION_2.getStatus());
        TransactionDto mappedBelongsTo = mapperToTest.toDto(MOCK_TRANSACTION_2.getBelongsTo());
        assertEquals(mappedDto.getBelongsTo().getCustomerPhone(), mappedBelongsTo.getCustomerPhone());
        assertEquals(mappedDto.getBelongsTo().getStatus(), mappedBelongsTo.getStatus());
        assertEquals(mappedDto.getBelongsTo().getAmount(), mappedBelongsTo.getAmount());
        assertEquals(mappedDto.getBelongsTo().getReferenceId(), mappedBelongsTo.getReferenceId());
        assertEquals(mappedDto.getBelongsTo().getCustomerEmail(), mappedBelongsTo.getCustomerEmail());
        assertEquals(mappedDto.getAmount(), MOCK_TRANSACTION_2.getAmount());
        assertEquals(mappedDto.getReferenceId(), MOCK_TRANSACTION_2.getReferenceId());
        assertEquals(mappedDto.getCustomerEmail(), MOCK_TRANSACTION_2.getCustomerEmail());
        assertEquals(mappedDto.getCustomerPhone(), MOCK_TRANSACTION_2.getCustomerPhone());
    }

    @Test
    void testEntityConversionWithMerchant() {
        TransactionDto mappedTransactionDto = mapperToTest.toDto(MOCK_TRANSACTION_1);
        Transaction entityWithMerchant = mapperToTest.toEntityWithMerchant(mappedTransactionDto, MOCK_MERCHANT_1);

        assertEquals(entityWithMerchant.getAmount(), mappedTransactionDto.getAmount());
        assertEquals(entityWithMerchant.getStatus(), mappedTransactionDto.getStatus());
        assertEquals(entityWithMerchant.getReferenceId(), mappedTransactionDto.getReferenceId());
        assertEquals(entityWithMerchant.getCustomerEmail(), mappedTransactionDto.getCustomerEmail());
        assertEquals(entityWithMerchant.getCustomerPhone(), mappedTransactionDto.getCustomerPhone());
        assertEquals(entityWithMerchant.getMerchant().getId(), MOCK_MERCHANT_1.getId());
        assertEquals(entityWithMerchant.getMerchant().getName(), MOCK_MERCHANT_1.getName());
        assertEquals(entityWithMerchant.getMerchant().getEmail(), MOCK_MERCHANT_1.getEmail());
        assertEquals(entityWithMerchant.getMerchant().getDescription(), MOCK_MERCHANT_1.getDescription());
        assertEquals(entityWithMerchant.getMerchant().getTotalTransactionSum(), MOCK_MERCHANT_1.getTotalTransactionSum());
        assertEquals(entityWithMerchant.getMerchant().getTransactions().size(), MOCK_MERCHANT_1.getTransactions().size());
    }
}
