package org.dimensinfin.printer3d.backend.part.rest.v1;

import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.backend.part.persistence.Part;
import org.dimensinfin.printer3d.client.part.domain.PartList;

@Profile({ "local", "acceptance" })
@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v1")
public class PartControllerV1 {
	private PartServiceV1 partServiceV1;

	// - C O N S T R U C T O R S
	@Autowired
	public PartControllerV1( @NotNull final PartServiceV1 partServiceV1 ) {
		this.partServiceV1 = partServiceV1;
	}

	@PostMapping(path = "/parts",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Part> newPart( @RequestBody @Valid @NotNull final Part part ) {
		return new ResponseEntity<>( this.partServiceV1.newPart( part ), HttpStatus.CREATED );
	}

	@GetMapping("/parts")
	public ResponseEntity<PartList> partsList( @NotNull final HttpServletResponse response,
	                                           @RequestParam(name = "active") final Optional<Boolean> active ) {
		final boolean activeState = active.isPresent() && active.get();
		//		CircuitBreaker circuitBreaker = this.resilience4JConfig.getCircuitBreakerRegistry().circuitBreaker( "medicoList4Centro" );
		//		Supplier<CentroMedicoList> decoratedSupplier = CircuitBreaker.decorateSupplier(
		//				circuitBreaker,
		//				() ->

		//		);
		return new ResponseEntity<>( this.partServiceV1.partsList( activeState ), HttpStatus.OK );
	}
}
