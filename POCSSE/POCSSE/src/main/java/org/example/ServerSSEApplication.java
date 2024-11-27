package org.example;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;

@SpringBootApplication(exclude = { RedisReactiveAutoConfiguration.class })
public class ServerSSEApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ServerSSEApplication.class);
		app.setDefaultProperties( Collections.singletonMap("server.port", "8081"));
		app.run(args);
	}

//	@Bean
//	public SecurityWebFilterChain sseServerSpringSecurityFilterChain(ServerHttpSecurity http) {
//		http.authorizeExchange()
//				.anyExchange()
//				.permitAll();
//		return http.build();
//	}

}