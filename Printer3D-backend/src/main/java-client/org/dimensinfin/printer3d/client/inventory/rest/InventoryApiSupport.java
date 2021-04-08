package org.dimensinfin.printer3d.client.inventory.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.inventory.rest.dto.SetupRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

public interface InventoryApiSupport {
	@Headers({ "Content-Type:application/json" })
	@GET("/api/v1/inventory/models/delete/all")
	Call<CounterResponse> deleteAllModels();

	@Headers({ "Content-Type:application/json" })
	@GET("/api/v1/inventory/parts/delete/all")
	Call<CounterResponse> deleteAllParts();

	@Headers({ "Content-Type:application/json" })
	@GET("/api/v1/inventory/coils/delete/all")
	Call<CounterResponse> deleteAllRolls();

	@Headers({ "Content-Type:application/json" })
	@GET("/api/v1/inventory/coils/reset/destruction")
	Call<CounterResponse> resetCoilScheduler();

	@Headers({ "Content-Type:application/json" })
	@PUT("/api/v1/inventory/machines/setup")
	Call<Boolean> setupMachine( @Body final @NotNull @Valid SetupRequest setupRequest );
}
