package org.dimensinfin.printer3d.backend.core.exception;

import java.text.MessageFormat;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.dimensinfin.core.exception.ApiError;
import org.dimensinfin.core.exception.DimensinfinError;
import org.dimensinfin.core.exception.DimensinfinRuntimeException;

import static org.dimensinfin.printer3d.backend.Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX;
import static org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo.PERSISTENCE_ERROR;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
	private static DimensinfinError errorINVENTORYSTOREREPOSITORYUNEXPECTEDERROR( final RuntimeException rte, final String cause ) {
		return new DimensinfinError.Builder()
				.withErrorName( "INVENTORY_STORE_REPOSITORY_FAILURE" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + PERSISTENCE_ERROR )
				.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
				.withMessage( MessageFormat.format( "There is an SQL error on the Inventory repository. {0}. SQL cause: {1}",
						rte.getMessage(),
						cause ) )
				.withCause( cause )
				.build();
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<ApiError> handleDataIntegrityViolationException( final DataIntegrityViolationException dataIntegrityException ) {
		final String cause = dataIntegrityException.getCause().getCause().getMessage();
		final DimensinfinRuntimeException dimensinfinRuntimeException =
				new DimensinfinRuntimeException( errorINVENTORYSTOREREPOSITORYUNEXPECTEDERROR( dataIntegrityException, cause ) );
		return new ResponseEntity<>( new ApiError( dimensinfinRuntimeException ), dimensinfinRuntimeException.getHttpStatus() );
	}

	@ExceptionHandler(DimensinfinRuntimeException.class)
	protected ResponseEntity<ApiError> handleDimensinfinRuntimeException( final DimensinfinRuntimeException dimensinfinRuntimeException ) {
		return new ResponseEntity<>( new ApiError( dimensinfinRuntimeException ), dimensinfinRuntimeException.getHttpStatus() );
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid( final MethodArgumentNotValidException ex, final HttpHeaders headers,
	                                                               final HttpStatus status, final WebRequest request ) {
		return new ResponseEntity<>(
				new ApiError( new DimensinfinRuntimeException( DimensinfinRuntimeException.errorINVALIDREQUESTSTRUCTURE( ex ) ) ),
				HttpStatus.BAD_REQUEST );
	}
}
