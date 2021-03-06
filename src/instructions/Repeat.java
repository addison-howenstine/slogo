package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class Repeat extends ControlStructureCommand {

	@Override
	public int getNumRequiredParameters() {
		return 2;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		if (canEvaluate()){
			
			int timesToRun = (int) parameters.get(0).evaluate(view, model);
			for (int i = 0; i < timesToRun - 1; i ++){
				parameters.get(1).evaluate(view, model);
			}
			
			return (parameters.get(1).evaluate(view, model));
		}
		else
			return -1;
	}
}