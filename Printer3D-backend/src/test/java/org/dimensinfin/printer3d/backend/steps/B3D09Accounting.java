package org.dimensinfin.printer3d.backend.steps;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.common.client.rest.CounterResponse;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.accounting.rest.AccountFeignClientSupport;
import org.dimensinfin.printer3d.backend.support.accounting.rest.WeekAmountValidator;
import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class B3D09Accounting extends StepSupport {
	private final AccountFeignClientSupport accountFeignClientSupport;
	// - C O N S T R U C T O R S
	public B3D09Accounting( final @NotNull Printer3DWorld printer3DWorld,
	                        final AccountFeignClientSupport accountFeignClientSupport ) {
		super( printer3DWorld );
		this.accountFeignClientSupport = accountFeignClientSupport;
	}

	@When("moving back one week all closed dates")
	public void moving_back_one_week_all_closed_dates() throws IOException {
		final ResponseEntity<CounterResponse> counter = this.accountFeignClientSupport.moveBackCloseDates(
				Instant.now().minus( 1,
				ChronoUnit.WEEKS )
		);
		Assertions.assertNotNull( counter );
		Assertions.assertNotNull( counter.getBody() );
	}

	@Then("the week report has the next contents")
	public void the_week_report_has_the_next_contents( final List<Map<String, String>> dataTable ) {
		final ResponseEntity<List<WeekAmount>> weekData = this.printer3DWorld.getListWeekAmountResponseEntity();
		Assertions.assertNotNull( weekData );
		Assertions.assertNotNull( weekData.getBody() );
		for (int i = 0; i < dataTable.size(); i++) {
			final Map<String, String> row = dataTable.get( i );
			final WeekAmount record = weekData.getBody().get( i );
			Assertions.assertTrue( new WeekAmountValidator().validate( row, record ) );
		}
	}

}
