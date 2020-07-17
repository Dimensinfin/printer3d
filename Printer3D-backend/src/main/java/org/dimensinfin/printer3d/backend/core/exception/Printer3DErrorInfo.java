package org.dimensinfin.printer3d.backend.core.exception;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import org.dimensinfin.common.exception.DimensinfinError;

import static org.dimensinfin.printer3d.backend.Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX;

public class Printer3DErrorInfo {
//	PART_REPOSITORY_CONFLICT( HttpStatus.CONFLICT,
//	                          APPLICATION_ERROR_CODE_PREFIX + ".constraintviolation",
//			"The Part [{0}] is rejected because constraint violation. {0}" ),
//	REQUEST_PROCESSING_FAILURE( HttpStatus.INTERNAL_SERVER_ERROR,
//	                            APPLICATION_ERROR_CODE_PREFIX + ".logic.exception",
//			"The request [{0}] has assigned a Part with id [{1}] that does not exist on the Part repository." );
	public static DimensinfinError INVENTORY_STORE_REPOSITORY_FAILURE( final SQLException sqle ) {
		if (null != sqle.getCause())
			return new DimensinfinError.Builder()
					.withErrorName( "INVENTORY_STORE_REPOSITORY_FAILURE" )
					.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".persistence.sql.error" )
					.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
					.withMessage( MessageFormat.format( "There is an SQL error on the Inventory repository. {0}. SQL cause: {1}",
							sqle.getMessage(),
							sqle.getCause().toString() ) )
					.build();
		else
			return new DimensinfinError.Builder()
					.withErrorName( "INVENTORY_STORE_REPOSITORY_FAILURE" )
					.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".persistence.sql.error" )
					.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
					.withMessage( MessageFormat.format( "There is an SQL error on the Inventory repository. {0}.",
							sqle.getMessage() ) )
					.build();
	}

	public static DimensinfinError REQUEST_STORE_REPOSITORY_FAILURE( final SQLException sqle ) {
		if (null != sqle.getCause())
			return new DimensinfinError.Builder()
					.withErrorName( "REQUEST_STORE_REPOSITORY_FAILURE" )
					.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".persistence.sql.error" )
					.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
					.withMessage( MessageFormat.format( "There is an SQL error on the Requests repository. {0}. SQL cause: {1}",
							sqle.getMessage(),
							sqle.getCause().toString() ) )
					.build();
		else
			return new DimensinfinError.Builder()
					.withErrorName( "REQUEST_STORE_REPOSITORY_FAILURE" )
					.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".persistence.sql.error" )
					.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
					.withMessage( MessageFormat.format( "There is an SQL error on the Requests repository. {0}.",
							sqle.getMessage() ) )
					.build();
	}

	public static DimensinfinError REQUEST_CANNOT_BE_FULFILLED( final UUID requestId ) {
		return new DimensinfinError.Builder()
				.withErrorName( "REQUEST_CANNOT_BE_FULFILLED" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".logic.exception" )
				.withHttpStatus( HttpStatus.CONFLICT )
				.withMessage( MessageFormat.format(
						"Request record with id [{0}] has not enough resources to be completed. Obsolete state.",
						requestId ) )
				.build();
	}
	public static DimensinfinError MACHINE_NOT_FOUND( final String machineIdentification ) {
		return new DimensinfinError.Builder()
				.withErrorName( "MACHINE_NOT_FOUND" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".notfound" )
				.withHttpStatus( HttpStatus.NOT_FOUND )
				.withMessage( MessageFormat.format( "Machine with label or id [{0}] not found at the repository.", machineIdentification ) )
				.build();
	}

	public static DimensinfinError EXPECTED_COIL_NOT_FOUND() {
		return new DimensinfinError.Builder()
				.withErrorName( "COIL_NOT_FOUND" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".logic.exception" )
				.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
				.withMessage( "Coil not found when processing a new build job. Check database records for the selected Part." )
				.build();
	}

	public static DimensinfinError MODEL_ALREADY_EXISTS( final UUID modelId ) {
		return new DimensinfinError.Builder()
				.withErrorName( "MODEL_ALREADY_EXISTS" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".already.exists" )
				.withHttpStatus( HttpStatus.CONFLICT )
				.withMessage( MessageFormat.format( "Model with id [{0}] already exists. Use the Update endpoint.", modelId ) )
				.build();
	}

	public static DimensinfinError MODEL_NOT_FOUND( final UUID modelId ) {
		return new DimensinfinError.Builder()
				.withErrorName( "MODEL_NOT_FOUND" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".notfound" )
				.withHttpStatus( HttpStatus.NOT_FOUND )
				.withMessage( MessageFormat.format( "Model with id [{0}] not found at the repository.", modelId ) )
				.build();
	}

	public static DimensinfinError COIL_ALREADY_EXISTS( final UUID coilId ) {
		return new DimensinfinError.Builder()
				.withErrorName( "COIL_ALREADY_EXISTS" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".already.exists" )
				.withHttpStatus( HttpStatus.CONFLICT )
				.withMessage( MessageFormat.format(
						"A Coil with id [{0}] already exists at the repository. This should not be possible and means a development defect.",
						coilId ) )
				.build();
	}

	public static DimensinfinError COIL_NOT_FOUND( final UUID coilId ) {
		return new DimensinfinError.Builder()
				.withErrorName( "COIL_NOT_FOUND" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".notfound" )
				.withHttpStatus( HttpStatus.NOT_FOUND )
				.withMessage( MessageFormat.format( "Coil with id [{0}] not found at the repository.", coilId ) )
				.build();
	}
	public static DimensinfinError STOCK_PROCESSING_FAILURE( final UUID partId ) {
		return new DimensinfinError.Builder()
				.withErrorName( "STOCK_PROCESSING_FAILURE" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".logic.exception" )
				.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
				.withMessage( MessageFormat.format( "Part with id [{0}] is not found at the stock list.", partId ) )
				.build();
	}

	// - C O N S T R U C T O R S
	protected Printer3DErrorInfo() { }
}
