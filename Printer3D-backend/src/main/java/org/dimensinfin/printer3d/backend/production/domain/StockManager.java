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

	/**
	 * Activate inactive Parts on the catalog but that are being used on Models. To completely deactivate parts Models have to be reviewed.
	 */
	public void activate( final UUID partId ) {
		this.stocks.computeIfPresent( partId, ( UUID key, StockLevel stockLevel ) -> stockLevel.setActive( true ) );
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

	public int getStockLevel( final UUID partId ) {
		if (this.stocks.containsKey( partId ))
			return this.stocks.get( partId ).getStockLevel();
		else throw new DimensinfinRuntimeException( Printer3DErrorInfo.errorSTOCKPROCESSINGFAILURE( partId ) );
	}

	public boolean isActive( final UUID partId ) {
		if (this.stocks.containsKey( partId ))
			return this.stocks.get( partId ).isActive();
		else return false;
	}

	public int minus( final UUID partId, final int quantity ) {
		LogWrapper.info( MessageFormat.format( "Subtracting {0} from id [{1}]", quantity, partId ) );
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
						.withStockLevel( part.getStockLevel() )
						.withStock( part.getStockAvailable() )
						.withActive( part.isActive() )
						.build() );
				this.prices.put( part.getId(), part.getPrice() );
			} );
			return this;
		} finally {
			LogWrapper.exit();
		}
	}

	private static class StockLevel {
		private int level = 1;
		private int stock = 0;
		private boolean active = true;

		// - C O N S T R U C T O R S
		private StockLevel() { }

		// - G E T T E R S   &   S E T T E R S
		public int getStock() {
			return stock;
		}

		public int getStockLevel() {
			return this.level;
		}

		public boolean isActive() {
			return this.active;
		}

		public StockLevel setActive( final boolean active ) {
			this.active = active;
			return this;
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

			public StockLevel.Builder withActive( final Boolean active ) {
				this.onConstruction.active = true;
				if (null != active) this.onConstruction.active = active;
				return this;
			}

			public StockLevel.Builder withStock( final Integer stock ) {
				this.onConstruction.stock = Objects.requireNonNull( stock );
				return this;
			}

			public StockLevel.Builder withStockLevel( final Integer stockLevel ) {
				this.onConstruction.level = Objects.requireNonNull( stockLevel );
				return this;
			}
		}
	}
}
