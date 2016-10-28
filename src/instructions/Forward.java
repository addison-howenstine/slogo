package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class Forward extends Instruction {

	public Forward() {
	}

	@Override
	public int getNumRequiredParameters() {
		return 1;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		if (canEvaluate())
			return model.forward(parameters.get(0).evaluate(view, model), false);
		else
			return -1;
	}	

}
