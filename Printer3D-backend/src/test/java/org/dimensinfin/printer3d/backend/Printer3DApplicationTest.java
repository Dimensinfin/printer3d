package org.dimensinfin.printer3d.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Printer3DApplication.class)
//@ActiveProfiles("test")
public class Printer3DApplicationTest {
	@Test
	public void mainContract() {
		Assertions.assertNotNull( new Printer3DApplication() );
	}
}
