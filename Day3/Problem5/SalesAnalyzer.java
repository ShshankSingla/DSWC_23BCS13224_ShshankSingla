import java.util.List;

enum Status {
    COMPLETED,
    PENDING
}

class Transaction {

    private String category;
    private Status status;
    private double amount;

    public Transaction(
            String category,
            Status status,
            double amount) {

        this.category = category;
        this.status = status;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public Status getStatus() {
        return status;
    }

    public double getAmount() {
        return amount;
    }
}

public class SalesAnalyzer {

    public double calculateElectronicsRevenue(
            List<Transaction> transactions) {

        return transactions
                .stream()
                .filter(t ->
                        t.getStatus()
                                == Status.COMPLETED)
                .filter(t ->
                        t.getCategory()
                                .equals("ELECTRONICS"))
                .mapToDouble(
                        Transaction::getAmount)
                .sum();
    }

    public static void main(String[] args) {

        List<Transaction> transactions =
                List.of(

                        new Transaction(
                                "ELECTRONICS",
                                Status.COMPLETED,
                                1000
                        ),

                        new Transaction(
                                "FASHION",
                                Status.COMPLETED,
                                500
                        ),

                        new Transaction(
                                "ELECTRONICS",
                                Status.PENDING,
                                700
                        ),

                        new Transaction(
                                "ELECTRONICS",
                                Status.COMPLETED,
                                300
                        )
                );

        SalesAnalyzer analyzer =
                new SalesAnalyzer();

        System.out.println(
                analyzer
                        .calculateElectronicsRevenue(
                                transactions
                        )
        );
    }
}