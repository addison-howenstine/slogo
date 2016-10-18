package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class IfElse extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		return 3;
	}

	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model) {
		if (canEvaluate()){
			if (parameters.get(0).evaluate(view, model) != 0)
				return parameters.get(1).evaluate(view, model);
			else
				return parameters.get(2).evaluate(view, model);
		}
		else
			return -1;
	}

}
