package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class Pi extends MathOperation {

	@Override
	public int getNumRequiredParameters() {
		return 0;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		if (canEvaluate())
			return Math.PI;
		else
			return -1;
	}
}
