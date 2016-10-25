package instructions;

import java.util.ArrayList;
import java.util.List;

import slogo_controller.TurtleController;
import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class Tell extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		return 1;
	}

	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model) {
		List<Integer> activeModels = new ArrayList<Integer>();
		parameters.get(0).parameters.forEach(param -> activeModels.add( (int) param.evaluate(view, model) )) ;
		return view.getController().tell(activeModels);

	}
}
