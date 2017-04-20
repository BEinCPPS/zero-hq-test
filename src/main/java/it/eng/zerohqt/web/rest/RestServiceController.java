package it.eng.zerohqt.web.rest;

import it.eng.zerohqt.business.InformationBayContextTransformer;
import it.eng.zerohqt.business.Reasoner;
import it.eng.zerohqt.business.model.Acknowledge;
import it.eng.zerohqt.config.OrionConfiguration;
import it.eng.zerohqt.dao.TablesMetaDataDao;
import it.eng.zerohqt.dao.TestStationDao;
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
    private TablesMetaDataDao tablesMetaDataDao;
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
                    transformToAcknowledges(testStationDao
                            .findAllNotifications(orionConfiguration.orionService.toLowerCase()));
        } catch (Exception e) {
            logger.error(e);
            return new ArrayList<>();
        }
    }

    @RequestMapping(path = "/stationsBays", method = RequestMethod.GET)
    public List<String> stationsBays() {
        try {
            return tablesMetaDataDao.getTablesMetaData(orionConfiguration.orionService);

        } catch (Exception e) {
            logger.error(e);
            return new ArrayList<>();
        }
    }

    @RequestMapping(path = "/stationBayHistory", method = RequestMethod.GET)
    public List<Acknowledge> stationBayHistory(
            @RequestParam String stationBay) {
        try {
            return InformationBayContextTransformer.
                    transformToAcknowledges(testStationDao.
                            findAllNotificationsStationBay(orionConfiguration.orionService.toLowerCase(), stationBay));
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
                    transformToAcknowledges(testStationDao.
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

    /*@RequestMapping(value = "/error")
    public String error() {
        return "<h1>You got an ERROR :-(<h1>"; //TODO
    }*/


}
