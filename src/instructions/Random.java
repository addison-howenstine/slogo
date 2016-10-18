package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class Random extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		return 1;
	}

	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model) {
		if (canEvaluate())
			return Math.random() * parameters.get(0).evaluate(view, model);
		else
			return -1;
	}
}
