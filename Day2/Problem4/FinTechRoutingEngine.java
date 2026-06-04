interface PaymentStrategy {

    boolean processPayment(double amount);
}

class CreditCardStrategy implements PaymentStrategy {

    @Override
    public boolean processPayment(double amount) {

        System.out.println(
                "Processing credit card payment of Rs. " + amount
        );

        return true;
    }
}

class CryptoStrategy implements PaymentStrategy {

    @Override
    public boolean processPayment(double amount) {

        System.out.println(
                "Processing cryptocurrency payment of Rs. " + amount
        );

        return true;
    }
}

class TransactionProcessor {

    private PaymentStrategy strategy;

    public TransactionProcessor(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeTransaction(double amount) {

        boolean status = strategy.processPayment(amount);

        if (status) {
            System.out.println("Transaction completed successfully.");
        } else {
            System.out.println("Transaction failed.");
        }
    }
}

public class FinTechRoutingEngine {

    public static void main(String[] args) {

        PaymentStrategy creditCard =
                new CreditCardStrategy();

        PaymentStrategy crypto =
                new CryptoStrategy();

        TransactionProcessor processor =
                new TransactionProcessor(creditCard);

        processor.executeTransaction(5000);

        System.out.println();

        processor.setPaymentStrategy(crypto);

        processor.executeTransaction(12000);
    }
}