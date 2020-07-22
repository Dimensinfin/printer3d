package org.dimensinfin.printer3d.backend.production.request.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.domain.StockManager;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestContentType;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;

public class RequestServiceCore {
	protected final PartRepository partRepository;
	protected final ModelRepository modelRepository;

	protected final StockManager stockManager;

	// - C O N S T R U C T O R S
	public RequestServiceCore( final @NotNull PartRepository partRepository,
	                           final @NotNull ModelRepository modelRepository ) {
		this.partRepository = partRepository;
		this.modelRepository = modelRepository;
		this.stockManager = new StockManager( partRepository );
	}

	public void decrementStock( final UUID recordId, final int quantity ) {
		final Optional<PartEntity> partOpt = this.partRepository.findById( recordId );
		partOpt.ifPresent( partEntity -> {
			partEntity.decrementStock( quantity );
			this.partRepository.save( partEntity );
		} );
	}

	protected float calculateRequestAmount( final RequestEntityV2 requestEntityV2 ) {
		float amount = 0.0F;
		for (RequestItem item : requestEntityV2.getContents()) {
			if (item.getType() == RequestContentType.PART)
				amount = amount + item.getQuantity() * this.stockManager.getPrice( item.getItemId() );
			if (item.getType() == RequestContentType.MODEL) {
				final Optional<ModelEntity> model = this.modelRepository.findById( item.getItemId() );
				if (model.isPresent())
					for (UUID modelPartId : model.get().getPartIdList())
						amount = amount + this.stockManager.getPrice( modelPartId ) * item.getQuantity();
			}
		}
		return amount;
	}

	protected List<RequestItem> modelBOM( final UUID modelId, final int modelQuantity ) {
		final Map<UUID, Integer> contents = new HashMap<>();
		final ModelEntity model = this.modelRepository.findById( modelId ).orElseThrow();
		Integer hit;
		for (UUID contentId : model.getPartIdList()) {
			contents.putIfAbsent( contentId, 0 );
			hit = contents.get( contentId );
			contents.put( contentId, ++hit );
		}
		final List<RequestItem> resultContents = new ArrayList<>();
		for (Map.Entry<UUID, Integer> targetContent : contents.entrySet())
			resultContents.add( new RequestItem.Builder()
					.withItemId( targetContent.getKey() )
					.withQuantity( targetContent.getValue() * modelQuantity )
					.withType( RequestContentType.PART ).build() );
		return resultContents;
	}
}
