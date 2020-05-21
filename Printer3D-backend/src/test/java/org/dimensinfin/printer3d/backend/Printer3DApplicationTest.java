package org.dimensinfin.printer3d.backend;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Printer3DApplication.class)
@ActiveProfiles("test")
public class Printer3DApplicationTest {
	@Test
	public void mainContract() {
		Printer3DApplication.main( new String[0] );
		Assertions.assertTrue( Printer3DApplication.hasPrinter() );
	}
}