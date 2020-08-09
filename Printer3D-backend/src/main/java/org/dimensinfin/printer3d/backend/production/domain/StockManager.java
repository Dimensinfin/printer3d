package org.dimensinfin.printer3d.backend.production.domain;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;

/**
 * Will manage the stock accounting used when Job or Request processing.
 * It will read the Parts repository and create a memory structure to maintain the stock numbers.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.6.0
 */
public class StockManager {
	private final PartRepository partRepository;
	private final Map<UUID, StockLevel> stocks = new HashMap<>();
	private final Map<UUID, Float> prices = new HashMap<>();

	// - C O N S T R U C T O R S
	public StockManager( final @NotNull PartRepository partRepository ) {
		this.partRepository = Objects.requireNonNull( partRepository );
	}

	// - G E T T E R S   &   S E T T E R S
	public Set<UUID> getStockIterator() {
		return this.stocks.keySet();
	}

	public StockManager clean() {
		this.stocks.clear();
		return this;
	}

	public float getPrice( final UUID partId ) {
		return this.prices.getOrDefault( partId, 0.0F );
	}

	public int getStock( final UUID partId ) {
		if (this.stocks.containsKey( partId ))
			return this.stocks.get( partId ).getStock();
		else throw new DimensinfinRuntimeException( Printer3DErrorInfo.errorSTOCKPROCESSINGFAILURE( partId ) );
	}

	public int minus( final UUID partId, final int quantity ) {
		LogWrapper.info( "Subtracting id: " + partId.toString() );
		LogWrapper.info( "Quantity: " + quantity );
		if (this.stocks.containsKey( partId )) {
			this.stocks.computeIfPresent( partId, ( UUID key, StockLevel stockLevel ) -> stockLevel.reduceStock( quantity ) );
			return this.stocks.get( partId ).getStock();
		} else throw new DimensinfinRuntimeException( Printer3DErrorInfo.errorSTOCKPROCESSINGFAILURE( partId ) );
	}

	public StockManager startStock() {
		LogWrapper.enter();
		try {
			this.partRepository.findAll().forEach( part -> {
				LogWrapper.info(
						MessageFormat.format( "id: {0} stock: {1}/{2}",
								part.getId(),
								part.getStockLevel(),
								part.getStockAvailable()
						)
				);
				this.stocks.put( part.getId(), new StockLevel.Builder()
						.withStock( part.getStockAvailable() )
						.build() );
				this.prices.put( part.getId(), part.getPrice() );
			} );
			return this;
		} finally {
			LogWrapper.exit();
		}
	}

	private static class StockLevel {
		private int stock = 0;

		// - C O N S T R U C T O R S
		private StockLevel() { }

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
			private final StockLevel onConstruction;

			// - C O N S T R U C T O R S
			public Builder() {
				this.onConstruction = new StockLevel();
			}

			public StockLevel build() {
				return this.onConstruction;
			}

			public StockLevel.Builder withStock( final Integer stock ) {
				this.onConstruction.stock = Objects.requireNonNull( stock );
				return this;
			}
		}
	}
}
