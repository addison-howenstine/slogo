package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class Constant extends Instruction {
	
	double value;

	public Constant(double value) {
		this.value = value;
	}
	
	public void setValue(double value){
		this.value = value;
	}
	
	@Override
	public int getNumRequiredParameters() {
		return 0;
	}

	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model) {
		return value;
	}

}
