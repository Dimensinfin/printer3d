package org.dimensinfin.core.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DimObjectsTest {

	@Test
	public void requireNonNull_nullMessage() {
		final String nullString = null;
		// Insert test code here.
		final NullPointerException exception = Assertions.assertThrows( NullPointerException.class, () -> {
			DimObjects.requireNonNull( nullString, "This is the message if null pointer detected." );
		} );
		final String expected = "[AssertThrows.assertThrows]>This is the message if null pointer detected.";
		Assertions.assertEquals( expected, exception.getMessage() );
	}

	@Test
	public void requireNonNull_nullNoMessage() {
		final String nullString = null;
		// Insert test code here.
		final NullPointerException exception = Assertions.assertThrows( NullPointerException.class, () -> {
			DimObjects.requireNonNull( nullString );
		} );
		final String expected = "[AssertThrows.assertThrows]>Null Pointer validation detected unexpected null value.";
		Assertions.assertEquals( expected, exception.getMessage() );
	}

	@Test
	public void requireNonNull_valid() {
		Assertions.assertNotNull( DimObjects.requireNonNull( "string" ) );
		Assertions.assertNotNull( DimObjects.requireNonNull( "string", "The string is not null." ) );
	}
}