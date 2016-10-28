package slogo_view;

import javafx.scene.paint.Paint;

public class PenOptions {
	
	private Paint color;
	private int width;
	private double dashLength;
	private double dashSpace;
	
	public PenOptions(Paint color, int width, double dashLength, double dashSpace) {
		this.color = color;
		this.width = width;
		this.dashLength = dashLength;
		this.dashSpace = dashSpace;
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

	public double getDashLength() {
		return dashLength;
	}

	public void setDashLength(double dashLength) {
		this.dashLength = dashLength;
	}

	public double getDashSpace() {
		return dashSpace;
	}

	public void setDashSpace(double dashSpace) {
		this.dashSpace = dashSpace;
	}
}
