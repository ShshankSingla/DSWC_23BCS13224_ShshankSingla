import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

class Order {

    private String orderId;

    public Order(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return orderId;
    }
}

public class ExchangeManager {

    private ConcurrentHashMap<String,
            CopyOnWriteArrayList<Order>>
            orderBook =
            new ConcurrentHashMap<>();

    public void placeOrder(
            String ticker,
            Order order) {

        orderBook
                .computeIfAbsent(
                        ticker,
                        key ->
                                new CopyOnWriteArrayList<>()
                )
                .add(order);
    }

    public CopyOnWriteArrayList<Order>
    getOrders(String ticker) {

        return orderBook.get(ticker);
    }

    public static void main(String[] args) {

        ExchangeManager manager =
                new ExchangeManager();

        manager.placeOrder(
                "BTC",
                new Order("ORDER-1")
        );

        manager.placeOrder(
                "BTC",
                new Order("ORDER-2")
        );

        System.out.println(
                manager.getOrders("BTC")
        );
    }
}