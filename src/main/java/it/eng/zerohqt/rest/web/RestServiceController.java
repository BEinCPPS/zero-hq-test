package it.eng.zerohqt.rest.web;

import it.eng.zerohqt.orion.OrionContextConsumer;
import it.eng.zerohqt.orion.model.subscribe.SubscriptionResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by ascatox on 10/02/17.
 */
@RestController
public class RestServiceController {

    private final Logger logger = Logger.getLogger(RestServiceController.class);
    @Autowired
    private OrionContextConsumer orionContextConsumer;

    @RequestMapping(path = "/subscribe", method = RequestMethod.GET)
    public List<SubscriptionResponse> subscribe() {
        List<SubscriptionResponse> subscriptionResponses = null;
        try {
            subscriptionResponses = orionContextConsumer.subscribeContexts(Optional.empty());
        } catch (Exception e) {
            logger.error(e);
        }
        return subscriptionResponses;
    }

    @RequestMapping(path = "/notify", method = RequestMethod.POST)
    public String notify(String message) {
        logger.info("Notification from ORION --> "+message);
        return message;
    }

}
