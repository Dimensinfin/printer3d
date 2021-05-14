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
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityV2ToCustomerRequestResponseConverter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestContentType;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;

public class CommonRequestService {
	protected static final RequestEntityV2ToCustomerRequestResponseConverter requestEntityV2ToCustomerRequestResponseConverter = new RequestEntityV2ToCustomerRequestResponseConverter();
	protected final PartRepository partRepository;
	protected final ModelRepository modelRepository;

	protected final StockManager stockManager;

	// - C O N S T R U C T O R S
	public CommonRequestService( @NotNull final PartRepository partRepository,
	                             @NotNull final ModelRepository modelRepository ) {
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

	@Deprecated
	protected float calculateRequestAmount( final RequestEntityV2 requestEntityV2 ) {
		float amount = 0.0F;
		for (final RequestItem item : requestEntityV2.getContents()) {
			if (item.getType() == RequestContentType.PART)
				amount = amount + item.getQuantity() * this.stockManager.getPrice( item.getItemId() );
			if (item.getType() == RequestContentType.MODEL) {
				final Optional<ModelEntity> model = this.modelRepository.findById( item.getItemId() );
				if (model.isPresent())
					for (final UUID modelPartId : model.get().getPartIdList())
						amount = amount + this.stockManager.getPrice( modelPartId ) * item.getQuantity();
			}
		}
		return amount;
	}

	/**
	 * For each of the requests found get the contents and subtract the required number of items from the stock values. If any of the subtractions
	 * results in a negative value then the Request should be kept open and this is signaled by the returned boolean.
	 * Extract the Ids from the stock. Check if stock goes negative. For Models this expands to the BOM.
	 *
	 * @param requestEntityV2 the request entity that should be processed
	 * @return true if there are missing items. This means the Request should be kept open. If false the Request can be COMPLETED.
	 */
	protected boolean collectItemsFromStock( final RequestEntityV2 requestEntityV2 ) {
		boolean underStocked = false;
		for (final RequestItem content : requestEntityV2.getContents()) {
			if (content.getType() == RequestContentType.PART) {
				final int missing = this.stockManager.minus( content.getItemId(), content.getQuantity() );
				if (missing < 0) { // Subtract the request quantity from the stock.
					underStocked = true;
					content.setMissing( Math.abs( missing ) );
				}
			}
			if (content.getType() == RequestContentType.MODEL)
				for (final RequestItem modelContent : this.modelBOM( content.getItemId(), content.getQuantity() )) {
					final int missing = this.stockManager.minus( modelContent.getItemId(), modelContent.getQuantity() );
					if (missing < 0) {// Subtract the request quantity from the stock.
						underStocked = true;
						content.setMissing(
								Math.min(
										content.getQuantity(),
										Math.max(
												modelContent.getMissing(),
												(int) Math.ceil( (float) Math.abs( missing ) / (float) modelContent.getQuantity() )
										)
								)
						);
					}
				}
		}
		return !underStocked;
	}

	protected List<RequestItem> modelBOM( final UUID modelId, final int modelQuantity ) {
		final Map<UUID, Integer> contents = new HashMap<>();
		final ModelEntity model = this.modelRepository.findById( modelId ).orElseThrow();
		Integer hit;
		for (final UUID contentId : model.getPartIdList()) {
			contents.putIfAbsent( contentId, 0 );
			hit = contents.get( contentId );
			contents.put( contentId, ++hit );
		}
		final List<RequestItem> resultContents = new ArrayList<>();
		for (final Map.Entry<UUID, Integer> targetContent : contents.entrySet())
			resultContents.add( new RequestItem.Builder()
					.withItemId( targetContent.getKey() )
					.withQuantity( targetContent.getValue() * modelQuantity )
					.withType( RequestContentType.PART ).build() );
		return resultContents;
	}
}
