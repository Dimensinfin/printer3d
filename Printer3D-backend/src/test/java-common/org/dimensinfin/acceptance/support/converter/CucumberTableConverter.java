package org.dimensinfin.acceptance.support.converter;

import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import org.dimensinfin.acceptance.support.CommonWorld;

public abstract class CucumberTableConverter<T> implements Converter<List<Map<String, String>>, List<T>> {

	private static final String LIST_SEPARATOR = ",";

	@Override
	public List<T> convert( List<Map<String, String>> dataTable ) {
		return dataTable.stream()
				.map( row -> convert( row ) )
				.collect( Collectors.toList() );
	}

	public abstract T convert( Map<String, String> cucumberRow );

	protected String cleanKey( final String key ) {
		return key.replace( '>', ' ' ).trim();
	}

	protected boolean containsAnyField( Set<String> keys, Map<String, String> cucumberRow ) {
		for (String key : keys) {
			if (StringUtils.isNotEmpty( cucumberRow.get( key ) )) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	protected String cucumberDataMap( final CommonWorld world, final String cucumberData ) {
		if (cucumberData.contains( "world" )) {
			final String key = this.cleanKey( cucumberData.split( ":" )[1] );
			switch (key) {
				case "id":
					return world.getId().toString();
			}
		}
		return cucumberData;
	}

	protected String dynamicDateConversion( final String reference ) {
		if (reference.equalsIgnoreCase( "<today>" ))
			return Instant.now().toString();
		if (reference.equalsIgnoreCase( "<yesterday>" ))
			return Instant.now().minus( Period.ofDays( 1 ) ).toString();
		return reference;
	}

	protected List<String> getListFromCucumberValue( String value ) {
		return StringUtils.isNotEmpty( value ) ?
				Arrays.stream( value.split( LIST_SEPARATOR ) ).map( String::trim ).collect( Collectors.toList() ) : null;
	}
}
