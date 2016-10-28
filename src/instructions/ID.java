package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class ID extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		return 0;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		return view.getController().modelID();
	}

}
