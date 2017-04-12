package ua.training.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ua.training.controller.WebSocketChatController;

import javax.annotation.Resource;

/**
 * Created by Adrien on 14.03.2017.
 */
@Configuration
@EnableWebSocket
@PropertySource("classpath:reg.properties")
public class WebSocketConfig extends WebMvcConfigurerAdapter
        implements WebSocketConfigurer {
    @Resource
    private Environment environment;

    @Override
    public void registerWebSocketHandlers(
            WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(getHandler(),
        environment.getProperty("reg.sockurl")).withSockJS();
    }

    @Bean
    public WebSocketChatController getHandler(){
        return new WebSocketChatController();
    }
}
