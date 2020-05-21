package org.dimensinfin.printer3d.backend.support.core;

import org.springframework.stereotype.Component;

@Component
public class AcceptanceTargetConfig {
	private static final String DEFAULT_BACKEND_SERVER = "http://0.0.0.0:";
	private static final Integer DEFAULT_BACKEND_PORT = 5110;
	private static final String DEFAULT_INNODENTAL_BACKEND_ACCEPTED_CONTENT_TYPE = "application/json";

	public String getBackendServer() {
		return DEFAULT_BACKEND_SERVER + DEFAULT_BACKEND_PORT;
	}

	public String getContentType() {
		return DEFAULT_INNODENTAL_BACKEND_ACCEPTED_CONTENT_TYPE;
	}
}
