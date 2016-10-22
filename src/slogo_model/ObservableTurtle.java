package slogo_model;

import java.util.ArrayList;
import java.util.List;

import slogo_view.SLOGOViewExternal;

/**
 * The class Turtle is a model that is unaware of any existing
 * view or controller objects. However, the ObservableTurtle extends Turtle
 * such that when a Turtle method is called that would require a view to
 * display a change visually, the ObservableTurtle will alert each of the
 * Observers in its list of SLOGOView listeners.
 * 
 * Many views may listen to one ObserverTurtle.
 * 
 * @author Addison
 * 
 */

public class ObservableTurtle extends Turtle {

	List<SLOGOViewExternal> listeners;

	public ObservableTurtle(double maxX, double maxY){
		super(maxX, maxY);
		listeners = new ArrayList<SLOGOViewExternal>();
	}

	public ObservableTurtle(SLOGOViewExternal listener){
		this(listener.getMaxX(), listener.getMaxY());
		listeners.add(listener);
	}

	//TODO: Talk this over
	//Not so sure about this code...why does a model have multiple views??
	public ObservableTurtle(List<SLOGOViewExternal> listeners){
		this(listeners.get(0).getMaxX(), listeners.get(0).getMaxY());
		this.listeners.addAll(listeners);
	}

	public void addListener(SLOGOViewExternal listener){
		listeners.add(listener);
	}

	/**
	 * Notifies all listeners that an important change
	 * has been made and they should check for it.
	 */
	private void alertListeners(){
		for (SLOGOViewExternal listener : listeners)
			listener.updateScreen();
	}

	@Override
	public double setXY(double x, double y) {
		double toReturn = super.setXY(x,y);
		alertListeners();
		return toReturn;
	}

	@Override
	public double setHeading(double heading) {
		double toReturn = super.setHeading(heading);
		alertListeners();		
		return toReturn;
	}
	
	@Override
	public double penDown() {
		double toReturn = super.penDown();
		alertListeners();
		return toReturn;
	}

	@Override
	public double penUp() {
		double toReturn = super.penUp();
		alertListeners();
		return toReturn;
	}

	@Override
	public double showTurtle() {
		double toReturn = super.showTurtle();
		alertListeners();
		return toReturn;
	}

	@Override
	public double hideTurtle() {
		double toReturn = super.hideTurtle();
		alertListeners();
		return toReturn;
	}
}