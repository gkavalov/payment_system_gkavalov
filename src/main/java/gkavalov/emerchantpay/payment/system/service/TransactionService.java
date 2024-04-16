package gkavalov.emerchantpay.payment.system.service;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    // TODO Only approved or refunded transactions can be referenced,
    // otherwise the status of the submitted transaction will be created with
    // error status
}
