package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class SetTowards extends TurtleCommand {

	@Override
	public int getNumRequiredParameters() {
		return 2;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		if (canEvaluate())
			return model.towards(parameters.get(0).evaluate(view, model), parameters.get(1).evaluate(view, model));
		else
			return -1;
	}

}
