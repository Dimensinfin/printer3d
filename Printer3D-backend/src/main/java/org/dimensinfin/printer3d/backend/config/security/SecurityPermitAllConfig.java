package org.dimensinfin.printer3d.backend.config.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins( Arrays.asList( "http://localhost:5102" ) );
		configuration.setAllowedMethods( Arrays.asList( "OPTIONS", "GET", "POST", "PATCH" ) );
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration( "/**", configuration );
		return source;
	}

	/**
	 * This configuration disables the security completely but leaving active the CORS detection.
	 *
	 * @param http the security service
	 */
	@Override
	protected void configure( final HttpSecurity http ) throws Exception {
		http.authorizeRequests()
				.anyRequest().permitAll()
				//				.and().cors()
				.and().csrf().disable();
	}
}
