import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

interface WebhookIntegration {
    void sendWebhook();
}

@Component
class SlackIntegration implements WebhookIntegration {

    @Override
    public void sendWebhook() {
        System.out.println("Sending webhook to Slack");
    }
}

@Component
class DiscordIntegration implements WebhookIntegration {

    @Override
    public void sendWebhook() {
        System.out.println("Sending webhook to Discord");
    }
}

@Component
class EmailIntegration implements WebhookIntegration {

    @Override
    public void sendWebhook() {
        System.out.println("Sending webhook to Email");
    }
}

@Component
class WebhookDispatcher implements ApplicationContextAware, SmartInitializingSingleton {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

        this.applicationContext = applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {

        Map<String, WebhookIntegration> integrations =
                applicationContext.getBeansOfType(WebhookIntegration.class);

        System.out.println(
                "Routing table ready. Total integrations loaded: "
                        + integrations.size()
        );

        integrations.forEach((beanName, integration) ->
                System.out.println(beanName + " registered"));
    }
}

@SpringBootApplication
public class StreamHubApplication {

    public static void main(String[] args) {

        var context =
                SpringApplication.run(
                        StreamHubApplication.class,
                        args
                );

        Map<String, WebhookIntegration> integrations =
                context.getBeansOfType(WebhookIntegration.class);

        integrations.values()
                .forEach(WebhookIntegration::sendWebhook);
    }
}