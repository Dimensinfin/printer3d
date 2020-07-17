package org.dimensinfin.printer3d.client.accounting.rest;

import java.time.Instant;
import javax.validation.constraints.NotNull;

import org.dimensinfin.common.client.rest.CounterResponse;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AccountingApiSupport {
	/**
	 * Get the aggregated request amounts for the requests closed during a week. Get the &lt;weekCount&gt; most recent records.
	 *
	 * @param closeDate the new close date for all closed Requests
	 * @return Call&lt;WeekAmountList&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@PUT("api/v1/accounting/requests/closedate/{closeDate}")
    Call<CounterResponse> setCloseDate( @Path("closeDate") final @NotNull Instant closeDate );

}
