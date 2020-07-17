package org.dimensinfin.printer3d.client.production.rest;

import java.util.List;

import org.dimensinfin.printer3d.client.production.rest.dto.Job;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ProductionApiV1 {
	// - G E T T E R S   &   S E T T E R S
	/**
	 * Create the list of jobs required to reach the **Inventory** target.
	 * Search the **Inventory** needs and stocks mismatch to create a list of the Parts missing to reach the correct stocks levels for each part and
	 * each color. This version also has the rule 1 to show contiguous all parts with the same material type and color and same priority. Also applies
	 * the rule 2 to show contiguous jobs that use the same model if there rule 1 does not applies.
	 *
	 * @return Call&lt;JobsList&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@GET("api/v1/production/jobs/pending")
	Call<List<Job>> getPendingJobs();
}
