package it.eng.zerohqt.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eng.zerohqt.business.model.InformationBay;
import it.eng.zerohqt.orion.model.TestStationBayContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by ascatox on 09/02/17.
 */
@Service
public class Reasoner {
    private final Logger logger = Logger.getLogger(Reasoner.class);

    public void feed(String message) {
        ObjectMapper mapper = new ObjectMapper();
        TestStationBayContext stationBayContext = null;
        try {
            stationBayContext = mapper.readValue(message, TestStationBayContext.class);
            final InformationBay informationBay = TestStationBayContextTransformer.transformToInformationBay(stationBayContext);
        } catch (IOException e) {
            logger.error(e);
        }
    }

}
