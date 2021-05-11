package org.dimensinfin.printer3d.backend.core.exception;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

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
import org.dimensinfin.logging.LogWrapper;

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
		LogWrapper.info( ex.getMessage() );
		final String message = ex.getMessage();
		final String cause = message.substring( message.indexOf( "[Field" ), message.indexOf( ";", message.indexOf( "[Field" ) ) );
		final String subcause = message.substring( message.lastIndexOf( "default message [" ), message.lastIndexOf( "]" ) );

		final List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map( x -> x.getDefaultMessage() )
				.collect( Collectors.toList() );

		return new ResponseEntity<>(
				new ApiError( new DimensinfinRuntimeException( DimensinfinRuntimeException.errorFIELDNOTVALIDREQUEST( ex, cause, subcause ) ) ),
				HttpStatus.BAD_REQUEST );
	}
}
