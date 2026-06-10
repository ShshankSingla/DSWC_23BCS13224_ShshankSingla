import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

interface PIIProcessor {
}

@Component
class TransactionService implements PIIProcessor {

    public void processTransaction() {
        System.out.println("Processing Sensitive Transaction");
    }
}

@Component
class PublicCurrencyService {

    public void getExchangeRate() {
        System.out.println("Fetching Exchange Rate");
    }
}

@Component
class ComplianceAuditProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(
            Object bean,
            String beanName) throws BeansException {

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(
            Object bean,
            String beanName) throws BeansException {

        if (bean instanceof PIIProcessor) {
            System.out.println(
                    "[AUDIT] Securely initialized PII Processor Bean: "
                            + beanName
            );
        }

        return bean;
    }
}

@SpringBootApplication
public class SecureBankApplication {

    public static void main(String[] args) {

        var context =
                SpringApplication.run(
                        SecureBankApplication.class,
                        args
                );

        TransactionService transactionService =
                context.getBean(TransactionService.class);

        PublicCurrencyService currencyService =
                context.getBean(PublicCurrencyService.class);

        transactionService.processTransaction();
        currencyService.getExchangeRate();
    }
}