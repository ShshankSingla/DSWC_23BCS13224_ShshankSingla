import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

interface PaymentProcessor {
    void processPayment(double amount);
}

@Component
@Primary
class StripeProcessor implements PaymentProcessor {

    @Override
    public void processPayment(double amount) {
        System.out.println("Stripe processed payment: " + amount);
    }
}

@Component
class SquareProcessor implements PaymentProcessor {

    @Override
    public void processPayment(double amount) {
        System.out.println("Square processed payment: " + amount);
    }
}

class BankXmlProcessor implements PaymentProcessor {

    private String configuration;

    public BankXmlProcessor(String configuration) {
        this.configuration = configuration;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Bank XML Processor handled payment: " + amount);
    }
}

@Component("bankProcessor")
class BankXmlProcessorFactoryBean implements FactoryBean<BankXmlProcessor> {

    @Override
    public BankXmlProcessor getObject() {

        String step1 = "Load XML Config";
        String step2 = "Validate Certificates";
        String step3 = "Initialize Encryption";
        String step4 = "Build Connection Pool";
        String step5 = "Create Processor";

        return new BankXmlProcessor(
                step1 + step2 + step3 + step4 + step5
        );
    }

    @Override
    public Class<?> getObjectType() {
        return BankXmlProcessor.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

@Component
class CheckoutService {

    private final PaymentProcessor defaultProcessor;
    private final PaymentProcessor bankProcessor;

    public CheckoutService(
            PaymentProcessor defaultProcessor,
            @Qualifier("bankProcessor") PaymentProcessor bankProcessor) {

        this.defaultProcessor = defaultProcessor;
        this.bankProcessor = bankProcessor;
    }

    public void checkout() {

        defaultProcessor.processPayment(5000);

        bankProcessor.processPayment(7500);
    }
}

@SpringBootApplication
public class GlobalPayApplication {

    public static void main(String[] args) {

        var context =
                SpringApplication.run(
                        GlobalPayApplication.class,
                        args
                );

        CheckoutService checkoutService =
                context.getBean(CheckoutService.class);

        checkoutService.checkout();
    }
}