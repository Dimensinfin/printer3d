package org.dimensinfin.printer3d.client.inventory.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.dimensinfin.common.client.rest.CountResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

public interface InventoryApiSupport {
	@Headers({ "Content-Type:application/json" })
	@GET("/api/v1/inventory/parts/delete/all")
	Call<CountResponse> deleteAllParts();

	@Headers({ "Content-Type:application/json" })
	@GET("/api/v1/inventory/coils/delete/all")
	Call<CountResponse> deleteAllRolls();

	@Headers({ "Content-Type:application/json" })
	@PUT("/api/v1/inventory/machines/setup")
	Call<Boolean> setupMachine( @Body final @NotNull @Valid SetupRequest setupRequest );
}
