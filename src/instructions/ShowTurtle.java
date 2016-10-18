package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class ShowTurtle extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		return 0;
	}

	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model) {
		if (canEvaluate())
			return model.showTurtle();
		else
			return -1;
	}

}
