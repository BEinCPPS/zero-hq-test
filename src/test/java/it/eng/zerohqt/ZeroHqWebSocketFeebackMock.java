package it.eng.zerohqt;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eng.zerohqt.business.model.FeedbackAcknowledge;
import it.eng.zerohqt.business.model.FeedbackInfo;
import it.eng.zerohqt.config.WebSocketConfiguration;
import it.eng.zerohqt.orion.OrionContextConsumer;
import it.eng.zerohqt.orion.client.model.OrionContextElement;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ZeroHqWebSocketFeebackMock {
    @Autowired
    private OrionContextConsumer orionContextConsumer;

    static final String WEBSOCKET_URI = "ws://localhost:8080/websocket";
    static final String WEBSOCKET_TOPIC = WebSocketConfiguration.DEFAULT_CHANNEL;
    static final String PATH_SCALE = "/mock/feedbackScale.json";
    static final String PATH_FEEDBACK = "/mock/feedback.json";
    static final int DELAY = 3000; //milliseconds
    final Logger logger = Logger.getLogger(ZeroHqWebSocketFeebackMock.class);

    BlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;
    String[] messages;
    Random rand;
    List<FeedbackInfo> feedbackList;
    List<FeedbackAcknowledge> feedbackAcknowledges;

    @Before
    public void setup() {
        feedbackList = new ArrayList<>();
        rand = new Random();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String pathScale = getClass().getResource(PATH_SCALE).getPath();
            OrionContextElement orionContextElement = mapper.readValue(new File(pathScale), OrionContextElement.class);
            final String feedbackScaleContext = orionContextConsumer.createFeedbackScaleContext(orionContextElement);
            logger.info(feedbackScaleContext);
            String pathFeedback = getClass().getResource(PATH_FEEDBACK).getPath();
            feedbackList = Arrays.asList(mapper.readValue(new File(pathFeedback), FeedbackInfo[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
    }

    @Test
    public void shouldReceiveAFeedbackFromTheServer() throws Exception {
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
            message.setTimestamp(new Date());
            ObjectMapper mapper = new ObjectMapper();
            String messageJson = mapper.writeValueAsString(message);
            session.send(WEBSOCKET_TOPIC + "/" + WebSocketConfiguration.FEEDBACK_TOPIC, messageJson.getBytes());
            logger.info(messageJson);
            //Assert.assertEquals(messageJson, blockingQueue.poll(1, SECONDS));
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
