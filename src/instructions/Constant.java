package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class Constant extends Instruction {
	
	double value;
	
	public Constant(){
	}

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
	public double evaluate(SLOGOView view, SLOGOModel model) {
		return value;
	}

}
