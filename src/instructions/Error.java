package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class Error extends Instruction {
	
	String errorMessage;
	
	public Error(){
		errorMessage = "";
	}
	
	public Error(String errorMessage){
		this.errorMessage = errorMessage;
	}

	@Override
	public int getNumRequiredParameters() {
		return 0;
	}

	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model) {
		if (canEvaluate()){
			view.showError(errorMessage);
		}
		return 0;
	}

}
