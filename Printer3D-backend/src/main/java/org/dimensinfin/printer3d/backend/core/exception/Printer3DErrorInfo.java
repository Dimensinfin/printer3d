package org.dimensinfin.printer3d.backend.core.exception;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import org.dimensinfin.common.exception.DimensinfinError;
import org.dimensinfin.common.exception.DimensinfinErrorInfo;

import static org.dimensinfin.printer3d.backend.Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX;

public class Printer3DErrorInfo extends DimensinfinErrorInfo {
	public static DimensinfinError REQUEST_NOT_FOUND( final UUID requestId ) {
		return new DimensinfinError.Builder()
				.withErrorName( "REQUEST_NOT_FOUND" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".defined.repository.logic" )
				.withHttpStatus( HttpStatus.CONFLICT )
				.withMessage( MessageFormat.format( "Request record with id [{0}] not found at the repository.", requestId ) )
				.build();
	}

	public static DimensinfinError INVENTORY_STORE_REPOSITORY_FAILURE( final UUID identifier, final SQLException sqle ) {
		if (null != sqle.getCause())
			return new DimensinfinError.Builder()
					.withErrorName( "INVENTORY_STORE_REPOSITORY_FAILURE" )
					.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".persistence.sql.error" )
					.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
					.withMessage( MessageFormat.format( "Request record with id [{0}] not found at the repository. {1}",
							identifier,
							sqle.getMessage() ) )
					.withCause( Optional.of( sqle.getCause().toString() ) )
					.build();
		else
			return new DimensinfinError.Builder()
					.withErrorName( "INVENTORY_STORE_REPOSITORY_FAILURE" )
					.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".persistence.sql.error" )
					.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
					.withMessage( MessageFormat.format( "Request record with id [{0}] not found at the repository. {1}",
							identifier,
							sqle.getMessage() ) )
					.build();
	}

	// - C O N S T R U C T O R S
	protected Printer3DErrorInfo() { }
}
