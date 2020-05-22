package org.dimensinfin.printer3d.backend.part.persistence;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class PartTest {
	@Test
	public void equalsContract() {
		EqualsVerifier.forClass( Part.class )
//				.suppress(Warning.NONFINAL_FIELDS)
//				.suppress( Warning.SURROGATE_KEY)
				.verify();
	}
}
