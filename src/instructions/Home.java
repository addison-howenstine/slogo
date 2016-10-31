package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class Home extends TurtleCommand {

	@Override
	public int getNumRequiredParameters() {
		return 0;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		if (canEvaluate()){
			return model.home();
		}
		else
			return -1;
	}
}
