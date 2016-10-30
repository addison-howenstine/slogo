package instructions;

import java.util.List;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class Forward extends TurtleCommand implements UnlimitedParametersInstruction{

	public Forward() {
	}

	@Override
	public int getNumRequiredParameters() {
		return 1;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		if (canEvaluate())
			return model.forward(parameters.get(0).evaluate(view, model), false);
		else
			return -1;
	}

	@Override
	public double evaluateUnlimitedParameters(SLOGOView view, SLOGOModel model) {
		double toReturn = 0;
		for(Instruction i : parameters ){
			toReturn = model.forward(i.evaluate(view, model), false);
		}
		return toReturn;
	}	

}
