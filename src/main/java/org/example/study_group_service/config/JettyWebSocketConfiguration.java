package org.example.study_group_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class JettyWebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private final DefaultHandshakeHandler handshakeHandler;

    public JettyWebSocketConfiguration(DefaultHandshakeHandler handshakeHandler) {
        this.handshakeHandler = handshakeHandler;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setHandshakeHandler(handshakeHandler).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.setApplicationDestinationPrefixes("/person");
        registry.enableSimpleBroker("/topic");
    }

}
