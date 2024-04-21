package gkavalov.emerchantpay.payment.system.mapper;

import gkavalov.emerchantpay.payment.system.model.dto.MerchantDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.MerchantStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static gkavalov.emerchantpay.payment.system.TestConstants.MOCK_MERCHANT_1;
import static gkavalov.emerchantpay.payment.system.TestConstants.MOCK_MERCHANT_2;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MerchantMapperTest {

    @Spy
    private TransactionMapperImpl mockTransactionMapper;

    @InjectMocks
    private MerchantMapperImpl mapperToTest;

    @Test
    void testDtoConversion() {
        final MerchantDto mappedDto = mapperToTest.toDto(Set.of(MOCK_MERCHANT_1)).toArray(new MerchantDto[]{})[0];

        assertEquals(mappedDto.getDescription(), MOCK_MERCHANT_1.getDescription());
        assertEquals(mappedDto.getName(), MOCK_MERCHANT_1.getName());
        assertEquals(mappedDto.getEmail(), MOCK_MERCHANT_1.getEmail());
        assertEquals(mappedDto.getStatus(), MOCK_MERCHANT_1.getStatus());
        assertEquals(mappedDto.getTotalTransactionSum(), MOCK_MERCHANT_1.getTotalTransactionSum());
        assertEquals(mappedDto.getTransactions().size(), MOCK_MERCHANT_1.getTransactions().size());
    }

    @Test
    void testEntityConversion() {
        final MerchantDto dto = mapperToTest.toDto(MOCK_MERCHANT_1);
        final Merchant entity = mapperToTest.toEntity(dto);

        assertEquals(entity.getDescription(), MOCK_MERCHANT_1.getDescription());
        assertEquals(entity.getName(), MOCK_MERCHANT_1.getName());
        assertEquals(entity.getEmail(), MOCK_MERCHANT_1.getEmail());
        assertEquals(entity.getStatus(), MOCK_MERCHANT_1.getStatus());
        assertEquals(entity.getTotalTransactionSum(), MOCK_MERCHANT_1.getTotalTransactionSum());
        // TODO Resolve the cyclical mapping
        //assertEquals(entity.getTransactions().size(), MOCK_MERCHANT_1.getTransactions().size());
    }

    @Test
    void testEntityWithMerchantConversion() {
        final Merchant localMockedMerchant = new Merchant(1L, "test-merchant-1-name",
                "test-merchant-1-description", "test@merchant.one", MerchantStatus.ACTIVE,
                new BigDecimal("1.0"), new HashSet<>());
        final MerchantDto mappedDto = mapperToTest.toDto(Set.of(MOCK_MERCHANT_2)).toArray(new MerchantDto[]{})[0];
        mapperToTest.updateMerchant(localMockedMerchant, mappedDto);

        assertEquals(localMockedMerchant.getDescription(), MOCK_MERCHANT_2.getDescription());
        assertEquals(localMockedMerchant.getName(), MOCK_MERCHANT_2.getName());
        assertEquals(localMockedMerchant.getEmail(), MOCK_MERCHANT_2.getEmail());
        assertEquals(localMockedMerchant.getStatus(), MOCK_MERCHANT_2.getStatus());
        assertEquals(localMockedMerchant.getTotalTransactionSum(), MOCK_MERCHANT_2.getTotalTransactionSum());
        assertEquals(localMockedMerchant.getTransactions().size(), MOCK_MERCHANT_2.getTransactions().size());
    }
}
