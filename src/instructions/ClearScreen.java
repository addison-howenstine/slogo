package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class ClearScreen extends TurtleCommand {

	@Override
	public int getNumRequiredParameters() {
		return 0;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		if (canEvaluate()){
			view.clearTrails();
			double isPenDown = model.isPenDown();
			model.penUp();
			model.home();
			if (isPenDown == 1)
				model.penDown();
			return model.clearScreen();
		}
		else
			return -1;
	}
}
