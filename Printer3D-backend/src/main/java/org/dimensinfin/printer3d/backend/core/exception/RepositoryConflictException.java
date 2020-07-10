package org.dimensinfin.printer3d.backend.core.exception;

import org.springframework.dao.DataIntegrityViolationException;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.common.exception.ErrorInfoN;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;

/**
 * Used to reports exceptions related to interactions with the repository that do not result on the expected result.
 * Examples of this are duplicated records, records not found or any other application constraint logic.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.8.0
 */
public class RepositoryConflictException extends DimensinfinRuntimeException {
//	private ErrorInfoN error;

	// - C O N S T R U C T O R S
	public RepositoryConflictException( final ErrorInfo conflict, final DataIntegrityViolationException die ) {
		super( conflict, die.getMessage() );
	}

	public RepositoryConflictException( final ErrorInfo repositoryConflictError, final String identifier ) {
		super( repositoryConflictError, identifier );
	}

	public RepositoryConflictException( final ErrorInfoN error ) {
		super( error );
//		this.error = error;
	}


	//	public String getErrorCode() {
	//		return this.dimensinfinRuntimeException.getErrorInfo().errorCode;
	//	}
	//
	//	public String getErrorInfo() {
	//		return this.dimensinfinRuntimeException.getErrorInfo().name();
	//	}
	//
	//	public String getHttpStatus() {
	//		return this.dimensinfinRuntimeException.getHttpStatus().toString();
	//	}
	//
	//	public Integer getHttpStatusCode() {
	//		return this.dimensinfinRuntimeException.getHttpStatus().value();
	//	}
	//
	//	public String getHttpStatusName() {
	//		return this.dimensinfinRuntimeException.getHttpStatus().name();
	//	}
	//
	//	public String getMessage() {
	//		return this.dimensinfinRuntimeException.getMessage();
	//	}


}
