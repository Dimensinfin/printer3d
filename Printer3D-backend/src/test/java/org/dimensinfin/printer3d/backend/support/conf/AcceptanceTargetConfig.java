package org.dimensinfin.printer3d.backend.support.conf;

import org.springframework.stereotype.Component;

@Component
public class AcceptanceTargetConfig implements ITargetConfiguration {
	private static final String DEFAULT_BACKEND_SERVER = "http://localhost:";
	private static final Integer DEFAULT_BACKEND_PORT = 5140;
	private static final String DEFAULT_INNODENTAL_BACKEND_ACCEPTED_CONTENT_TYPE = "application/json";

	// - G E T T E R S   &   S E T T E R S
	public String getBackendServer() {
		return DEFAULT_BACKEND_SERVER + DEFAULT_BACKEND_PORT;
	}

	public String getContentType() {
		return DEFAULT_INNODENTAL_BACKEND_ACCEPTED_CONTENT_TYPE;
	}
}
