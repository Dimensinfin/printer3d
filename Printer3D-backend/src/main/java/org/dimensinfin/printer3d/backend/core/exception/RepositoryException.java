package org.dimensinfin.printer3d.backend.core.exception;

import org.dimensinfin.common.exception.DimensinfinError;
import org.dimensinfin.common.exception.DimensinfinRuntimeException;

/**
 * Special exception to be used to document SQL exceptions that sometimes have the information on the cause and not on the message. With this
 * exception we extend the application core exception with the SQL message.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.1.0
 */
public class RepositoryException extends DimensinfinRuntimeException {
	// - C O N S T R U C T O R S
	public RepositoryException( final DimensinfinError error ) {
		super( error );
	}
	//	public RepositoryException( final DimensinfinError error, final SQLException sqle ) {
	//		super( error );
	//		error.setStatus( HttpStatus.INTERNAL_SERVER_ERROR );
	//		if (null != sqle.getCause()) {
	//			error.setCause( sqle.getCause().toString() );
	//		}
	//		error.updateMessage( error.getMessage().concat( " " ).concat( sqle.getMessage() ) );
	//	}
}
