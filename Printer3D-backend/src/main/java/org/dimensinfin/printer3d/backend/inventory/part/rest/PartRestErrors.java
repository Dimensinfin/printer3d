package org.dimensinfin.printer3d.backend.inventory.part.rest;

import java.text.MessageFormat;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import org.dimensinfin.core.exception.DimensinfinError;
import org.dimensinfin.printer3d.backend.Printer3DApplication;

public class PartRestErrors {
	public static DimensinfinError errorPARTREPOSITORYCONFLICT( final UUID partId, final String constraintMessage ) {
		return new DimensinfinError.Builder()
				.withErrorName( "PART_REPOSITORY_CONFLICT" )
				.withErrorCode( Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX + ".constraintviolation" )
				.withHttpStatus( HttpStatus.CONFLICT )
				.withMessage( MessageFormat.format( "The Part [{0}] is rejected because constraint violation. {1}", partId, constraintMessage ) )
				.build();
	}

	public static DimensinfinError errorPARTNOTFOUND( final UUID partId ) {
		return new DimensinfinError.Builder()
				.withErrorName( "PART_NOT_FOUND" )
				.withErrorCode( Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX + ".notfound" )
				.withHttpStatus( HttpStatus.NOT_FOUND )
				.withMessage( MessageFormat.format( "Part with id [{0}] not found at the repository.", partId ) )
				.build();
	}

	public static DimensinfinError errorPARTALREADYEXISTS( final UUID partId ) {
		return new DimensinfinError.Builder()
				.withErrorName( "PART_ALREADY_EXISTS" )
				.withErrorCode( Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX + ".already.exists" )
				.withHttpStatus( HttpStatus.CONFLICT )
				.withMessage( MessageFormat.format( "Part with id [{0}] already exists. Use the Update endpoint.", partId ) )
				.build();
	}

	// - C O N S T R U C T O R S
	private PartRestErrors() {}
}