package gkavalov.emerchantpay.payment.system.service;

import gkavalov.emerchantpay.payment.system.configuration.TransactionCleanerConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableScheduling
public class TransactionsCleaner {

    private final TransactionCleanerConfiguration cleanerConfiguration;
    private final TransactionService transactionService;

    @Scheduled(cron = "*/${payment.system.transactions.cleaner.frequencyInSeconds} * * * * *")
    public void deleteTransactionsRegularly() {
        long deletedTransactions = transactionService.deleteOldTransactions(cleanerConfiguration.getTimeLimitInMins());
        if (deletedTransactions > 0) {
            log.info("Deleted {} transactions", deletedTransactions);
        }
    }
}
