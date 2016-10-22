package instructions;

import slogo_controller.SLOGOController;
import slogo_controller.TurtleController;
import slogo_model.SLOGOModel;
import slogo_model.Turtle;
import slogo_view.Playground;
import slogo_view.SLOGOViewExternal;

public class BackEndTesterMain {

	public BackEndTesterMain() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		SLOGOViewExternal view = null;
		SLOGOModel turtle = new Turtle(view.getMaxX(), view.getMaxY());
		SLOGOController control = new TurtleController(view, turtle);
		control.run("fd 10 back left 20");
	}

}
