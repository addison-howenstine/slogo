package slogo_controller;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class TurtleInstruction {

	protected double forward(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return model.forward((double) right);
	}

	protected double backward(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return model.back((double) right);
	}

	protected double left(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return model.left((double) right);
	}

	protected double right(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return model.right((double) right);
	}

	protected double setHeading(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return model.setHeading((double) right);
	}

	protected double setTowards(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return model.towards((double) left, (double) right);
	}

	protected double setPosition(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return model.setXY((double) left, (double) right);
	}
	
	protected double penDown(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return model.penDown();
	}
	
	protected double penUp(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return model.penUp();
	}
	
	protected double showTurtle(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return model.showTurtle();
	}
	
	protected double hideTurtle(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return model.hideTurtle();
	}
	
	protected double home(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return model.home();
	}
	
	protected double clearScreen(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		view.clearTrails();
		return model.clearScreen();
	}	
}