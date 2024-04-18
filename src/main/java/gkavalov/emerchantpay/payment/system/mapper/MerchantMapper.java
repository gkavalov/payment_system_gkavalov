package gkavalov.emerchantpay.payment.system.mapper;

import gkavalov.emerchantpay.payment.system.model.dto.MerchantDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import org.mapstruct.*;

import java.util.Set;

import static org.mapstruct.NullValuePropertyMappingStrategy.SET_TO_DEFAULT;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = TransactionMapper.class,
        nullValuePropertyMappingStrategy = SET_TO_DEFAULT)
public interface MerchantMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMerchant(@MappingTarget final Merchant merchant, final MerchantDto merchantDto);

    Set<MerchantDto> toDto(final Set<Merchant> merchants);

    MerchantDto toDto(final Merchant merchant);
}
