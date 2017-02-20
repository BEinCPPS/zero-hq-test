package it.eng.zerohqt.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eng.zerohqt.business.model.InformationBay;
import it.eng.zerohqt.orion.model.TestStationBayContext;
import it.eng.zerohqt.rest.websocket.WebSocketController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
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
        TestStationBayContext stationBayContext;
        try {
            stationBayContext = mapper.readValue(message, TestStationBayContext.class);
            Optional<List<InformationBay>> informationBays = TestStationBayContextTransformer.transformToInformationBay(stationBayContext);
            InformationBay informationBay = null;
            if (informationBays.isPresent()) {
                informationBay = informationBays.get().get(0); //TODO Only one
            }
            try {
                webSocketController.sendToClient(informationBay);
            } catch (Exception e) {
                logger.error(e);
            }
        } catch (IOException e) {
            logger.error(e);
        }
    }

}
