package gkavalov.emerchantpay.payment.system.mapper;

import gkavalov.emerchantpay.payment.system.model.dto.MerchantDto;
import gkavalov.emerchantpay.payment.system.model.dto.UpdateMerchantDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import org.mapstruct.*;

import java.util.Set;

import static org.mapstruct.NullValuePropertyMappingStrategy.SET_TO_DEFAULT;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = TransactionMapper.class,
        nullValuePropertyMappingStrategy = SET_TO_DEFAULT)
public interface MerchantMapper {

    Set<MerchantDto> toDto(final Set<Merchant> merchants);

    @Mapping(target = "transactions", qualifiedByName = "toNestedDto")
    MerchantDto toDto(final Merchant merchant);

    @Mapping(target = "transactions", ignore = true)
    Merchant toEntity(final MerchantDto merchant);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMerchant(@MappingTarget final Merchant merchant, final UpdateMerchantDto merchantDto);
}
