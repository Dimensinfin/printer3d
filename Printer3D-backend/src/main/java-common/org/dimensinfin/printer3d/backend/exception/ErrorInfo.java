package org.dimensinfin.printer3d.backend.exception;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.12.0
 */
public enum ErrorInfo {
	RUNTIME_INTERNAL_ERROR( HttpStatus.INTERNAL_SERVER_ERROR,
			"dimensinfin.printer3d.exception.runtime",
			"{0}" ),
	INVALID_REQUEST_STRUCTURE( HttpStatus.BAD_REQUEST,
			"dimensinfin.printer3d.exception.request.validation",
			"The request is not valid. {0}" ),
	INVENTORY_STORE_REPOSITORY_FAILURE( HttpStatus.INTERNAL_SERVER_ERROR,
			"dimensinfin.printer3d.persistence.sql.error",
					"There is an SQL error on the Inventory repository. {0}. Cause: {1}" );

	public final HttpStatus status;
	public final String errorCode;
	public final String errorMessage;

	ErrorInfo( final HttpStatus status, final String errorCode, final String errorMessage ) {
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage( final Object... arguments ) {
		return MessageFormat.format( this.errorMessage, arguments );
	}
}
