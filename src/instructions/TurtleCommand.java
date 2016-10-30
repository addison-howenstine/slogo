package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public abstract class TurtleCommand extends Instruction{

	@Override
	public abstract int getNumRequiredParameters();


}
