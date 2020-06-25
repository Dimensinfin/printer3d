package org.dimensinfin.printer3d.backend.core.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;

//@Order(Ordered.HIGHEST_PRECEDENCE)
//@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
//	@ExceptionHandler(RepositoryConflictException.class)
	protected ResponseEntity<RepositoryConflictException> handleRepositoryException( final RepositoryConflictException repositoryConflictException ) {
		return new ResponseEntity<>( repositoryConflictException, repositoryConflictException.getHttpStatus() );
	}

//	@ExceptionHandler(DimensinfinRuntimeException.class)
	protected ResponseEntity<DimensinfinRuntimeException> handleRepositoryException( final DimensinfinRuntimeException dimensinfinRuntimeException ) {
		return new ResponseEntity<>( dimensinfinRuntimeException, dimensinfinRuntimeException.getHttpStatus() );
	}
}
