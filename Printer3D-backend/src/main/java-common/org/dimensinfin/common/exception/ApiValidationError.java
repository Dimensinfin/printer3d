package org.dimensinfin.common.exception;

//@Data
//@EqualsAndHashCode(callSuper = false)
//@AllArgsConstructor
public class ApiValidationError extends ApiSubError {
	private String object;
	private String field;
	private Object rejectedValue;
	private String message;

	ApiValidationError(String object, String message) {
		this.object = object;
		this.message = message;
	}
	private ApiValidationError() {}

	// - B U I L D E R
	public static class Builder {
		private final ApiValidationError onConstruction;

		public Builder() {
			this.onConstruction = new ApiValidationError();
		}

		public ApiValidationError build() {
			return this.onConstruction;
		}
	}
}
