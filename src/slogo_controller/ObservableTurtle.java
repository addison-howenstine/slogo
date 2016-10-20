package slogo_controller;

import slogo_model.Turtle;

public class ObservableTurtle extends Turtle {
	
	TurtleController controller;
	
	public ObservableTurtle(TurtleController controller){
		super();
		this.controller = controller;
	}
	
	public ObservableTurtle(){
		super();
	}
	
	public void setController(TurtleController controller){
		this.controller = controller;
	}
	
	@Override
	public double setXY(double x, double y) {
		double toReturn = super.setXY(x,y);
		controller.alertController();
		return toReturn;
	}
	
	@Override
	public double setHeading(double heading) {
		double toReturn = super.setHeading(heading);
		controller.alertController();
		return toReturn;
	}


}
