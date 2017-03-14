package it.eng.zerohqt;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eng.zerohqt.business.model.InformationBay;
import it.eng.zerohqt.business.model.StateInfo;
import it.eng.zerohqt.config.WebSocketConfiguration;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
    public static final int DELAY = 5000; //milliseconds
    private final Logger logger = Logger.getLogger(ZeroHqWebSocketTest.class);

    BlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;
    String[] messages;
    Random rand;
    List<InformationBay> informationBayList;

    @Before
    public void setup() {
        informationBayList = new ArrayList<>();
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
                informationBay.setStateInfo(stateInfo);
                informationBay.setTimestamp(new Date());
                informationBayList.add(informationBay);
            }
        }

        rand = new Random();
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(
                asList(new WebSocketTransport(new StandardWebSocketClient()))));
    }

    @Test
    public void shouldReceiveAMessageFromTheServer() throws Exception {
        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {
                })
                .get(1, SECONDS);
        session.subscribe(WEBSOCKET_TOPIC, new DefaultStompFrameHandler());

        for (; ; ) {
            Thread.sleep(DELAY);
            InformationBay[] messageBays = new InformationBay[informationBayList.size()];
            messageBays = informationBayList.toArray(messageBays);
            InformationBay message = messageBays[rand.nextInt(100) % messageBays.length];
            ObjectMapper mapper = new ObjectMapper();
            String messageJson = mapper.writeValueAsString(message);
            session.send(WEBSOCKET_TOPIC, messageJson.getBytes());
            logger.info(messageJson);
            Assert.assertEquals(messageJson, blockingQueue.poll(1, SECONDS));
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
