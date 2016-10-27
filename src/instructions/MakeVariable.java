package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class MakeVariable extends Instruction{
	public MakeVariable(){
	}
	
	@Override
	public int getNumRequiredParameters() {
		return 2;
	}
	
	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model){
		double variableVal = parameters.get(1).evaluate(view, model);
		String variableName = ((Variable) parameters.get(0)).getName();
		view.addUserVariable(variableName, variableVal);
		//TODO: Add to mapping
		view.getController().getVarMap().put(variableName, variableVal);
		
		return variableVal;		
	}
}
