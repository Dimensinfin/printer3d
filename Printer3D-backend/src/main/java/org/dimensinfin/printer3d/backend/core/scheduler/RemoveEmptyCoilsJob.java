package org.dimensinfin.printer3d.backend.core.scheduler;

import java.text.MessageFormat;
import javax.validation.constraints.NotNull;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.inventory.coil.rest.v2.CoilServiceV2;

/**
 * Periodic job (period is configured into properties) to remove Coils that have expired their destruction time.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.15.0
 */
@Service
@ConditionalOnProperty(name = "spring.scheduler.removecoils.active")
public class RemoveEmptyCoilsJob {
	private final CoilServiceV2 coilServiceV2;

	// - C O N S T R U C T O R S
	private RemoveEmptyCoilsJob( @NotNull final CoilServiceV2 coilServiceV2 ) {
		this.coilServiceV2 = coilServiceV2;
	}

	@Scheduled(fixedRateString = "${spring.scheduler.removecoils.scheduletime}")
	public void process() {
		LogWrapper.enter();
		final int deleteCoilsCount = this.coilServiceV2.removeExpiredCoils();
		LogWrapper.exit( MessageFormat.format( "Deleted {0} coils on this iteration.", deleteCoilsCount ) );
	}
}