package it.eng.zerohqt;

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

    static final String informationBay_11 = "{\n" +
            "\t\"stationName\": \"TestStation1\",\n" +
            "\t\"stationDescription\": \"Macchina 1 Box 1\",\n" +
            "\t\"bayCode\": \"teststation:TestStation1_1\",\n" +
            "\t\"bayNumber\": \"1\",\n" +
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

    static final String informationBay_12 = "{\n" +
            "\t\"stationName\": \"TestStation1\",\n" +
            "\t\"stationDescription\": \"Macchina 1 Box 2\",\n" +
            "\t\"bayCode\": \"teststation:TestStation1_2\",\n" +
            "\t\"bayNumber\": \"2\",\n" +
            "\t\"ipAddress\": \"100.100.100.100\",\n" +
            "\t\"notification\": {\n" +
            "\t\t\"stateCode\": \"105\",\n" +
            "\t\t\"statePayload\": \" |N| | |N| |A|0| \",\n" +
            "\t\t\"stateDescription\": \"null\",\n" +
            "\t\t\"acknowledge\": \"null\",\n" +
            "\t\t\"timestamp\": 1488881865344\n" +
            "\t},\n" +
            "\t\"timestamp\": 1488881865344\n" +
            "}";

    static final String informationBay_13 = "{\n" +
            "\t\"stationName\": \"TestStation1\",\n" +
            "\t\"stationDescription\": \"Macchina 1 Box 3\",\n" +
            "\t\"bayCode\": \"teststation:TestStation1_3\",\n" +
            "\t\"bayNumber\": \"3\",\n" +
            "\t\"ipAddress\": \"100.100.100.100\",\n" +
            "\t\"notification\": {\n" +
            "\t\t\"stateCode\": \"106\",\n" +
            "\t\t\"statePayload\": \" |N| | |N| |A|0| \",\n" +
            "\t\t\"stateDescription\": \"null\",\n" +
            "\t\t\"acknowledge\": \"null\",\n" +
            "\t\t\"timestamp\": 1488881865345\n" +
            "\t},\n" +
            "\t\"timestamp\": 1488881865345\n" +
            "}";

    static final String informationBay_14 = "{\n" +
            "\t\"stationName\": \"TestStation1\",\n" +
            "\t\"stationDescription\": \"Macchina 1 Box 4\",\n" +
            "\t\"bayCode\": \"teststation:TestStation1_4\",\n" +
            "\t\"bayNumber\": \"4\",\n" +
            "\t\"ipAddress\": \"100.100.100.100\",\n" +
            "\t\"notification\": {\n" +
            "\t\t\"stateCode\": \"107\",\n" +
            "\t\t\"statePayload\": \" |N| | |N| |A|0| \",\n" +
            "\t\t\"stateDescription\": \"null\",\n" +
            "\t\t\"acknowledge\": \"null\",\n" +
            "\t\t\"timestamp\": 1488881865346\n" +
            "\t},\n" +
            "\t\"timestamp\": 1488881865346\n" +
            "}";

    static final String informationBay_21 = "{\n" +
            "\t\"stationName\": \"TestStation2\",\n" +
            "\t\"stationDescription\": \"Macchina 2 Box 1\",\n" +
            "\t\"bayCode\": \"teststation:TestStation2_1\",\n" +
            "\t\"bayNumber\": \"1\",\n" +
            "\t\"ipAddress\": \"100.100.100.100\",\n" +
            "\t\"notification\": {\n" +
            "\t\t\"stateCode\": \"114\",\n" +
            "\t\t\"statePayload\": \" |N| | |N| |A|0| \",\n" +
            "\t\t\"stateDescription\": \"null\",\n" +
            "\t\t\"acknowledge\": \"null\",\n" +
            "\t\t\"timestamp\": 1488881865343\n" +
            "\t},\n" +
            "\t\"timestamp\": 1488881865343\n" +
            "}";

    static final String informationBay_22 = "{\n" +
            "\t\"stationName\": \"TestStation2\",\n" +
            "\t\"stationDescription\": \"Macchina 2 Box 2\",\n" +
            "\t\"bayCode\": \"teststation:TestStation2_2\",\n" +
            "\t\"bayNumber\": \"2\",\n" +
            "\t\"ipAddress\": \"100.100.100.100\",\n" +
            "\t\"notification\": {\n" +
            "\t\t\"stateCode\": \"115\",\n" +
            "\t\t\"statePayload\": \" |N| | |N| |A|0| \",\n" +
            "\t\t\"stateDescription\": \"null\",\n" +
            "\t\t\"acknowledge\": \"null\",\n" +
            "\t\t\"timestamp\": 1488881865344\n" +
            "\t},\n" +
            "\t\"timestamp\": 1488881865344\n" +
            "}";

    static final String informationBay_23 = "{\n" +
            "\t\"stationName\": \"TestStation2\",\n" +
            "\t\"stationDescription\": \"Macchina 2 Box 3\",\n" +
            "\t\"bayCode\": \"teststation:TestStation2_3\",\n" +
            "\t\"bayNumber\": \"3\",\n" +
            "\t\"ipAddress\": \"100.100.100.100\",\n" +
            "\t\"notification\": {\n" +
            "\t\t\"stateCode\": \"116\",\n" +
            "\t\t\"statePayload\": \" |N| | |N| |A|0| \",\n" +
            "\t\t\"stateDescription\": \"null\",\n" +
            "\t\t\"acknowledge\": \"null\",\n" +
            "\t\t\"timestamp\": 1488881865345\n" +
            "\t},\n" +
            "\t\"timestamp\": 1488881865345\n" +
            "}";

    static final String informationBay_24 = "{\n" +
            "\t\"stationName\": \"TestStation2\",\n" +
            "\t\"stationDescription\": \"Macchina 2 Box 4\",\n" +
            "\t\"bayCode\": \"teststation:TestStation2_4\",\n" +
            "\t\"bayNumber\": \"4\",\n" +
            "\t\"ipAddress\": \"100.100.100.100\",\n" +
            "\t\"notification\": {\n" +
            "\t\t\"stateCode\": \"117\",\n" +
            "\t\t\"statePayload\": \" |N| | |N| |A|0| \",\n" +
            "\t\t\"stateDescription\": \"null\",\n" +
            "\t\t\"acknowledge\": \"null\",\n" +
            "\t\t\"timestamp\": 1488881865346\n" +
            "\t},\n" +
            "\t\"timestamp\": 1488881865346\n" +
            "}";

    static final String informationBay_31 = "{\n" +
            "\t\"stationName\": \"TestStation3\",\n" +
            "\t\"stationDescription\": \"Macchina 3 Box 1\",\n" +
            "\t\"bayCode\": \"teststation:TestStation3_1\",\n" +
            "\t\"bayNumber\": \"1\",\n" +
            "\t\"ipAddress\": \"100.100.100.100\",\n" +
            "\t\"notification\": {\n" +
            "\t\t\"stateCode\": \"124\",\n" +
            "\t\t\"statePayload\": \" |N| | |N| |A|0| \",\n" +
            "\t\t\"stateDescription\": \"null\",\n" +
            "\t\t\"acknowledge\": \"null\",\n" +
            "\t\t\"timestamp\": 1488881865343\n" +
            "\t},\n" +
            "\t\"timestamp\": 1488881865343\n" +
            "}";

    static final String informationBay_32 = "{\n" +
            "\t\"stationName\": \"TestStation3\",\n" +
            "\t\"stationDescription\": \"Macchina 3 Box 2\",\n" +
            "\t\"bayCode\": \"teststation:TestStation3_2\",\n" +
            "\t\"bayNumber\": \"2\",\n" +
            "\t\"ipAddress\": \"100.100.100.100\",\n" +
            "\t\"notification\": {\n" +
            "\t\t\"stateCode\": \"125\",\n" +
            "\t\t\"statePayload\": \" |N| | |N| |A|0| \",\n" +
            "\t\t\"stateDescription\": \"null\",\n" +
            "\t\t\"acknowledge\": \"null\",\n" +
            "\t\t\"timestamp\": 1488881865344\n" +
            "\t},\n" +
            "\t\"timestamp\": 1488881865344\n" +
            "}";

    static final String informationBay_33 = "{\n" +
            "\t\"stationName\": \"TestStation3\",\n" +
            "\t\"stationDescription\": \"Macchina 3 Box 3\",\n" +
            "\t\"bayCode\": \"teststation:TestStation3_3\",\n" +
            "\t\"bayNumber\": \"2\",\n" +
            "\t\"ipAddress\": \"100.100.100.100\",\n" +
            "\t\"notification\": {\n" +
            "\t\t\"stateCode\": \"126\",\n" +
            "\t\t\"statePayload\": \" |N| | |N| |A|0| \",\n" +
            "\t\t\"stateDescription\": \"null\",\n" +
            "\t\t\"acknowledge\": \"null\",\n" +
            "\t\t\"timestamp\": 1488881865345\n" +
            "\t},\n" +
            "\t\"timestamp\": 1488881865345\n" +
            "}";

    static final String informationBay_34 = "{\n" +
            "\t\"stationName\": \"TestStation3\",\n" +
            "\t\"stationDescription\": \"Macchina 3 Box 4\",\n" +
            "\t\"bayCode\": \"teststation:TestStation3_4\",\n" +
            "\t\"bayNumber\": \"4\",\n" +
            "\t\"ipAddress\": \"100.100.100.100\",\n" +
            "\t\"notification\": {\n" +
            "\t\t\"stateCode\": \"127\",\n" +
            "\t\t\"statePayload\": \" |N| | |N| |A|0| \",\n" +
            "\t\t\"stateDescription\": \"null\",\n" +
            "\t\t\"acknowledge\": \"null\",\n" +
            "\t\t\"timestamp\": 1488881865346\n" +
            "\t},\n" +
            "\t\"timestamp\": 1488881865346\n" +
            "}";

    BlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;
    String[] messages;
    Random rand;

    @Before
    public void setup() {
        messages = new String[12];
        //WARNING !!! ADD NEW MESSAGES
        messages[0] = informationBay_11;
        messages[1] = informationBay_12;
        messages[2] = informationBay_13;
        messages[3] = informationBay_14;
        messages[4] = informationBay_21;
        messages[5] = informationBay_22;
        messages[6] = informationBay_23;
        messages[7] = informationBay_24;
        messages[8] = informationBay_31;
        messages[9] = informationBay_32;
        messages[10] = informationBay_33;
        messages[11] = informationBay_34;
        //WARNING !!! ADD NEW MESSAGES

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
            String message = messages[rand.nextInt(100) % messages.length];
            session.send(WEBSOCKET_TOPIC, message.getBytes());
            logger.info(message);

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
