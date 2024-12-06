package org.dimensinfin.poc.backend_pocws.infrastructure.adapters.outbound.websockets;

import java.time.Instant;
import java.util.UUID;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.dimensinfin.poc.backend_pocws.domain.Part;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class MessageSenderImplementation {
			private final SimpMessagingTemplate messagingTemplate;

		public MessageSenderImplementation (SimpMessagingTemplate messagingTemplate) {
			this.messagingTemplate = messagingTemplate;
		}

		// some code here...

		@Scheduled(fixedDelay = 5000)
		public void longRunningTask() {
			log.info("Sending periodic message");

			final Part message = Part.builder()
					.id( UUID.randomUUID() )
					.name( "Message" )
					.build();
			messagingTemplate.convertAndSend("/topic", message );
		}
}
