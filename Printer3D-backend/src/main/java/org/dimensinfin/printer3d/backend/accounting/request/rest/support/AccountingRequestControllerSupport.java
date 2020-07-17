package org.dimensinfin.printer3d.backend.accounting.request.rest.support;

public class AccountingRequestControllerSupport {
	private AccountingRequestControllerSupport() {}

	// - B U I L D E R
	public static class Builder {
		private final AccountingRequestControllerSupport onConstruction;

		public Builder() {
			this.onConstruction = new AccountingRequestControllerSupport();
		}

		public AccountingRequestControllerSupport build() {
			return this.onConstruction;
		}
	}
}
