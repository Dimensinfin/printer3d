package org.dimensinfin.printer3d.backend.accounting.request.rest;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;

import org.dimensinfin.core.exception.DimensinfinError;
import org.dimensinfin.printer3d.backend.Printer3DApplication;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.15.1
 */
public class AccountingRestErrors {
	public static DimensinfinError errorEXPORTGENERATIONEXCEPTION( final String exceptionMessage ) {
		return new DimensinfinError.Builder()
				.withErrorName( "CVS_EXPORT_GENERATION" )
				.withErrorCode( Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX + ".data.generation" )
				.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
				.withMessage( MessageFormat.format( "Exception detected while generating the CVS Report Data Export. {0}", exceptionMessage ) )
				.build();
	}

// - C O N S T R U C T O R S
	private AccountingRestErrors() {}
}