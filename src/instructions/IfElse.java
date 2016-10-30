package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class IfElse extends ControlStructureCommand {

	@Override
	public int getNumRequiredParameters() {
		return 3;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
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
