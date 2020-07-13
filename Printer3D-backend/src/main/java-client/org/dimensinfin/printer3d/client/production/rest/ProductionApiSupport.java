package org.dimensinfin.printer3d.client.production.rest;

import java.util.List;

import org.dimensinfin.common.client.rest.CounterResponse;
import org.dimensinfin.printer3d.client.production.rest.dto.JobHistoric;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ProductionApiSupport {
	// - G E T T E R S   &   S E T T E R S
	@Headers({ "Content-Type:application/json" })
	@GET("/api/v1/production/jobs/completed")
	Call<List<JobHistoric>> getCompletedJobs();

	@Headers({ "Content-Type:application/json" })
	@GET("/api/v1/production/requests/repository")
	Call<RequestList> getRepositoryRequests();

	@Headers({ "Content-Type:application/json" })
	@GET("/api/v1/production/jobs/delete/all")
	Call<CounterResponse> deleteAllJobs();

	@Headers({ "Content-Type:application/json" })
	@GET("/api/v1/production/requests/delete/all")
	Call<CounterResponse> deleteAllRequests();

	@Headers({ "Content-Type:application/json" })
	@GET("/api/v1/production/requests/v2/delete/all")
	Call<CounterResponse> deleteAllRequestsV2();
}
