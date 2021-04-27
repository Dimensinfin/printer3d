package org.dimensinfin.printer3d.backend.accounting.request.rest.v1;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.accounting.request.rest.AccountingRestErrors;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;

@RestController
@Validated
@RequestMapping("/api/v1")
public class AccountingRequestControllerV1 {
	private static final Integer DEFAULT_WEEK_COUNT = 6;
	private final AccountingRequestServiceV1 accountingRequestServiceV1;
	private final RequestsRepositoryV2 requestsRepositoryV2;

	// - C O N S T R U C T O R S
	public AccountingRequestControllerV1( @NotNull final AccountingRequestServiceV1 accountingRequestServiceV1,
	                                      @NotNull final RequestsRepositoryV2 requestsRepositoryV2 ) {
		this.accountingRequestServiceV1 = accountingRequestServiceV1;
		this.requestsRepositoryV2 = requestsRepositoryV2;
	}

	@GetMapping(path = "/accounting/requests/data",
			consumes = "application/json",
			produces = "text/csv")
	public void exportToCSV( final HttpServletResponse response ) {
		response.setContentType( "text/csv" );
		final DateFormat dateFormatter = new SimpleDateFormat( "yyyy-MM-dd_HH-mm" );
		final String currentDateTime = dateFormatter.format( new Date() );

		final String headerKey = "Content-Disposition";
		final String headerValue = "attachment; filename=pedidos-usuario-completados_" + currentDateTime + ".csv";
		response.setHeader( headerKey, headerValue );

		try (final ICsvBeanWriter csvWriter = new CsvBeanWriter( response.getWriter(), CsvPreference.STANDARD_PREFERENCE )) {
			final String[] csvHeader = { "id", "etiqueta", "fecha apertura", "fecha cierre", "importe" };
			final String[] nameMapping = { "id", "label", "requestDate", "closedDate", "amount" };
			csvWriter.writeHeader( csvHeader );
			for (final RequestEntityV2 request : this.requestsRepositoryV2.findAll().stream()
					.filter( RequestEntityV2::isClosed )
					.collect( Collectors.toList() ))
				csvWriter.write( request, nameMapping );
		} catch (final IOException ioe) {
			throw new DimensinfinRuntimeException( AccountingRestErrors.errorEXPORTGENERATIONEXCEPTION( ioe.getMessage() ) );
		}
	}

	@GetMapping(path = "/accounting/requests/amount/week",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<List<WeekAmount>> getRequestsAmountPerWeek( @RequestParam(name = "weekCount", required = false) final Integer weekCount ) {
		return new ResponseEntity<>( this.accountingRequestServiceV1.getRequestsAmountPerWeek(
				(null != weekCount) ? weekCount : DEFAULT_WEEK_COUNT
		), HttpStatus.OK );
	}
}
