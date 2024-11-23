package org.dimensinfin.poc.acceptance.steps;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.dimensinfin.Printer3DWorld;

public abstract class StepSupport {
	private static final Map<String, UUID> machineIdentifiers = new HashMap<>();

	static {
		machineIdentifiers.put( "Ender 3 Pro - A", UUID.fromString( "e18aa442-19cd-4b08-8ed0-9f1917821fac" ) );
		machineIdentifiers.put( "Ender 3 Pro - B", UUID.fromString( "d55a5ca6-b1f5-423c-9a47-007439534744" ) );
	}

	protected Printer3DWorld printer3DWorld;

	// - C O N S T R U C T O R S
	public StepSupport( @NotNull final Printer3DWorld printer3DWorld ) {
		this.printer3DWorld = printer3DWorld;
	}

	public UUID findMachineByLabel( final String label ) {
		return machineIdentifiers.get( label );
	}
}
