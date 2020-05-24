package org.dimensinfin.printer3d.client.part.rest;

import org.dimensinfin.common.client.rest.CountResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface PartsApiSupport {
	@Headers({ "Content-Type:application/json" })
	@GET("/api/v1/inventory/parts/delete/all")
	Call<CountResponse> deleteAllParts();
}
