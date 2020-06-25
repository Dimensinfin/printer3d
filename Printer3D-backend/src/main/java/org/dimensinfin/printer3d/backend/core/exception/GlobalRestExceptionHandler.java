package org.dimensinfin.printer3d.backend.core.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.dimensinfin.common.exception.ApiError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(RepositoryConflictException.class)
	protected ResponseEntity<ApiError> handleRepositoryException( final RepositoryConflictException repositoryConflictException ) {
		return new ResponseEntity<>( new ApiError(repositoryConflictException), repositoryConflictException.getHttpStatus() );
	}
}
