package org.dimensinfin.printer3d.client.production.rest.dto;

import java.util.UUID;

/**
 * Now a Request item can be of different types. So there is a need to use an isolation class that will contain the neutral data and be ale to
 * locate the detail record form the right repository. So the list of Parts now is transformed to a list of items.
 * A request item then has the type of item and the quantity of the same items.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.8.0
 */
public class RequestItem {
	private UUID itemId;
	private  RequestContentType type=RequestContentType.UNIDENTIFIED;
	private int quantity;

	// - C O N S T R U C T O R S
	private RequestItem() {}

	// - B U I L D E R
	public static class Builder {
		private final RequestItem onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new RequestItem();
		}

		public RequestItem build() {
			return this.onConstruction;
		}
	}
}
