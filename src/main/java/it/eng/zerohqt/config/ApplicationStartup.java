package it.eng.zerohqt.config;

import it.eng.zerohqt.orion.OrionContextConsumer;
import it.eng.zerohqt.orion.client.model.subscribe.SubscriptionResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ascatox on 04/05/17.
 */
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
    private final Logger logger = Logger.getLogger(ApplicationStartup.class);
    @Autowired
    private OrionContextConsumer orionContextConsumer;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        List<SubscriptionResponse> subscriptionResponses = null;
        try {
            subscriptionResponses = orionContextConsumer.subscribe(false);
            logger.info("Subscriptions to orion executed CORRECTLY: " + subscriptionResponses);
        } catch (Exception e) {
            logger.error("Subscriptions with error: " + e.getMessage());
        }

    }
}
