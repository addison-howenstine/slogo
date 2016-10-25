package instructions;

import java.util.ArrayList;
import java.util.List;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class Ask extends Instruction {

	@Override
	public int getNumRequiredParameters() {
		return 2;
	}

	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model) {
		List<Integer> toAsk = new ArrayList<Integer>();
		parameters.get(0).parameters.forEach(p -> toAsk.add((int) p.evaluate(view, model)));
		return view.getController().ask(toAsk, parameters.get(1).parameters);
	}

}
