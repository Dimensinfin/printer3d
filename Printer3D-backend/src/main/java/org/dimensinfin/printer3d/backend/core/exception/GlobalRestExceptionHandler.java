package org.dimensinfin.printer3d.backend.core.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(DimensinfinRuntimeException.class)
	protected ResponseEntity<ApiError> handleDimensinfinRuntimeException( final DimensinfinRuntimeException dimensinfinRuntimeException ) {
		return new ResponseEntity<>( new ApiError( dimensinfinRuntimeException ), dimensinfinRuntimeException.getHttpStatus() );
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid( final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request ) {
		LogWrapper.info( "Exception: " + ex.toString() );
		LogWrapper.info( "message: " + ex.getMessage() );
		return new ResponseEntity<>(
				new ApiError( new DimensinfinRuntimeException( DimensinfinRuntimeException.INVALID_REQUEST_STRUCTURE( ex ) ) ),
				HttpStatus.BAD_REQUEST );
	}

	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<ApiError> handleRuntimeException( final RuntimeException runtimeException ) {
		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException(
				DimensinfinRuntimeException.RUNTIME_INTERNAL_ERROR( runtimeException.getMessage() ),
				"Intercepted RuntimeException at the ErrorHandler level because this was not expected."
		);
		return new ResponseEntity<>( new ApiError( exception ), exception.getHttpStatus() );
	}
}
