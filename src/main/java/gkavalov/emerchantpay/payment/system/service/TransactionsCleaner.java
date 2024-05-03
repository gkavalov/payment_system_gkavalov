package gkavalov.emerchantpay.payment.system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableScheduling
public class TransactionsCleaner {

    @Value("${payment.system.transactions.cleaner.timeLimitInMins}")
    private int getTimeLimitInMins;

    private final TransactionService transactionService;

    @Scheduled(cron = "*/${payment.system.transactions.cleaner.frequencyInSeconds} * * * * *")
    public void cleanOldTransactions() {
        long deletedTransactions = transactionService.deleteOldTransactions(getTimeLimitInMins);
        if (deletedTransactions > 0) {
            log.info("Deleted {} transactions", deletedTransactions);
        }
    }
}
