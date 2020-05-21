package org.dimensinfin.printer3d.client.part.support;

import org.dimensinfin.common.client.rest.CountResponse;

import retrofit2.Call;
import retrofit2.http.Headers;

public interface PartsApiSupport {
	@Headers({ "Content-Type:application/json" })
	Call<CountResponse> deleteAllCentros();
}
