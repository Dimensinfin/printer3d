package org.dimensinfin.printer3d.client.part.domain;

/**
 * It is a short color code to be appended to the label for part identification. For the machine build job and the stock the color is a most important
 * element since differentiates to identical parts at the model level.
 */
public enum ColorCodeType {
	WHITE("WHITE"),
	GREEN("GREEN"),
	GREEN_TRANSP("GREEN-TRANSP"),
	RED("RED"),
	LIGHT_BLUE("LIGHT-BLUE"),
	PINK_TRANSP("PINK-TRANSP"),
	ORANGE_TRANSP("ORANGE-TRANSP");

	private String colorName;
	ColorCodeType(final String name) {
		this.colorName = name;
	}

	public String getColorName() {
		return this.colorName;
	}
}

