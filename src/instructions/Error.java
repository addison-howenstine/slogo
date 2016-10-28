package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

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
	public double evaluate(SLOGOView view, SLOGOModel model) {
		if (canEvaluate()){
			view.showError(errorMessage);
		}
		return 0;
	}

}
