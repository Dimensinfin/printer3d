package org.dimensinfin.printer3d.backend.core.exception;

import java.sql.SQLException;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;

/**
 * Special exception to be used to document SQL exceptions that sometimes have the information on the cause and not on the message. With this
 * exception we extend the application core exception with the SQL message.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.12.0
 */
public class RepositoryException extends DimensinfinRuntimeException {
	private final SQLException sqle;

	// - C O N S T R U C T O R S
	public RepositoryException( final ErrorInfo invalidRequest, final SQLException sqle ) {
		super( invalidRequest );
		this.sqle = sqle;
	}

	// - G E T T E R S   &   S E T T E R S
	@Override
	public String getMessage() {
		return this.errorInfo.getErrorMessage( this.sqle.getMessage(), this.sqle.getCause() );
	}
}
