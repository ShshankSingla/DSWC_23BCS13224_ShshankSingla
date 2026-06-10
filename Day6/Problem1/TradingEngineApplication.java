import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

interface TradingStrategy {
    void executeTrade();
}

abstract class AbstractStrategy implements TradingStrategy {

    protected String assetClass;

    public String getAssetClass() {
        return assetClass;
    }
}

@Component
class MomentumStrategy extends AbstractStrategy {

    public MomentumStrategy() {
        this.assetClass = "Equity";
    }

    @Override
    public void executeTrade() {
        System.out.println("Executing Momentum Strategy");
    }
}

@Component
class ArbitrageStrategy extends AbstractStrategy {

    public ArbitrageStrategy() {
        this.assetClass = "Derivatives";
    }

    @Override
    public void executeTrade() {
        System.out.println("Executing Arbitrage Strategy");
    }
}

@Component
class MarketDataService {

    public void loadMarketData() {
        System.out.println("Market Data Loaded");
    }
}

@Component
class AlertService {

    public void sendAlert(String message) {
        System.out.println("ALERT : " + message);
    }
}

@Component
class TradingEngine implements BeanNameAware, InitializingBean {

    private final MarketDataService marketDataService;
    private final List<TradingStrategy> strategies;

    private AlertService alertService;

    private String beanName;

    public TradingEngine(
            MarketDataService marketDataService,
            List<TradingStrategy> strategies) {

        this.marketDataService = marketDataService;
        this.strategies = strategies;
    }

    @Autowired(required = false)
    public void setAlertService(AlertService alertService) {
        this.alertService = alertService;
    }

    @Override
    public void setBeanName(String name) {

        this.beanName = name;
        System.out.println("Bean Name : " + beanName);
    }

    @PostConstruct
    public void warmUpCache() {

        System.out.println("Warming up cache...");
        marketDataService.loadMarketData();
    }

    @Override
    public void afterPropertiesSet() {

        if (marketDataService == null) {
            throw new IllegalStateException("MarketDataService missing");
        }

        if (strategies == null || strategies.isEmpty()) {
            throw new IllegalStateException("No strategies loaded");
        }

        System.out.println("Safety validation successful");
    }

    public void startTrading() {

        for (TradingStrategy strategy : strategies) {
            strategy.executeTrade();
        }

        if (alertService != null) {
            alertService.sendAlert("High Risk Trade Detected");
        }
    }

    @PreDestroy
    public void closePositions() {
        System.out.println("Closing all open market positions");
    }
}

@SpringBootApplication
public class TradingEngineApplication {

    public static void main(String[] args) {

        var context =
                SpringApplication.run(
                        TradingEngineApplication.class,
                        args
                );

        TradingEngine engine =
                context.getBean(TradingEngine.class);

        engine.startTrading();
    }
}