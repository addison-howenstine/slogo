package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class If extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		return 2;
	}

	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model) {
		if (canEvaluate()){
			if (parameters.get(0).evaluate(view, model) != 0)
				return parameters.get(1).evaluate(view, model);
			else return 0;
		}
		else
			return -1;
	}

}
