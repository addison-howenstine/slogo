package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class PenUp extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		return 1;
	}

	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model) {
		if (canEvaluate())
			return model.penUp();
		else
			return -1;
	}

}
