package it.eng.zerohqt.rest.websocket;

import it.eng.zerohqt.business.Reasoner;
import it.eng.zerohqt.business.model.InformationBay;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ascatox on 20/02/17.
 */
@Controller
@RequestMapping("/ws")
public class WebSocketController {
    private final Logger logger = Logger.getLogger(Reasoner.class);
    @Autowired
    private SimpMessagingTemplate sender;


    public void sendToClient(InformationBay informationBay) throws Exception {
        logger.info(informationBay.toString());
        sender.convertAndSend("/websocket/topic", informationBay) ;
    }

}
