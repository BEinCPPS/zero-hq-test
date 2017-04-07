package it.eng.zerohqt.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

    private final Logger logger = Logger.getLogger(WebSocketConfiguration.class);
    public static String INFORMATION_BAY_CHANNEL = "/informationBay";
    public static String ACKNOWLEDGE_CHANNEL = "/acknowledge";


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(INFORMATION_BAY_CHANNEL); //TODO Constants via application.properties
        config.enableSimpleBroker(ACKNOWLEDGE_CHANNEL); //TODO Constants via application.properties
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .setAllowedOrigins("*") //TODO
                .withSockJS();
    }
}
