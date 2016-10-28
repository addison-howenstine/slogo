package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class Not extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		return 1;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		if (canEvaluate())
			return (parameters.get(0).evaluate(view, model) == 0) ? 1 : 0;
		else
			return -1;
	}

}
