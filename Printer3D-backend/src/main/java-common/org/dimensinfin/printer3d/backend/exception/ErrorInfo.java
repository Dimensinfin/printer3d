package org.dimensinfin.printer3d.backend.exception;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;

import static org.dimensinfin.printer3d.backend.Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.12.0
 */
public enum ErrorInfo {
	RUNTIME_INTERNAL_ERROR( HttpStatus.INTERNAL_SERVER_ERROR,
			APPLICATION_ERROR_CODE_PREFIX + ".exception.runtime",
			"{0}" ),
	INVALID_REQUEST_STRUCTURE( HttpStatus.BAD_REQUEST,
			APPLICATION_ERROR_CODE_PREFIX + ".exception.request.validation",
			"The request is not valid. {0}" ),
	INVENTORY_STORE_REPOSITORY_FAILURE( HttpStatus.INTERNAL_SERVER_ERROR,
			APPLICATION_ERROR_CODE_PREFIX + ".persistence.sql.error",
			"There is an SQL error on the Inventory repository. {0}. Cause: {1}" ),
	PART_ALREADY_EXISTS( HttpStatus.CONFLICT,
			APPLICATION_ERROR_CODE_PREFIX + ".already.exists",
			"The Part [{0}] already exists. Use the Update endpoint." ),
	ROLL_ALREADY_EXISTS( HttpStatus.CONFLICT,
			APPLICATION_ERROR_CODE_PREFIX + ".already.exists",
			"The Roll [{0}] already exists at the repository. This should not be possible and means a development defect." ),
	PART_NOT_FOUND( HttpStatus.NOT_FOUND,
			APPLICATION_ERROR_CODE_PREFIX + ".notfound",
			"The Part [{0}] not found at the repository." ),
	MACHINE_NOT_FOUND( HttpStatus.NOT_FOUND,
			APPLICATION_ERROR_CODE_PREFIX + ".notfound",
			"The Machine with label [{0}] not found at the repository." ),
	MODEL_ALREADY_EXISTS( HttpStatus.CONFLICT,
			APPLICATION_ERROR_CODE_PREFIX + ".already.exists",
			"The Model [{0}] already exists. Use the Update endpoint." );

	public final HttpStatus status;
	public final String errorCode;
	public final String errorMessage;

	// - C O N S T R U C T O R S
	ErrorInfo( final HttpStatus status, final String errorCode, final String errorMessage ) {
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage( final Object... arguments ) {
		return MessageFormat.format( this.errorMessage, arguments );
	}
}
