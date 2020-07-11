package org.dimensinfin.printer3d.backend.core.exception;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.UUID;

import org.dimensinfin.common.exception.DimensinfinError;

import static org.dimensinfin.printer3d.backend.Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX;

public class Printer3DErrorInfo {
	public static final RequestCannotBeFulfilled REQUEST_CANNOT_BE_FULFILLED = new RequestCannotBeFulfilled();

	public static DimensinfinError REQUEST_NOT_FOUND( final UUID requestId ) {
		return new DimensinfinError.Builder()
				.withErrorName( "REQUEST_NOT_FOUND" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX+".defined.repository.logic" )
//				.withHttpStatus( HttpStatus.CONFLICT )
				.withMessage( MessageFormat.format( "Request record with id [{0}] not found at the repository." , requestId.toString()) )
//				.withCause ( cause)
				.build();
	}

	//	private Printer3DErrorInfo() {}
	//
	//	// - B U I L D E R
	//	public static class Builder {
	//		private final Printer3DErrorInfo onConstruction;
	//
	//		public Builder() {
	//			this.onConstruction = new Printer3DErrorInfo();
	//		}
	//
	//		public Printer3DErrorInfo build() {
	//			return this.onConstruction;
	//		}
	//	}
	public static class RequestCannotBeFulfilled  {
//		private UUID identifier;

//		public UUID getIdentifier() {
//			return this.identifier;
//		}

		public DimensinfinError parameters( final UUID identifier ) {
			return new DimensinfinError.Builder()
					.withErrorName("REQUEST_CANNOT_BE_FULFILLED")
					.withMessage( MessageFormat.format( "The request [{0}] has not enough resources to be completed. Obsolete state.",
							identifier.toString()) )
					.build();
		}
	}
}
