package instructions;

import java.util.List;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class MakeVariable extends ControlStructureCommand{
	public MakeVariable(){
	}
	
	public MakeVariable(List<Instruction> parameters){
		this.parameters = parameters;
	}
	
	@Override
	public int getNumRequiredParameters() {
		return 2;
	}
	
	@Override
	public double evaluate(SLOGOView view, SLOGOModel model){
		double variableVal = parameters.get(1).evaluate(view, model);
		String variableName = ((Variable) parameters.get(0)).getName();
		view.addUserVariable(variableName, variableVal);
		view.getController().getVarMap().put(variableName, variableVal);
		return variableVal;		
	}
}
