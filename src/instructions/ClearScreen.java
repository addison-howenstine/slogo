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
			model.home();
			view.clearTrails();
			return model.clearScreen();
		}
		else
			return -1;
	}
}
