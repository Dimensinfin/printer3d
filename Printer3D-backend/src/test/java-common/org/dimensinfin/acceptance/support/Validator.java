package org.dimensinfin.acceptance.support;

import java.util.Map;

public interface Validator<T> {
	boolean validate( final Map<String, String> rowData, final T record );
}
