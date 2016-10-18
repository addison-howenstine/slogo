package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class IsPenDown extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		return 0;
	}

	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model) {
		if (canEvaluate())
			return model.isPenDown();
		else
			return -1;
	}
}
