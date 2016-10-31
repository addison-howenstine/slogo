package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class Difference extends MathOperation implements UnlimitedParametersInstruction {

	@Override
	public int getNumRequiredParameters() {
		return 2;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		if (canEvaluate())
			return parameters.get(0).evaluate(view, model) - 
					parameters.get(1).evaluate(view, model);
		else
			return -1;
	}

	@Override
	public double evaluateUnlimitedParameters(SLOGOView view, SLOGOModel model) {
		double toReturn = parameters.get(0).evaluate(view, model);
		for (int i = 1; i < parameters.size(); i++){
			toReturn -= parameters.get(i).evaluate(view, model);
		}
		return toReturn;
	}
}
