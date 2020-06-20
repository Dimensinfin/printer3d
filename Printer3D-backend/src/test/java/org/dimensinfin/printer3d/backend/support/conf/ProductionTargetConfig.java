package org.dimensinfin.printer3d.backend.support.conf;

import org.springframework.stereotype.Component;

@Component
public class ProductionTargetConfig implements ITargetConfiguration {
	private static final String DEFAULT_BACKEND_SERVER = "https://printer3d-frontend.herokuapp.com/";
	private static final Integer DEFAULT_BACKEND_PORT = 80;
	private static final String DEFAULT_INNODENTAL_BACKEND_ACCEPTED_CONTENT_TYPE = "application/json";

	// - G E T T E R S   &   S E T T E R S
	public String getBackendServer() {
		return DEFAULT_BACKEND_SERVER;
	}

	public String getContentType() {
		return DEFAULT_INNODENTAL_BACKEND_ACCEPTED_CONTENT_TYPE;
	}
}
