package org.dimensinfin.printer3d.backend.core.exception;

import java.text.MessageFormat;
import java.util.UUID;

public class Printer3DErrorInfo {
	public static final RequestCannotBeFulfilled REQUEST_CANNOT_BE_FULFILLED = new RequestCannotBeFulfilled();

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

		public ErrorInfoN parameters( final UUID identifier ) {
			return new ErrorInfoN.Builder()
					.withErrorName("REQUEST_CANNOT_BE_FULFILLED")
					.withMessage( MessageFormat.format( "The request [{0}] has not enough resources to be completed. Obsolete state.",
							identifier.toString()) )
					.build();
		}
	}
}
