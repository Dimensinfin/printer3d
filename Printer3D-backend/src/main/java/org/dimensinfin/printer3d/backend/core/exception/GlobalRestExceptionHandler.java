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

import org.dimensinfin.common.exception.ApiError;
import org.dimensinfin.common.exception.DimensinfinErrorInfo;
import org.dimensinfin.common.exception.DimensinfinRuntimeException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(DimensinfinRuntimeException.class)
	protected ResponseEntity<ApiError> handleDimensinfinRuntimeException( final DimensinfinRuntimeException dimensinfinRuntimeException ) {
		return new ResponseEntity<>( new ApiError( dimensinfinRuntimeException ), dimensinfinRuntimeException.getStatus() );
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid( final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request ) {
		return new ResponseEntity<>(
				new ApiError( new DimensinfinRuntimeException( DimensinfinErrorInfo.RUNTIME_INTERNAL_ERROR( ex.getMessage() ) ) ),
				HttpStatus.BAD_REQUEST );
	}
}
