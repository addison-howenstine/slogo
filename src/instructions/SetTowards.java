package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class SetTowards extends TurtleCommand  implements UnlimitedParametersInstruction{

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

	@Override
	public double evaluateUnlimitedParameters(SLOGOView view, SLOGOModel model) {
		double toReturn = 0;
		for ( int i = 0; i < parameters.size(); i += getNumRequiredParameters()){
			toReturn = model.towards(parameters.get(i).evaluate(view, model), parameters.get(i + 1).evaluate(view, model));
		}
		return toReturn;
	}

}
