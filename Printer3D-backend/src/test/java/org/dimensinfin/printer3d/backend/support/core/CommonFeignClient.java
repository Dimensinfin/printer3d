package org.dimensinfin.printer3d.backend.support.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.validation.constraints.NotNull;

import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import org.dimensinfin.printer3d.backend.support.conf.ITargetConfiguration;

import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class CommonFeignClient {
	public static final Converter.Factory GSON_CONVERTER_FACTORY =
			GsonConverterFactory.create(
					new GsonBuilder()
							.registerTypeAdapter( LocalDate.class, new GSONLocalDateDeserializer() )
							.registerTypeAdapter( LocalDateTime.class, new GSONLocalDateTimeDeserializer() )
						//	.registerTypeAdapter( OffsetDateTime.class, new GSONOffsetDateTimeDeserializer() )
							.create() );
	protected final ITargetConfiguration acceptanceTargetConfig;

	// - C O N S T R U C T O R S
	public CommonFeignClient( @NotNull final ITargetConfiguration acceptanceTargetConfig ) {
		this.acceptanceTargetConfig = Objects.requireNonNull( acceptanceTargetConfig );
	}
}
