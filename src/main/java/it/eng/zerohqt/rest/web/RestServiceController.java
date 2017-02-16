package it.eng.zerohqt.rest.web;

import it.eng.zerohqt.config.OrionConfiguration;
import it.eng.zerohqt.dao.TestStationDao;
import it.eng.zerohqt.dao.domain.TestStationData;
import it.eng.zerohqt.orion.OrionContextConsumer;
import it.eng.zerohqt.orion.model.subscribe.SubscriptionResponse;
import javafx.application.Application;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
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
    @RequestMapping(path = "/accumulate", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON})
    public void notification() {
        logger.info("Notification from ORION --> "+new Date().toString());
    }

}
