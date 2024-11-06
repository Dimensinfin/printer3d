package org.example;

import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/sse-server")
public class SSEController {
	@GetMapping("/stream-sse-mvc")
	@CrossOrigin
	public SseEmitter streamSseMvc() {
		SseEmitter emitter = new SseEmitter();
		log.info( "Created event emitter" );
		ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
		sseMvcExecutor.execute(() -> {
			try {
				for (int i = 0; true; i++) {
					SseEmitter.SseEventBuilder event = SseEmitter.event()
							.data("SSE MVC - " + LocalTime.now().toString())
							.id(String.valueOf(i))
							.name("sse event - mvc");
					emitter.send(event);
					log.info( "Sent event SSE" );
					Thread.sleep(5000);
				}
			} catch (Exception ex) {
				emitter.completeWithError(ex);
				log.error( ex.getMessage() );
			}
		});
		return emitter;
	}
}
