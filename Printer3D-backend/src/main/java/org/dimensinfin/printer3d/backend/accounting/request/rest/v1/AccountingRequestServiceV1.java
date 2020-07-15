package org.dimensinfin.printer3d.backend.accounting.request.rest.v1;

import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.backend.accounting.request.WeekComparator;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;

public class AccountingRequestServiceV1 {
	private final RequestsRepositoryV2 requestsRepositoryV2;

	// - C O N S T R U C T O R S
	private AccountingRequestServiceV1( final @NotNull RequestsRepositoryV2 requestsRepositoryV2 ) {this.requestsRepositoryV2 = requestsRepositoryV2;}

	// - G E T T E R S   &   S E T T E R S
	public List<WeekAmount> getRequestsAmountPerWeek( final @NotNull Integer weekCount ) {
		final Map<Integer, Double> weekAggregation = new HashMap<>();
		this.requestsRepositoryV2.findAll()
				.stream()
				.filter( request -> !request.isOpen() )
				.forEach( request -> {
					final int week = request.getRequestDate().get( ChronoField.ALIGNED_WEEK_OF_YEAR );
					weekAggregation.compute( week, ( key, val ) -> val = val + request.getAmount() );
				} );
		return this.transformAmountsMap( weekAggregation ).stream().limit( weekCount ).collect( Collectors.toList());
	}

	private List<WeekAmount> transformAmountsMap( final Map<Integer, Double> weekData ) {
		final List<WeekAmount> weekAmountList = new ArrayList<>();
		weekData.forEach( ( key, val ) -> weekAmountList.add( new WeekAmount.Builder()
				.withWeek( key )
				.withAmount( val )
				.build() ) );
		Collections.sort( weekAmountList, new WeekComparator() );
		return weekAmountList;
	}
}
