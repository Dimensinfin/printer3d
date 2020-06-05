package org.dimensinfin.printer3d.client.inventory.rest;

import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListv2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface InventoryApiV2 {
// - G E T T E R S   &   S E T T E R S
	/**
	 * Gets the list of **Machines** defined on the system and persisted on the Inventory unit.
	 * This version changes the contents so it is not compatible with previous versions. The build job information is collected into a new data
	 * structure and there are more fields added to the build record.
	 *
	 * @return Call&lt;MachineListV2&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@GET("api/v2/inventory/machines")
	Call<MachineListv2> getMachines();
}
