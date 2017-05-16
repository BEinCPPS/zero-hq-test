package it.eng.zerohqt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.eng.zerohqt.business.model.*;
import it.eng.zerohqt.config.WebSocketConfiguration;
import it.eng.zerohqt.dao.model.AcknowledgeType;
import it.eng.zerohqt.orion.OrionContextConsumer;
import it.eng.zerohqt.orion.OrionContextConsumerExecutor;
import it.eng.zerohqt.orion.client.OrionClient;
import it.eng.zerohqt.orion.client.model.ContextElementList;
import it.eng.zerohqt.orion.client.model.OrionContextElement;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ZeroHqWebSocketFeebackTest {
    @Autowired
    private OrionContextConsumer orionContextConsumer;

    static final String WEBSOCKET_URI = "ws://localhost:8080/websocket";
    static final String WEBSOCKET_TOPIC = WebSocketConfiguration.DEFAULT_CHANNEL;
    static final String PATH_SCALE = "/Users/ascatox/Documents/Sviluppo/workspace_beincpps/zeroHQTest/src/main/resources/mock/feedbackScale.json";
    static final String PATH_FEEDBACK = "/Users/ascatox/Documents/Sviluppo/workspace_beincpps/zeroHQTest/src/main/resources/mock/feedback.json";
    public static final int DELAY = 3000; //milliseconds
    private final Logger logger = Logger.getLogger(ZeroHqWebSocketFeebackTest.class);

    BlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;
    String[] messages;
    Random rand;
    List<FeedbackInfo> feedbackList;

    @Before
    public void setup() {
        feedbackList = new ArrayList<>();
        rand = new Random();
        /*for (int j = 1; j <= 9; j++) {
            FeedbackInfo feedback = new FeedbackInfo();
            feedback.setMeasureId("measure" + j);
            feedback.setValue(105.7 * j);
            feedbackList.add(feedback);
        }*/
        ClassLoader classLoader = getClass().getClassLoader();
//        File file = new File(classLoader.getResource("/mock/informationBay.json").getFile());
        ObjectMapper mapper = new ObjectMapper();
        try {
           // String content = new String(Files.readAllBytes(Paths.get(PATH_SCALE)));
            OrionContextElement orionContextElement = mapper.readValue(new File(PATH_SCALE), OrionContextElement.class);
            final String feedbackScaleContext = orionContextConsumer.createFeedbackScaleContext(orionContextElement);
            logger.info(feedbackScaleContext);
            feedbackList = Arrays.asList(mapper.readValue(new File(PATH_FEEDBACK), FeedbackInfo[].class));
        } catch (Exception e) {
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
        session.subscribe(WEBSOCKET_TOPIC + "/" + WebSocketConfiguration.FEEDBACK_TOPIC, new DefaultStompFrameHandler());
        for (; ; ) {
            Thread.sleep(DELAY);
            FeedbackInfo[] messagesFeedback = new FeedbackInfo[feedbackList.size()];
            messagesFeedback = feedbackList.toArray(messagesFeedback);
            FeedbackInfo message = messagesFeedback[rand.nextInt(100) % messagesFeedback.length];
            ObjectMapper mapper = new ObjectMapper();
            String messageJson = mapper.writeValueAsString(message);
            session.send(WEBSOCKET_TOPIC + "/" + WebSocketConfiguration.FEEDBACK_TOPIC, messageJson.getBytes());
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
