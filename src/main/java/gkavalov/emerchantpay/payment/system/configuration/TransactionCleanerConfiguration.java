package gkavalov.emerchantpay.payment.system.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "payment.system.transactions.cleaner")
public class TransactionCleanerConfiguration {

    private int frequencyInSeconds;
    private int timeLimitInMins;
}
