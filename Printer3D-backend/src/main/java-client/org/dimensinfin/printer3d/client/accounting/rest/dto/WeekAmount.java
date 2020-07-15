package org.dimensinfin.printer3d.client.accounting.rest.dto;

import java.util.Objects;

public class WeekAmount {
	private Integer year = 2020;
	private Integer week;
	private Double amount;

	// - C O N S T R U C T O R S
	private WeekAmount() {}

	public Integer getYear() {
		return this.year;
	}

	public Integer getWeek() {
		return this.week;
	}

	public Double getAmount() {
		return this.amount;
	}

	// - B U I L D E R
	public static class Builder {
		private final WeekAmount onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new WeekAmount();
		}

		public WeekAmount build() {
			Objects.requireNonNull( this.onConstruction.week );
			Objects.requireNonNull( this.onConstruction.amount );
			if (this.onConstruction.year < 2020) this.onConstruction.year = 2020;
			return this.onConstruction;
		}

		public WeekAmount.Builder withAmount( final Double amount ) {
			this.onConstruction.amount = Objects.requireNonNull( amount );
			return this;
		}

		public WeekAmount.Builder withWeek( final Integer week ) {
			this.onConstruction.week = Objects.requireNonNull( week );
			return this;
		}

		public WeekAmount.Builder withYear( final Integer year ) {
			this.onConstruction.year = Objects.requireNonNull( year );
			return this;
		}
	}
}
