package it.eng.zerohqt.config;

import it.eng.zerohqt.orion.OrionContextConsumer;
import it.eng.zerohqt.orion.client.model.subscribe.SubscriptionResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ascatox on 23/05/17.
 */
@Component
public class SubscriptionsScheduler {
    @Autowired
    private OrionContextConsumer orionContextConsumer;

    private final Logger logger = Logger.getLogger(SubscriptionsScheduler.class);

    @Scheduled(fixedRate = 30000)
    public void subscribe() {
        List<SubscriptionResponse> subscriptionResponses = null;
        try {
            subscriptionResponses = orionContextConsumer.subscribe();
            logger.info("Subscriptions to orion executed CORRECTLY: " + subscriptionResponses);
        } catch (Exception e) {
            logger.error("Subscriptions with error: " + e.getMessage());
        }
    }
}
