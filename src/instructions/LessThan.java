package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class LessThan extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		return 2;
	}

	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model) {
		if (canEvaluate())
			return ( parameters.get(0).evaluate(view, model) < 
					parameters.get(1).evaluate(view, model) ) ? 1 : 0;
		else
			return -1;
	}
}
