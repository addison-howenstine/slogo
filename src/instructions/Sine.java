package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class Sine extends MathOperation {

	@Override
	public int getNumRequiredParameters() {
		return 1;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		if (canEvaluate())
			return Math.sin(Math.toRadians( parameters.get(0).evaluate(view, model) ));
		else
			return -1;
	}
}
