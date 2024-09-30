package com.rosenzest.electric.storage.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergyStorageEchartsData {

	private double value;

	private ItemStyle itemStyle;

	public EnergyStorageEchartsData(double value, String color) {
		this.value = value;
		this.itemStyle = new ItemStyle(color);
	}

	@Data
	@AllArgsConstructor
	public static class ItemStyle {

		/**
		 * color
		 */
		private String color;
	}
}
