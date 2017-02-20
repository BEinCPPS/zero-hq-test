package it.eng.zerohqt.config;

import it.eng.zerohqt.security.AuthoritiesConstants;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

    private final Logger logger = Logger.getLogger(WebSocketConfiguration.class);
    public static final String IP_ADDRESS = "IP_ADDRESS";

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/websocket");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/topic")
//            .setHandshakeHandler(new DefaultHandshakeHandler() {
//                @Override
//                protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
//                    Principal principal = request.getPrincipal();
//                    if (principal == null) {
//                        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
//                        principal = new AnonymousAuthenticationToken("WebSocketConfiguration", "anonymous", authorities);
//                    }
//                    return principal;
//                }
//            })
                .withSockJS();
        //.setInterceptors(httpSessionHandshakeInterceptor());
    }

//    @Bean
//    public HandshakeInterceptor httpSessionHandshakeInterceptor() {
//        return new HandshakeInterceptor() {
//
//            @Override
//            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//                if (request instanceof ServletServerHttpRequest) {
//                    ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//                    attributes.put(IP_ADDRESS, servletRequest.getRemoteAddress());
//                }
//                return true;
//            }
//
//            @Override
//            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
//
//            }
//        };
//    }
}
