package org.dimensinfin.printer3d.backend;

import org.junit.jupiter.api.Assertions;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Printer3DApplication.class)
//@ActiveProfiles("test")
public class Printer3DApplicationTest {
//	@Test
	public void mainContract() {
		Printer3DApplication.main( new String[0] );
		Assertions.assertTrue( Printer3DApplication.hasPrinter() );
	}
}
