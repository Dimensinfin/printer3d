package org.dimensinfin.printer3d.backend.production.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.dimensinfin.printer3d.backend.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;

/**
 * Will manage the stock accounting used when Job or Request processing.
 * It will read the Parts repository and create a memory structure to maintain the stock numbers.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.6.0
 */
@Component
public class StockManager {
	private final PartRepository partRepository;
	private final Map<UUID, StockLevel> stocks = new HashMap<>();

	// - C O N S T R U C T O R S
	@Autowired
	public StockManager( final @NotNull PartRepository partRepository ) {
		this.partRepository = Objects.requireNonNull( partRepository );
	}

	public int getStock( final UUID partId ) {
		if (stocks.containsKey( partId ))
			return this.stocks.get( partId ).getStock();
		else throw new DimensinfinRuntimeException( ErrorInfo.STOCK_PROCESSING_FAILURE.getErrorMessage( partId ) );
	}

	public int minus( final UUID partId, final int quantity ) {
		if (this.stocks.containsKey( partId )) {
			this.stocks.computeIfPresent( partId, ( UUID key, StockLevel stockLevel ) -> stockLevel.reduceStock( quantity ) );
			return this.stocks.get( partId ).getStock();
		} else throw new DimensinfinRuntimeException( ErrorInfo.STOCK_PROCESSING_FAILURE.getErrorMessage( partId ) );
	}

	public void startStock() {
		this.partRepository.findAll().forEach( ( part ) ->
				stocks.put( part.getId(), new StockLevel.Builder()
						.withLevel( part.getStockLevel() )
						.withStock( part.getStockAvailable() )
						.build() )
		);
	}

	private static class StockLevel {
		private int level = 0;
		private int stock = 0;

		// - C O N S T R U C T O R S
		private StockLevel() {
		}

		// - G E T T E R S   &   S E T T E R S
		public int getStock() {
			return stock;
		}

		public StockLevel reduceStock( final int minus ) {
			this.stock -= minus;
			return this;
		}

		// - B U I L D E R
		public static class Builder {
			private StockLevel onConstruction;

			// - C O N S T R U C T O R S
			public Builder() {
				this.onConstruction = new StockLevel();
			}

			public StockLevel build() {
				Objects.requireNonNull( this.onConstruction.level );
				Objects.requireNonNull( this.onConstruction.stock );
				return this.onConstruction;
			}

			public StockLevel.Builder withLevel( final Integer level ) {
				this.onConstruction.level = Objects.requireNonNull( level );
				return this;
			}

			public StockLevel.Builder withStock( final Integer stock ) {
				this.onConstruction.stock = Objects.requireNonNull( stock );
				return this;
			}
		}
	}
}
