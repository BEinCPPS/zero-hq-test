package it.eng.zerohqt;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eng.zerohqt.business.model.Acknowledge;
import it.eng.zerohqt.business.model.InformationBay;
import it.eng.zerohqt.business.model.StateInfo;
import it.eng.zerohqt.business.transformer.ZeroHQTContextTransformer;
import it.eng.zerohqt.config.WebSocketConfiguration;
import it.eng.zerohqt.dao.model.AcknowledgeType;
import it.eng.zerohqt.dao.model.StateType;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by ascatox on 06/03/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ZeroHqWebSocketTest {

    static final String WEBSOCKET_URI = "ws://localhost:8080/websocket";
    static final String WEBSOCKET_TOPIC = WebSocketConfiguration.DEFAULT_CHANNEL;
    public static final int DELAY = 3000; //milliseconds
    public static final String PATHNAME = "/Users/ascatox/Documents/Sviluppo/workspace_beincpps/zeroHQTest/src/main/resources/mock/informationBay.json";
    private final Logger logger = Logger.getLogger(ZeroHqWebSocketTest.class);

    BlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;
    String[] messages;
    Random rand;
    List<InformationBay> informationBayList;

    @Before
    public void setup() {
        informationBayList = new ArrayList<>();
        rand = new Random();
        /*
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 4; j++) {
                InformationBay informationBay = new InformationBay();
                informationBay.setIpAddress("100.100.100.100");
                String testStationName = "TestStation" + i;
                informationBay.setStationName(testStationName);
                informationBay.setBayCode("testStation:" + testStationName + "_" + j);
                informationBay.setBayNumber(j);
                StateInfo stateInfo = new StateInfo();
                stateInfo.setStateDescription("Rule Execution");
                stateInfo.setStatePayload("|N| | |N| |A|0|");
                int stateCode = 127 - j;
                stateInfo.setStateCode(stateCode + "");
                stateInfo.setTimestamp(new Date());
                Acknowledge acknowledge = new Acknowledge();
                acknowledge.setBayCode(("testStation:" + testStationName + "_" + j));
                acknowledge.setBayNumber(j);
                acknowledge.setStationName(testStationName);
                acknowledge.setAckType(AcknowledgeType.ack1); //TODO Randomize
                acknowledge.setDescription(AcknowledgeType.ack1.getDescription()); //TODO Randomize
                informationBay.setStateInfo(stateInfo);
                informationBay.setAcknowledge(acknowledge);
                informationBay.setTimestamp(new Date());
                informationBayList.add(informationBay);
            }
        }*/
        ClassLoader classLoader = getClass().getClassLoader();
//        File file = new File(classLoader.getResource("/mock/informationBay.json").getFile());
        ObjectMapper mapper = new ObjectMapper();

        try {
            informationBayList = Arrays.asList(mapper.readValue(new File(PATHNAME), InformationBay[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        blockingQueue = new LinkedBlockingDeque<>();
        //stompClient = new WebSocketStompClient(new SockJsClient(
        //      asList(new WebSocketTransport(new StandardWebSocketClient()))));
        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
    }

    @Test
    public void shouldReceiveAMessageFromTheServer() throws Exception {
        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {
                })
                .get(1, SECONDS);
        session.subscribe(WEBSOCKET_TOPIC + "/" + WebSocketConfiguration.INFORMATION_BAY_TOPIC, new DefaultStompFrameHandler());
        session.subscribe(WEBSOCKET_TOPIC + "/" + WebSocketConfiguration.ACKNOWLEDGE_TOPIC, new DefaultStompFrameHandler());
        for (; ; ) {
            Thread.sleep(DELAY);
            InformationBay[] messageBays = new InformationBay[informationBayList.size()];
            messageBays = informationBayList.toArray(messageBays);
            InformationBay message = messageBays[rand.nextInt(100) % messageBays.length];
            StateType stateType = ZeroHQTContextTransformer.resolveStateType(message.getStateInfo().getStateCode(), message.getAcknowledge());
            message.getStateInfo().setType(stateType);
            ObjectMapper mapper = new ObjectMapper();
            String messageJson = mapper.writeValueAsString(message);
            session.send(WEBSOCKET_TOPIC + "/" + WebSocketConfiguration.INFORMATION_BAY_TOPIC, messageJson.getBytes());
            logger.info(messageJson);
            Assert.assertEquals(messageJson, blockingQueue.poll(1, SECONDS));
            if (message.getAcknowledge() == null) continue;
            String messageJson2 = mapper.writeValueAsString(message);
            session.send(WEBSOCKET_TOPIC + "/" + WebSocketConfiguration.ACKNOWLEDGE_TOPIC, messageJson2.getBytes());
            logger.info(messageJson2);
            Assert.assertEquals(messageJson2, blockingQueue.poll(1, SECONDS));
        }
    }

    class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            blockingQueue.offer(new String((byte[]) o));
        }


    }
}
