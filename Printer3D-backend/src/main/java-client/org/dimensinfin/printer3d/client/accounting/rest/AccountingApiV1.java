package org.dimensinfin.printer3d.client.accounting.rest;

import java.util.List;

import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface AccountingApiV1 {
	@Headers({ "Content-Type:application/json" })
	@GET("api/v1/accounting/requests/data")
	Call<String> downloadClosedRequestsData();

	/**
	 * Get the aggregated request amounts for the requests closed during a week. Get the &lt;weekCount&gt; most recent records.
	 *
	 * @param weekCount The number of weeks to send back on the result. The current week will be the first records and then the n-1 records for the
	 *                  previous weeks.  (optional, default to 6d)
	 * @return Call&lt;WeekAmountList&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@GET("api/v1/accounting/requests/amount/week")
	Call<List<WeekAmount>> getRequestsAmountPerWeek( final @Query("weekCount") Integer weekCount );
}
