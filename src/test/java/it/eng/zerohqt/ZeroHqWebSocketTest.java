package it.eng.zerohqt;

import it.eng.zerohqt.config.WebSocketConfiguration;
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

    static final String informationBay_1 = "{\n" +
            "\t\"stationName\": \"TestStation1\",\n" +
            "\t\"stationDescription\": \"Macchina 1 Box 1\",\n" +
            "\t\"bayCode\": \"teststation:TestStation1_1\",\n" +
            "\t\"ipAddress\": \"100.100.100.100\",\n" +
            "\t\"notification\": {\n" +
            "\t\t\"stateCode\": \"104\",\n" +
            "\t\t\"statePayload\": \" |N| | |N| |A|0| \",\n" +
            "\t\t\"stateDescription\": \"null\",\n" +
            "\t\t\"acknowledge\": \"null\",\n" +
            "\t\t\"timestamp\": 1488881865343\n" +
            "\t},\n" +
            "\t\"timestamp\": 1488881865343\n" +
            "}";

    static final String informationBay_2 = "{\n" +
            "\t\"stationName\": \"TestStation2\",\n" +
            "\t\"stationDescription\": \"Macchina 1 Box 1\",\n" +
            "\t\"bayCode\": \"teststation:TestStation2_1\",\n" +
            "\t\"ipAddress\": \"100.100.100.100\",\n" +
            "\t\"notification\": {\n" +
            "\t\t\"stateCode\": \"104\",\n" +
            "\t\t\"statePayload\": \" |N| | |N| |A|0| \",\n" +
            "\t\t\"stateDescription\": \"null\",\n" +
            "\t\t\"acknowledge\": \"null\",\n" +
            "\t\t\"timestamp\": 1488881865343\n" +
            "\t},\n" +
            "\t\"timestamp\": 1488881865343\n" +
            "}";

    static final String informationBay_3 = "{\n" +
            "\t\"stationName\": \"TestStation3\",\n" +
            "\t\"stationDescription\": \"Macchina 1 Box 1\",\n" +
            "\t\"bayCode\": \"teststation:TestStation3_1\",\n" +
            "\t\"ipAddress\": \"100.100.100.100\",\n" +
            "\t\"notification\": {\n" +
            "\t\t\"stateCode\": \"104\",\n" +
            "\t\t\"statePayload\": \" |N| | |N| |A|0| \",\n" +
            "\t\t\"stateDescription\": \"null\",\n" +
            "\t\t\"acknowledge\": \"null\",\n" +
            "\t\t\"timestamp\": 1488881865343\n" +
            "\t},\n" +
            "\t\"timestamp\": 1488881865343\n" +
            "}";


    BlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;
    String[] messages;
    Random rand;

    @Before
    public void setup() {
        messages = new String[3];
        messages[0] = informationBay_1;
        messages[1] = informationBay_2;
        messages[2] = informationBay_3;
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
            Thread.sleep(5000);
            String message = messages[rand.nextInt(100) % messages.length];
            session.send(WEBSOCKET_TOPIC, message.getBytes());

            Assert.assertEquals(message, blockingQueue.poll(1, SECONDS));
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
