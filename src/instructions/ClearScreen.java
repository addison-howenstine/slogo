package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class ClearScreen extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		return 0;
	}

	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model) {
		if (canEvaluate()){
			model.home();
			view.clearTrails();
			return model.clearScreen();
		}
		else
			return -1;
	}
}
