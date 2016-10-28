package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class XCoordinate extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		return 0;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		if (canEvaluate())
			return model.xCor();
		else
			return -1;
	}
}
