package slogo_view;

import javafx.scene.paint.Paint;

public class PenOptions {
	
	private Paint color;
	private int width;
	
	public PenOptions(Paint color, int width) {
		this.color = color;
		this.width = width;
	}
	
	public Paint getColor() {
		return color;
	}

	public void setColor(Paint color) {
		this.color = color;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
