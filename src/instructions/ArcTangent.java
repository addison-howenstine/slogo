package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class ArcTangent extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		return 1;
	}

	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model) {
		if (canEvaluate())
			return Math.atan(Math.toRadians( parameters.get(0).evaluate(view, model) ));
		else
			return -1;
	}
}