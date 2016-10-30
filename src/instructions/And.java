package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class And extends BooleanOperation implements UnlimitedParametersInstruction {

	@Override
	public int getNumRequiredParameters() {
		return 2;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		if (canEvaluate())
			return (parameters.get(0).evaluate(view, model) + 
					parameters.get(1).evaluate(view, model) == getNumRequiredParameters()) ? 1 : 0;
		else
			return -1;
	}

	@Override
	public double evaluateUnlimitedParameters(SLOGOView view, SLOGOModel model) {
		double sumOfTrue = 0;
		for (int i = 0; i < parameters.size(); i++){
			sumOfTrue += parameters.get(i).evaluate(view, model);
		}
		return ( sumOfTrue == parameters.size() ) ? 1 : 0;
	}
}
