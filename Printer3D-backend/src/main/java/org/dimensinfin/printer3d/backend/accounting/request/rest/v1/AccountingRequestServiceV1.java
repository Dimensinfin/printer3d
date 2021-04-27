package org.dimensinfin.printer3d.backend.accounting.request.rest.v1;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.threeten.extra.YearWeek;

import org.dimensinfin.printer3d.backend.accounting.request.WeekComparator;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;

@Service
public class AccountingRequestServiceV1 {
	private final RequestsRepositoryV2 requestsRepositoryV2;

	// - C O N S T R U C T O R S
	@Autowired
	public AccountingRequestServiceV1( @NotNull final RequestsRepositoryV2 requestsRepositoryV2 ) {this.requestsRepositoryV2 = requestsRepositoryV2;}

	// - G E T T E R S   &   S E T T E R S

	/**
	 * Get the aggregated request amounts for the requests closed during a week. Get the <weekCount> most recent records.
	 *
	 * @param weekCount the desired maximum number of weeks starting from the most recent and backwards.
	 * @return a list of <code>WeekAmounts</code> with the aggregated Request amounts that were closed during that week.
	 */
	public List<WeekAmount> getRequestsAmountPerWeek( @NotNull final Integer weekCount ) {
		final Map<YearWeek, Float> weekAggregation = new HashMap<>();
		this.requestsRepositoryV2.findAll()
				.stream()
				.filter( RequestEntityV2::isClosed )
				.forEach( request ->
						weekAggregation.merge( YearWeek.from( request.getClosedDate().atZone( ZoneId.systemDefault() ) ),
								request.getAmount(),
								Float::sum ) );
		return this.transformAmountsMap( weekAggregation )
				.stream()
				.sorted( new WeekComparator().reversed() )
				.limit( weekCount )
				.sorted( new WeekComparator() )
				.collect( Collectors.toList() );
	}

	/**
	 * Return the ordered list of year/week aggregated amounts from the collected data on the Map.
	 *
	 * @param weekData the input map data.
	 * @return an ordered list of <code>WeekAmount</code> in ascending calendar order.
	 */
	private List<WeekAmount> transformAmountsMap( final Map<YearWeek, Float> weekData ) {
		final List<WeekAmount> weekAmountList = new ArrayList<>();
		weekData.forEach( ( key, val ) -> weekAmountList.add( new WeekAmount.Builder()
				.withYear( key.getYear() )
				.withWeek( key.getWeek() )
				.withAmount( val )
				.build() ) );
		weekAmountList.sort( new WeekComparator() );
		return weekAmountList;
	}
}
