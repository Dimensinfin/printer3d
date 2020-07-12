package org.dimensinfin.common.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DimensinfinRuntimeExceptionTest {
	@Test
	public void constructorContract() {
		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException( DimensinfinErrorInfo.RUNTIME_INTERNAL_ERROR( "-MESSAGE-" ) );
		Assertions.assertNotNull( exception );
	}
}
