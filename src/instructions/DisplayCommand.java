package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public abstract class DisplayCommand extends Instruction {

	@Override
	public abstract int getNumRequiredParameters();

	@Override
	public abstract double evaluate(SLOGOView view, SLOGOModel model);

}
