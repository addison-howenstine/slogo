package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class ListStart extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		// List can have an unlimited number of parameters but shouldn't look for them in parser
		return 0;
	}

	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model) {
		int numParams = parameters.size();
		// evaluate each parameter
		for (int i = 0; i < numParams - 1; i++)
			parameters.get(i).evaluate(view, model);
		// return the return value of the final parameter
		return parameters.get(numParams - 1).evaluate(view, model);
	}

}
