package org.example.study_group_service.config;

import org.example.study_group_service.service.handler.PersonWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.time.Duration;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/ws/person")
                .setHandshakeHandler(handshakeHandler());
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new PersonWebSocketHandler();
    }

    @Bean
    public DefaultHandshakeHandler handshakeHandler() {
        JettyRequestUpgradeStrategy strategy = new JettyRequestUpgradeStrategy();
        strategy.addWebSocketConfigurer(configurable -> {
            configurable.setInputBufferSize(8192);
            configurable.setIdleTimeout(Duration.ofSeconds(600));
        });
        return new DefaultHandshakeHandler(strategy);
    }
}
