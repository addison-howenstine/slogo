package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class ListEnd extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		// List can have an unlimited number of parameters but shouldn't look for them in parser
		return 0;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		// do nothing, this object should never be evaluated
		return 0;
	}

}
