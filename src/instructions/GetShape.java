package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class GetShape extends DisplayCommand {

	@Override
	public int getNumRequiredParameters() {
		return 0;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		return view.getImage();
	}

}
