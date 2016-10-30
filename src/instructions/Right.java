package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class Right extends TurtleCommand  implements UnlimitedParametersInstruction{

	@Override
	public int getNumRequiredParameters() {
		return 1;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		if (canEvaluate())
			return model.right(parameters.get(0).evaluate(view, model));
		else
			return -1;	}

	@Override
	public double evaluateUnlimitedParameters(SLOGOView view, SLOGOModel model) {
		double toReturn = 0;
		for(Instruction i : parameters ){
			toReturn = model.right(i.evaluate(view, model));
		}
		return toReturn;	
	}

}
