package org.dimensinfin.printer3d.backend.exception;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.12.0
 */
public enum ErrorInfo {
	RUNTIME_INTERNAL_ERROR( HttpStatus.INTERNAL_SERVER_ERROR,
			"innnodental.exception.runtime",
			"{0}" ),
	INVALID_REQUEST_STRUCTURE( HttpStatus.BAD_REQUEST,
			"innnodental.exception.request.validation",
			"The request is not valid. {0}" ),
	CITA_NOT_FOUND( HttpStatus.NOT_FOUND,
			"innnodental.persistence.notfound",
			"The requested cita with id [{0}] is not found at the repository." ),
	CENTRO_NOT_FOUND( HttpStatus.NOT_FOUND,
			"innnodental.persistence.notfound",
			"The requested Centro with id [{0}] is not found at the repository." ),
	CENTRO_STORE_REPOSITORY_FAILURE( HttpStatus.INTERNAL_SERVER_ERROR,
			"innnodental.persistence.sql.error",
			"There is an SQL error on the Centros repository. {0}. Cause: {1}" ),
	CENTRO_ALREADY_EXISTS(HttpStatus.CONFLICT,
			"innnodental.persistence.already.exists",
			"The Centro [{0}] already exists. Use the Update endpoint."),
	MEDICO_STORE_REPOSITORY_FAILURE( HttpStatus.INTERNAL_SERVER_ERROR,
			"innnodental.persistence.sql.error",
					"There is an SQL error on the Medicos repository. {0}. Cause: {1}" );

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
