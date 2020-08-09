package org.dimensinfin.printer3d.backend.production.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestContentType;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;

@Component
public class RequestPartCollector {
	private final ModelRepository modelRepository;
	private final RequestsRepositoryV2 requestsRepositoryV2;

	// - C O N S T R U C T O R S
	@Autowired
	public RequestPartCollector( final @NotNull ModelRepository modelRepository,
	                             final @NotNull RequestsRepositoryV2 requestsRepositoryV2 ) {
		this.modelRepository = modelRepository;
		this.requestsRepositoryV2 = requestsRepositoryV2;
	}

	/**
	 * Read all requests open and then subtract the request associated parts from the current stock counts.
	 * The stocks are updated and the results are returned for missing parts evaluation and then pending job generation.
	 *
	 * @return the final state for the stocks.
	 */
	public StockManager collectRequestPartsFromRepository( final StockManager stockManager ) {
		LogWrapper.enter();
		try {
			this.requestsRepositoryV2.findAll()
					.stream()
					.filter( RequestEntityV2::isOpen )
					.forEach( requestEntityV2 -> {
						// Subtract the Parts from the inventory
						LogWrapper.info( "Processing Request: " + requestEntityV2.getId().toString() );
						for (RequestItem content : requestEntityV2.getContents()) {
							LogWrapper.info( "Processing Content: " + content.getItemId().toString() );
							if (content.getType() == RequestContentType.PART) {
								stockManager.minus( content.getItemId(), content.getQuantity() ); // Subtract the request
							}
							if (content.getType() == RequestContentType.MODEL) {
								for (RequestItem modelContent : this.modelBOM( content.getItemId(), content.getQuantity() )) {
									stockManager.minus( modelContent.getItemId(), modelContent.getQuantity() ); // Subtract the request
								}
							}
						}
					} );
			return stockManager;
		} finally {
			LogWrapper.exit();
		}
	}

	/**
	 * Get the contained expanded list of parts from a model taking on account the times this models should be used.
	 *
	 * @param modelId       the unique identifier of the target model
	 * @param modelQuantity the number oc model copies
	 * @return the list of parts required to complete this number of model copies.
	 */
	private List<RequestItem> modelBOM( final UUID modelId, final int modelQuantity ) {
		final Map<UUID, Integer> contents = new HashMap<>();
		final ModelEntity model = this.modelRepository.findById( modelId )
				.orElseThrow( () -> new DimensinfinRuntimeException( Printer3DErrorInfo.errorMODELNOTFOUND( modelId ) ) );
		Integer hit;
		for (UUID contentId : model.getPartIdList()) {
			contents.putIfAbsent( contentId, 0 );
			contents.put( contentId, contents.get( contentId ) + 1 );
		}
		final List<RequestItem> resultContents = new ArrayList<>();
		for (Map.Entry<UUID, Integer> targetContent : contents.entrySet())
			resultContents.add( new RequestItem.Builder()
					.withItemId( targetContent.getKey() )
					.withQuantity( targetContent.getValue() * modelQuantity )
					.withType( RequestContentType.PART ).build() );
		LogWrapper.info( "Model BOM: " + resultContents.toString() );
		return resultContents;
	}
}
