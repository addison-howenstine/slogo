package instructions;

import java.util.ArrayList;
import java.util.List;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class Ask extends MultipleTurtlesCommand {

	@Override
	public int getNumRequiredParameters() {
		return 2;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		List<Integer> toAsk = new ArrayList<Integer>();
		parameters.get(0).parameters.forEach(p -> toAsk.add((int) p.evaluate(view, model)));
		return view.getController().ask(toAsk, parameters.get(1).parameters);
	}

}
