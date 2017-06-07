package it.eng.zerohqt;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eng.zerohqt.business.model.FeedbackAcknowledge;
import it.eng.zerohqt.business.model.FeedbackInfo;
import it.eng.zerohqt.business.model.InformationBay;
import it.eng.zerohqt.config.WebSocketConfiguration;
import org.apache.log4j.Logger;
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
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ZeroHqWebSocketFeebackAcknowledgeMock {
    static final String WEBSOCKET_URI = "ws://localhost:8080/websocket";
    static final String WEBSOCKET_TOPIC = WebSocketConfiguration.DEFAULT_CHANNEL;
    static final String PATH_FEEDBACK_ACKNOWLEDGE = "/mock/feedbackAcknowledge.json";
    static final int DELAY_LONG = 1000;

    final Logger logger = Logger.getLogger(ZeroHqWebSocketFeebackAcknowledgeMock.class);

    BlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;
    Random rand;
    List<FeedbackInfo> feedbackList;
    List<FeedbackAcknowledge> feedbackAcknowledges;

    @Before
    public void setup() {
        feedbackList = new ArrayList<>();
        rand = new Random();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String pathFeedbackAck = getClass().getResource(PATH_FEEDBACK_ACKNOWLEDGE).getPath();
            feedbackAcknowledges = Arrays.asList(mapper.readValue(new File(pathFeedbackAck), FeedbackAcknowledge[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
    }

    @Test
    public void shouldReceiveAnAckFromTheServer() throws Exception {
        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {
                })
                .get(1, SECONDS);
        session.subscribe(WEBSOCKET_TOPIC + "/" + WebSocketConfiguration.ACKNOWLEDGE_TOPIC, new DefaultStompFrameHandler());
        int counter = 0;
        for (; ; ) {
            Thread.sleep(DELAY_LONG);
            FeedbackAcknowledge[] messagesFeedback = new FeedbackAcknowledge[feedbackAcknowledges.size()];
            messagesFeedback = feedbackAcknowledges.toArray(messagesFeedback);
            FeedbackAcknowledge feedbackAcknowledge = messagesFeedback[rand.nextInt(100) % messagesFeedback.length];
            feedbackAcknowledge.setTimestamp(new Date());
            InformationBay informationBay = new InformationBay();
            informationBay.setAcknowledge(feedbackAcknowledge);
            ObjectMapper mapper = new ObjectMapper();
            String messageJson = mapper.writeValueAsString(informationBay);
            if (counter > 0) continue;
                session.send(WEBSOCKET_TOPIC + "/" + WebSocketConfiguration.ACKNOWLEDGE_TOPIC, messageJson.getBytes());
            counter++;
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
