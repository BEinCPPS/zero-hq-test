package it.eng.zerohqt;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eng.zerohqt.business.model.InformationBay;
import it.eng.zerohqt.business.transformer.ZeroHQTContextTransformer;
import it.eng.zerohqt.config.WebSocketConfiguration;
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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

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
    public static final String PATHNAME = "/mock/informationBay.json";
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
        ObjectMapper mapper = new ObjectMapper();
        try {
            String pathInformationBay = getClass().getResource(PATHNAME).getPath();
            informationBayList = Arrays.asList(mapper.readValue(new File(pathInformationBay), InformationBay[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        blockingQueue = new LinkedBlockingDeque<>();
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
