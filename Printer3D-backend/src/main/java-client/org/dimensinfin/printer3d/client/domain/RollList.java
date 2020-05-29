package org.dimensinfin.printer3d.client.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.dimensinfin.printer3d.backend.inventory.roll.persistence.Roll;

public class RollList {
	private Integer count = 0;
	private List<Roll> rolls = new ArrayList<>();

	// - C O N S T R U C T O R S
	private RollList() {}

	public Integer getCount() {
		return this.count;
	}

	public List<Roll> getRolls() {
		return this.rolls;
	}

	// - B U I L D E R
	public static class Builder {
		private RollList onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new RollList();
		}
		public RollList.Builder withCount( final Integer count ) {
			if (null != count) this.onConstruction.count = count;
			return this;
		}

		public RollList.Builder withRollList( final List<Roll> rolls ) {
			if (null != rolls)	this.onConstruction.rolls = Objects.requireNonNull( rolls );
			return this;
		}

		public RollList build() {
			Objects.requireNonNull( this.onConstruction.count );
			Objects.requireNonNull( this.onConstruction.rolls );
			return this.onConstruction;
		}
	}
}
