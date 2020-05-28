package org.dimensinfin.printer3d.client.part.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.backend.inventory.roll.persistence.Roll;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RollsApiV1 {
	/**
	 * Creates a new **Roll** instance on the persistence repository.
	 * The 3D printers need and use plastic filament to build the models. The rolls have as main characteristics the type of plastic, the color and
	 * the quantity of the material.
	 *
	 * @param roll Contains the *Roll** fields requested to the user on the frontend UI.
	 * @return the persisted Roll record.
	 */
	@Headers({ "Content-Type:application/json" })
	@POST("api/v1/inventory/rolls")
	Call<Roll> newRoll( @Header("Authorization") final @NotNull String authorizationToken,
	                    @Body @NotNull @Valid Roll roll
	);
}
