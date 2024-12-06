package org.dimensinfin.poc.backend_pocws.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker( final MessageBrokerRegistry config ) {
		config.setApplicationDestinationPrefixes( "/app" );
		config.enableSimpleBroker( "/topic" );
	}

	@Override
	public void registerStompEndpoints(final  StompEndpointRegistry registry ) {
		log.info( "Registering stomp endpoints..." );
		registry.addEndpoint( "/printer-sockets" ).setAllowedOriginPatterns( "*" ).withSockJS();
		log.info("Registered endpoint: /printer-sockets");
	}

}