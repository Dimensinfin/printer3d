package org.dimensinfin.poc.backend_pocws.infrastructure.adapters.inbound;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import jakarta.validation.constraints.NotNull;

@Controller
public class PartsStompApiImplementation {
	private SimpMessagingTemplate simpMessagingTemplate;

	public PartsStompApiImplementation( @NotNull final SimpMessagingTemplate simpMessagingTemplate ) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	@Scheduled(fixedRate = 3000)
	public void sendTicks() {
		simpMessagingTemplate.convertAndSend( "/topic/ticks", getStockTicks() );
	}

	private Map<String, Integer> getStockTicks() {
		Map<String, Integer> ticks = new HashMap<>();
		ticks.put( "AAPL", getRandomTick() );
		ticks.put( "GOOGL", getRandomTick() );
		ticks.put( "MSFT", getRandomTick() );
		ticks.put( "TSLA", getRandomTick() );
		ticks.put( "AMZN", getRandomTick() );
		ticks.put( "HPE", getRandomTick() );

		return ticks;
	}

	private int getRandomTick() {
		return ThreadLocalRandom.current().nextInt( -100, 100 + 1 );
	}
}
