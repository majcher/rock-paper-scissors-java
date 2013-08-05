package pl.mmajcherski.rps.ui.common;

import java.awt.Color;

public enum Colors {

	PLAYER_BACKGROUND(new Color(232, 238, 254, 255)),
	HEADER_BACKGROUND(new Color(221, 221, 221, 255)),
	BUTTON_BLUE(new Color(0, 67, 119, 255)), 
	BUTTON_GREEN(new Color(6, 148, 2, 255)); 
	
	private Color color;
	private Colors(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
}
