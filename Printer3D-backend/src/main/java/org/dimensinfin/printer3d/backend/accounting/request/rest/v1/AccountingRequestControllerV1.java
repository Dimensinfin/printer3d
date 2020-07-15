package org.dimensinfin.printer3d.backend.accounting.request.rest.v1;

import java.util.List;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;

@RestController
@Validated
@RequestMapping("/api/v1")
public class AccountingRequestControllerV1 {
	private final AccountingRequestServiceV1 accountingRequestServiceV1;

	public AccountingRequestControllerV1( final @NotNull AccountingRequestServiceV1 accountingRequestServiceV1 ) { this.accountingRequestServiceV1 =
			accountingRequestServiceV1;}

	@GetMapping(path = "/accounting/requests/week/{weekCount}",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<List<WeekAmount>> getRequestsAmountPerWeek( final @PathVariable @NotNull Integer weekCount ) {
		return new ResponseEntity<>( this.accountingRequestServiceV1.getRequestsAmountPerWeek(weekCount), HttpStatus.OK );
	}
}
