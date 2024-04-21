package gkavalov.emerchantpay.payment.system.service;

import gkavalov.emerchantpay.payment.system.exception.InactiveMerchantException;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.repository.MerchantRepository;
import gkavalov.emerchantpay.payment.system.service.impl.MerchantServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static gkavalov.emerchantpay.payment.system.TestConstants.MOCK_MERCHANT_1;
import static gkavalov.emerchantpay.payment.system.TestConstants.MOCK_MERCHANT_2;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MerchantServiceTest {

    @Mock
    private MerchantRepository mockMerchantRepository;

    @InjectMocks
    private MerchantServiceImpl underTest;

    @Test
    void testGetAllMerchants() {
        // given
        final Set<Merchant> mockedMerchantSet = Set.of(MOCK_MERCHANT_1, MOCK_MERCHANT_2);
        when(mockMerchantRepository.findAll()).thenReturn(mockedMerchantSet);
        // when
        final Set<Merchant> allMerchants = underTest.getAllMerchants();
        // then
        assertEquals(allMerchants.size(), mockedMerchantSet.size());
    }

    @Test
    void testGetMerchant() {
        // given
        final Long merchantId = 2L;
        when(mockMerchantRepository.findById(merchantId)).thenReturn(Optional.of(MOCK_MERCHANT_2));
        // when
        final Merchant merchant = underTest.getMerchant(merchantId);
        // then
        assertEquals(merchantId, merchant.getId());
        assertEquals(MOCK_MERCHANT_2.getId(), merchant.getId());
    }

    @Test
    void testNoMerchantFound() {
        // given
        final Long merchantId = 2L;
        when(mockMerchantRepository.findById(merchantId)).thenReturn(Optional.empty());
        // when and then
        assertThrows(EntityNotFoundException.class, () -> underTest.getMerchant(merchantId));
    }

    @Test
    void testGetActiveMerchant() throws InactiveMerchantException {
        // given
        final Long merchantId = 1L;
        when(mockMerchantRepository.findById(merchantId)).thenReturn(Optional.of(MOCK_MERCHANT_1));
        // when
        final Merchant merchant = underTest.isMerchantActive(merchantId);
        // then
        assertEquals(merchantId, merchant.getId());
        assertEquals(MOCK_MERCHANT_1.getId(), merchant.getId());
    }

    @Test
    void testGetInactiveMerchant() {
        // given
        final Long merchantId = 2L;
        when(mockMerchantRepository.findById(merchantId)).thenReturn(Optional.of(MOCK_MERCHANT_2));
        // when and then
        assertThrows(InactiveMerchantException.class, () -> underTest.isMerchantActive(merchantId));
    }

    @Test
    void testDeleteMerchant() {
        // given
        final Long merchantId = 1L;
        when(mockMerchantRepository.findById(merchantId)).thenReturn(Optional.of(MOCK_MERCHANT_2));
        // when and then
        assertDoesNotThrow(() -> underTest.deleteMerchant(merchantId));
    }
}
