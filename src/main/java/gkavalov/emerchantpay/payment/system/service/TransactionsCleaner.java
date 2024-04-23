package gkavalov.emerchantpay.payment.system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionsCleaner {

    private final TransactionService transactionService;

    // TODO Delete transactions older than an hour
    @Scheduled()
    public void deleteTransactionsRegularly() {

    }
}
