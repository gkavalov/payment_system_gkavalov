package gkavalov.emerchantpay.payment.system.repository;

import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TransactionRepository extends CrudRepository<Transaction, UUID> {
}
