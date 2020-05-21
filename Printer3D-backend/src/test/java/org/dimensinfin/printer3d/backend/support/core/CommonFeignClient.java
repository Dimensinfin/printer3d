package org.dimensinfin.printer3d.backend.support.core;

import java.util.Objects;
import javax.validation.constraints.NotNull;

import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class CommonFeignClient {
	public static final Converter.Factory GSON_CONVERTER_FACTORY =
			GsonConverterFactory.create(
					new GsonBuilder()
							.create() );
	protected final AcceptanceTargetConfig acceptanceTargetConfig;

	// - C O N S T R U C T O R S
	public CommonFeignClient( @NotNull final AcceptanceTargetConfig acceptanceTargetConfig ) {
		this.acceptanceTargetConfig = Objects.requireNonNull( acceptanceTargetConfig );
	}
}
