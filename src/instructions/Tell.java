package instructions;

import java.util.ArrayList;
import java.util.List;

import slogo_controller.TurtleController;
import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class Tell extends MultipleTurtlesCommand {

	@Override
	public int getNumRequiredParameters() {
		return 1;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		List<Integer> activeModels = new ArrayList<Integer>();
		parameters.get(0).parameters.forEach(param -> activeModels.add( (int) param.evaluate(view, model) )) ;
		return view.getController().tell(activeModels);

	}
}
