package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class SetPosition extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		return 2;
	}

	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model) {
		if (canEvaluate())
			return model.setXY(parameters.get(0).evaluate(view, model), parameters.get(1).evaluate(view, model));
		else
			return -1;
	}

}
