package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class Repeat extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		return 2;
	}

	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model) {
		if (canEvaluate()){
			
			int timesToRun = (int) parameters.get(0).evaluate(view, model);
			for (int i = 0; i < timesToRun; i ++)
				parameters.get(1).evaluate(view, model);
			
			return (parameters.get(0).evaluate(view, model));
		}
		else
			return -1;
	}
}