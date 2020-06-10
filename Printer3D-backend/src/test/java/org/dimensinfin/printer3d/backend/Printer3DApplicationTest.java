package org.dimensinfin.printer3d.backend;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import org.dimensinfin.printer3d.backend.core.security.SecurityPermitAllConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Printer3DApplication.class)
@ContextConfiguration(classes = { Printer3DApplication.class, SecurityPermitAllConfig.class })
@ActiveProfiles("test")
public class Printer3DApplicationTest {
	@Test
	public void mainContract() {
		Assert.assertNotNull( new Printer3DApplication() );
	}

//	@Test
	public void securityConfiguration() throws Exception {
		final SecurityPermitAllConfig securityPermitAllConfig = new SecurityPermitAllConfig();
		final WebSecurity webSecurity = new WebSecurity( null );
		Assert.assertNotNull( securityPermitAllConfig );
		Mockito.doNothing().when( securityPermitAllConfig ).configure( webSecurity );
	}

	@Test
	public void springBootAplication() {
		Printer3DApplication.main( new String[0] );
	}
}
