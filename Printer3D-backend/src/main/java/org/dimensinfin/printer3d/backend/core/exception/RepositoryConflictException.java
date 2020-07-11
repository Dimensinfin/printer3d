package org.dimensinfin.printer3d.backend.core.exception;

import org.springframework.http.HttpStatus;

import org.dimensinfin.common.exception.DimensinfinError;
import org.dimensinfin.common.exception.DimensinfinRuntimeException;

/**
 * Used to report exceptions related to interactions with the repository that do not result on the expected result.
 * Examples of this are duplicated records, records not found or any other application constraint logic.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.8.0
 */
public class RepositoryConflictException extends DimensinfinRuntimeException {
	// - C O N S T R U C T O R S
	public RepositoryConflictException( final DimensinfinError error ) {
		super( error.setStatus( HttpStatus.CONFLICT ) );
	}

	public RepositoryConflictException( final DimensinfinError error, final String cause ) {
		super( error.setStatus( HttpStatus.CONFLICT ).setCause( cause ) );
	}
}
