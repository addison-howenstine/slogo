package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class SetPenSize extends DisplayCommand {

	@Override
	public int getNumRequiredParameters() {
		return 1;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		int index = (int) parameters.get(0).evaluate(view, model);
		view.setPenWidth(index);
		return index;
	}

}
