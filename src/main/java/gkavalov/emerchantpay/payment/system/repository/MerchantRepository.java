package gkavalov.emerchantpay.payment.system.repository;

import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import org.springframework.data.repository.CrudRepository;

public interface MerchantRepository extends CrudRepository<Merchant,Long> {
}
