package it.eng.zerohqt.web.rest;

import it.eng.zerohqt.business.Reasoner;
import it.eng.zerohqt.business.model.Acknowledge;
import it.eng.zerohqt.business.model.FeedbackScale;
import it.eng.zerohqt.business.transformer.ZeroHQTContextTransformer;
import it.eng.zerohqt.config.OrionConfiguration;
import it.eng.zerohqt.dao.HistoryDao;
import it.eng.zerohqt.dao.TablesMetaDataDao;
import it.eng.zerohqt.dao.TestStationDao;
import it.eng.zerohqt.dao.UserAccessDao;
import it.eng.zerohqt.dao.model.UserAccess;
import it.eng.zerohqt.orion.OrionContextConsumer;
import it.eng.zerohqt.orion.client.model.subscribe.SubscriptionResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private HistoryDao historyDao;
    @Autowired
    private TablesMetaDataDao tablesMetaDataDao;
    @Autowired
    private OrionConfiguration orionConfiguration;
    @Autowired
    Reasoner reasoner;
    @Autowired
    private UserAccessDao userAccessDao;

    @RequestMapping(path = "/subscribe", method = RequestMethod.GET)
    public List<SubscriptionResponse> subscribe() {
        try {
            return orionContextConsumer.subscribe(true);
        } catch (Exception e) {
            logger.error(e);
            throw new RuntimeException(e);
        }

    }

    @RequestMapping(path = "/history", method = RequestMethod.GET)
    public List<Acknowledge> history() {
        try {
            return historyDao.readHistory(orionConfiguration.orionService.toLowerCase());
        } catch (Exception e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(path = "/feedbackScale", method = RequestMethod.GET)
    public List<FeedbackScale> getFeedbackScale() {
        try {
            return orionContextConsumer.readFeedbackScaleContext();
        } catch (Exception e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }


    @RequestMapping(path = "/stationsBays", method = RequestMethod.GET)
    public List<String> stationsBays() {
        try {
            return tablesMetaDataDao.getTablesMetaData(orionConfiguration.orionService);

        } catch (Exception e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(path = "/stationBayHistory", method = RequestMethod.GET)
    public List<Acknowledge> stationBayHistory(
            @RequestParam String stationBay) {
        try {
            return ZeroHQTContextTransformer.
                    transformToAcknowledges(testStationDao.
                            findAllNotificationsStationBay(orionConfiguration.orionService.toLowerCase(), stationBay));
        } catch (Exception e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(path = "/historyByAck", method = RequestMethod.GET)
    public List<Acknowledge> historyByAck(
            @RequestParam String ackType) {
        try {
            return historyDao.readHistoryByAck(orionConfiguration.orionService, ackType);
        } catch (Exception e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(path = "/nexthistory", method = RequestMethod.GET)
    public List<Acknowledge> nextHistory(
            @RequestParam int startPoint,
            @RequestParam int delta) {
        try {
            return ZeroHQTContextTransformer.
                    transformToAcknowledges(testStationDao.
                            findNextNotifications(orionConfiguration.orionService.toLowerCase(), startPoint, delta));
        } catch (Exception e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(path = "/userAccess", method = RequestMethod.POST)
    public void logUserAccess(@RequestBody UserAccess userAccess) {
        if (userAccess != null && !StringUtils.isBlank(userAccess.getEmail()))
            try {
                userAccessDao.insertUserAccess(userAccess);
            } catch (Exception e) {
                logger.error(e);
                throw new RuntimeException(e);
            }

    }

    //TODO Don't secure this endpoint, used by ORION
    @RequestMapping(path = "/notify", method = RequestMethod.POST)
    public void notification(@RequestBody String message) {
        logger.info("StateInfo from ORION --> " + message);
        reasoner.feed(message);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public String eHandler(RuntimeException e) {
        return e.getMessage();
    }

}
