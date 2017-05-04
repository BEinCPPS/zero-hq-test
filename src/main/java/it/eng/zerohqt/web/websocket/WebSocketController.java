package it.eng.zerohqt.web.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eng.zerohqt.business.Reasoner;
import it.eng.zerohqt.business.model.Acknowledge;
import it.eng.zerohqt.business.model.FeedbackInfo;
import it.eng.zerohqt.business.model.InformationBay;
import it.eng.zerohqt.config.WebSocketConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * Created by ascatox on 20/02/17.
 */
@Controller
public class WebSocketController {
    private final Logger logger = Logger.getLogger(Reasoner.class);
    @Autowired
    private SimpMessagingTemplate webSocketSender;


    public void sendToClient(InformationBay informationBay) throws Exception {
        logger.info(informationBay.toString());
        ObjectMapper mapper = new ObjectMapper();
        webSocketSender.convertAndSend(WebSocketConfiguration.DEFAULT_CHANNEL + "/"
                        + WebSocketConfiguration.INFORMATION_BAY_TOPIC,
                mapper.writeValueAsString(informationBay));
        webSocketSender.convertAndSend(WebSocketConfiguration.DEFAULT_CHANNEL + "/"
                        + WebSocketConfiguration.ACKNOWLEDGE_TOPIC,
                mapper.writeValueAsString(informationBay));
        logger.info("Sended at: " + new Date());
    }

    public void sendToClient(FeedbackInfo feedbackInfo) throws Exception {
        logger.info(feedbackInfo.toString());
        ObjectMapper mapper = new ObjectMapper();
        webSocketSender.convertAndSend(WebSocketConfiguration.DEFAULT_CHANNEL + "/"
                        + WebSocketConfiguration.FEEDBACK_TOPIC,
                mapper.writeValueAsString(feedbackInfo));
        logger.info("Sended at: " + new Date());
    }



}
