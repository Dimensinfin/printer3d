package org.dimensinfin.printer3d.backend.datamanagement.support.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.backend.datamanagement.support.domain.PartData;
import org.dimensinfin.printer3d.backend.datamanagement.support.domain.PartListData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.Query;

public interface InventoryApiData {
	@Headers({ "Content-Type:application/json" })
	@GET("api/v1/inventory/parts")
	Call<PartListData> getParts( @Header("Authorization") final @NotNull String authorizationToken,
	                             @Query("activesOnly") final Boolean activesOnly );

	@Headers({ "Content-Type:application/json" })
	@PATCH("api/v1/inventory/parts")
	Call<PartData> updatePart( @Header("Authorization") final @NotNull String authorizationToken,
	                           @Body final @NotNull @Valid PartData part );
}
