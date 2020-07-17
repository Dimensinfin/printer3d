package org.dimensinfin.printer3d.backend.accounting.request.rest.v1;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

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
	private AccountingRequestServiceV1( final @NotNull RequestsRepositoryV2 requestsRepositoryV2 ) {this.requestsRepositoryV2 = requestsRepositoryV2;}

	// - G E T T E R S   &   S E T T E R S
	public List<WeekAmount> getRequestsAmountPerWeek( final @NotNull Integer weekCount ) {
		final Map<YearWeek, Float> weekAggregation = new HashMap<>();
		this.requestsRepositoryV2.findAll()
				.stream()
				.filter( RequestEntityV2::isClosed )
				.forEach( request -> {
					final YearWeek week = YearWeek.from( request.getClosedDate().atZone( ZoneId.systemDefault() ) );
					if (weekAggregation.containsValue( week ))
						weekAggregation.put( week, weekAggregation.get( week ) + request.getAmount() );
					else
						weekAggregation.put( week, request.getAmount() );
				} );
		return this.transformAmountsMap( weekAggregation ).stream().limit( weekCount ).collect( Collectors.toList() );
	}

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
