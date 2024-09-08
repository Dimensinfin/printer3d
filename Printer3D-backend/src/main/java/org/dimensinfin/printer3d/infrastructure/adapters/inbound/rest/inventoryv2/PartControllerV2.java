package org.dimensinfin.printer3d.infrastructure.adapters.inbound.rest.inventoryv2;

import java.util.List;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.application.ports.inbound.InventoryV2UseCases;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.6.0
 */
@RestController
@Validated
@RequestMapping("/api/v2")
public class PartControllerV2 {
	private final InventoryV2UseCases inventoryV2UseCases;

	// - C O N S T R U C T O R S
	@Autowired
	public PartControllerV2( @NotNull final InventoryV2UseCases inventoryV2UseCases ) {
		this.inventoryV2UseCases = inventoryV2UseCases;
	}

	// - G E T T E R S   &   S E T T E R S

	/**
	 * Get the valid list of <b>Parts</b> persisted at the Inventory repository. Only the Parts that can be built because those that have no Coil
	 * will be removed from the listing.
	 */
	@GetMapping(path = "/inventory/parts",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<List<Part>> getPartsV2() {
		return new ResponseEntity<>( this.inventoryV2UseCases.getPartsUseCaseV2().execute(), HttpStatus.OK );
	}
}
