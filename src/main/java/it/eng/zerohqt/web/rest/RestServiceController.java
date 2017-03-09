package it.eng.zerohqt.web.rest;

import it.eng.zerohqt.business.InformationBayContextTransformer;
import it.eng.zerohqt.business.Reasoner;
import it.eng.zerohqt.business.model.Acknowledge;
import it.eng.zerohqt.business.model.InformationBay;
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
@CrossOrigin //TODO Filter Cross by domain
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
    public List<Acknowledge> history() {
        try {
            return InformationBayContextTransformer.
                    transformToInformationBaies(testStationDao
                            .findAllNotifications(orionConfiguration.orionService.toLowerCase()));
        } catch (Exception e) {
            logger.error(e);
            return new ArrayList<>();
        }
    }


    @RequestMapping(path = "/nexthistory", method = RequestMethod.GET)
    public List<Acknowledge> nextHistory(
            @RequestParam int startPoint,
            @RequestParam int delta) {
        try {
            return InformationBayContextTransformer.
                    transformToInformationBaies(testStationDao.
                            findNextNotifications(orionConfiguration.orionService.toLowerCase(), startPoint, delta));
        } catch (Exception e) {
            logger.error(e);
            return new ArrayList<>();
        }
    }
    //TODO Don't secure this endpoint, used by ORION
    @RequestMapping(path = "/notify", method = RequestMethod.POST)
    public void notification(@RequestBody String message) {
        logger.info("StateInfo from ORION --> " + message);
        reasoner.feed(message);
    }

}
