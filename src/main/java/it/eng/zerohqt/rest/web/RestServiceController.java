package it.eng.zerohqt.rest.web;

import it.eng.zerohqt.business.Reasoner;
import it.eng.zerohqt.config.OrionConfiguration;
import it.eng.zerohqt.dao.TestStationDao;
import it.eng.zerohqt.dao.model.TestStationData;
import it.eng.zerohqt.orion.OrionContextConsumer;
import it.eng.zerohqt.orion.client.model.subscribe.SubscriptionResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @Autowired
    private TestStationDao testStationDao;
    @Autowired
    private OrionConfiguration orionConfiguration;
    @Autowired
    Reasoner reasoner;

    //TODO Eliminare in fase di configurazione
    @RequestMapping(path = "/subscribe", method = RequestMethod.GET)
    public List<SubscriptionResponse> subscribe() {
        List<SubscriptionResponse> subscriptionResponses = null;
        try {
            subscriptionResponses = orionContextConsumer.subscribeContexts(Optional.of("teststation"));
        } catch (Exception e) {
            logger.error(e);
        }
        return subscriptionResponses;
    }

    @RequestMapping(path = "/history", method = RequestMethod.GET)
    public List<TestStationData> history() {
        try {
            return testStationDao.findAllNotifications(orionConfiguration.orionService.toLowerCase());
        } catch (Exception e) {
            logger.error(e);
            return new ArrayList<>();
        }
    }


    @RequestMapping(value = "/nexthistory/x0/{x0}/delta/{delta}" ,
    //                   params = {"x0","delta"},
                    method = RequestMethod.GET)
    public List<TestStationData> nexthistory(
            @PathVariable int x0,
            @PathVariable int delta) {
        try {
            return testStationDao.findNextNotifications(orionConfiguration.orionService.toLowerCase(),x0,delta);
        } catch (Exception e) {
            logger.error(e);
            return new ArrayList<>();
        }
    }

    @RequestMapping(path = "/notify", method = RequestMethod.POST)
    public void notification(@RequestBody String message) {
        logger.info("Notification from ORION --> " + message);
        reasoner.feed(message);
    }

}
