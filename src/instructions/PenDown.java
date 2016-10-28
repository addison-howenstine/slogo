package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class PenDown extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		return 0;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		if (canEvaluate())
			return model.penDown();
		else
			return -1;
	}

}
