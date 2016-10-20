package slogo_controller;

import java.util.ArrayList;
import java.util.List;

import slogo_model.Turtle;
import slogo_view.SLOGOViewExternal;

public class ObservableTurtle extends Turtle {

	List<SLOGOViewExternal> listeners;

	public ObservableTurtle(){
		super();
		listeners = new ArrayList<SLOGOViewExternal>();
	}

	public ObservableTurtle(SLOGOViewExternal listener){
		this();
		listeners.add(listener);
	}

	public ObservableTurtle(List<SLOGOViewExternal> listeners){
		this();
		this.listeners.addAll(listeners);
	}

	public void addListener(SLOGOViewExternal listener){
		listeners.add(listener);
	}

	private void alertListeners(){
		for (SLOGOViewExternal listener : listeners){
			listener.updateScreen();
			System.out.println("alerting listeners");
		}
	}

	@Override
	public double setXY(double x, double y) {
		double toReturn = super.setXY(x,y);
		//alertListeners();
		return toReturn;
	}

	@Override
	public double setHeading(double heading) {
		double toReturn = super.setHeading(heading);
		alertListeners();		
		return toReturn;
	}


}
