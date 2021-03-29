package org.dimensinfin.printer3d.backend.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
	/**
	 * This configuration disables the security completely but leaving active the CORS detection.
	 *
	 * @param http the security service
	 */
	@Override
	protected void configure( final HttpSecurity http ) throws Exception {
		http.authorizeRequests()
				.anyRequest().permitAll()
				.and()
				.csrf().disable();
	}
}
