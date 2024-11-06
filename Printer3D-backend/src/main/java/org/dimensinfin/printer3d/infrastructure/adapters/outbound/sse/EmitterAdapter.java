package org.dimensinfin.printer3d.infrastructure.adapters.outbound.sse;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import org.dimensinfin.printer3d.application.ports.outbound.EmitterPort;
import org.dimensinfin.printer3d.domain.EmitterCodes;
import org.dimensinfin.printer3d.domain.EmitterEventFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * Adapter to contain the SSE emitter and the logic to be able to send SSE events to clients.
 *
 * @author adamantinoo.git@gmail.com
 * @since 0.20
 */
@Slf4j
public class EmitterAdapter implements EmitterPort {
	private static final Long HOURS = 1 * 30 * 60 * 1000L;
	private static final Long SECONDS = 1 * 1000L;
	private static final Long TIMEOUT_WAIT = 10L;
	private static SseEmitter emitter;
	private final Lock lock = new ReentrantLock();

	public EmitterAdapter() {
	}

	@Override
	public SseEmitter getEmitter() {
		if ( Objects.isNull( emitter ) ) emitter = createEmitter();
		return emitter;
	}

	@Override
	public void emitMessage( final EmitterCodes code ) {
		code.handleEvent( this.getEmitter() );
	}

	private SseEmitter createEmitter() {
		final SseEmitter newEmitter = new SseEmitter( TIMEOUT_WAIT * SECONDS );
		newEmitter.onCompletion( this::signalCompletion );
		newEmitter.onTimeout( this::signalTimeout );
		newEmitter.onError( e -> {
			log.error( "Emitter error: " + e.getMessage() );
			resetEmitter();
		} );
		this.sendWelcomeMessage();
		return newEmitter;
	}

	private void sendWelcomeMessage() {
		new Timer().schedule(
				new java.util.TimerTask() {
					@Override
					public void run() {
						try {
							if ( Objects.nonNull( emitter ) ) {
								emitter.send( SseEmitter.event()
										.id( EmitterCodes.WELCOME.name() )
										.name( EmitterCodes.WELCOME.name() )
										.data( EmitterEventFactory.getEventByCode( EmitterCodes.WELCOME ).toString() ) );
								log.info( "Sending WELCOME messsage" );
							}
						} catch (final IOException exception) {
							log.error( exception.getMessage() );
						}
					}
				},
				2 * SECONDS
		);
		final ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
		sseMvcExecutor.execute( () -> {
//			synchronized (emitter) {
				try {
					for (int i = 0; true; i++) {
						if ( Objects.nonNull( emitter ) ) {
							emitter.send( SseEmitter.event()
									.id( "ITERATION->" + i )
									.name( EmitterCodes.HEARTBEAT.name() )
									.data( EmitterEventFactory.getEventByCode( EmitterCodes.HEARTBEAT ).toString() ) );
							log.info( "Sending HEARBEAT messsage" );
							Thread.sleep( 5 * SECONDS );
						}
					}
				} catch (Exception ex) {
					emitter.completeWithError( ex );
				}
//			}
		} );
	}

	private void signalCompletion() {
		log.info( "Event emitter has completed. Closed" );
		this.resetEmitter();
	}

	private void signalTimeout() {
		log.info( "Event emitter timed out after: " + TIMEOUT_WAIT + " seconds." );
		//		this.resetEmitter();
	}

	private void resetEmitter() {
		log.info( "Event emitter destroyed." );
		lock.lock();
		try {
			emitter.complete();
			emitter = null;
		} finally {
			lock.unlock();
		}
	}

}
