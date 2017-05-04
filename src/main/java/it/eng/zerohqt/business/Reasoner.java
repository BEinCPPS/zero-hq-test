package it.eng.zerohqt.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eng.zerohqt.business.model.FeedbackInfo;
import it.eng.zerohqt.business.model.InformationBay;
import it.eng.zerohqt.business.transformer.ZeroHQTContextTransformer;
import it.eng.zerohqt.orion.model.ZeroHQTContext;
import it.eng.zerohqt.web.websocket.WebSocketController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by ascatox on 09/02/17.
 */
@Service
public class Reasoner {
    private final Logger logger = Logger.getLogger(Reasoner.class);
    @Autowired
    WebSocketController webSocketController;

    public void feed(String message) {
        ObjectMapper mapper = new ObjectMapper();
        ZeroHQTContext zeroHQTContext;
        try {
            zeroHQTContext = mapper.readValue(message, ZeroHQTContext.class);
            Optional<?> zeroHQTObject = ZeroHQTContextTransformer
                    .transformForWebSocket(zeroHQTContext);
            if (zeroHQTObject.isPresent()) {
                if (zeroHQTObject.get() instanceof InformationBay) {
                    try {
                        InformationBay informationBay = (InformationBay) zeroHQTObject.get();
                        webSocketController.sendToClient(informationBay);
                    } catch (Exception e) {
                        logger.error(e);
                    }
                } else {
                    if (zeroHQTObject.get() instanceof FeedbackInfo) {
                        try {
                            FeedbackInfo feedbackInfo = (FeedbackInfo) zeroHQTObject.get();
                            webSocketController.sendToClient(feedbackInfo);
                        } catch (Exception e) {
                            logger.error(e);
                        }
                    }
                }

            }

        } catch (IOException e) {
            logger.error(e);
        }
    }

}
