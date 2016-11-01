package instructions;
import java.util.ArrayList;
import java.util.List;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class UserInstruction extends Instruction{
	private String name;
	private List<Variable> variableParameters = null;
	private ListStart actions = null;

	public UserInstruction(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public int getNumRequiredParameters(){
		if (variableParameters == null)
			return 0;
		else
			return variableParameters.size();
	}

	protected void setVariableParameters(List<Variable> variableParameters){
		this.variableParameters = variableParameters;
	}
	
	protected void setActions(ListStart actions){
		this.actions = actions;
	}

	public double evaluate(SLOGOView view, SLOGOModel model){
		for (int i = 0; i < parameters.size(); i++){
			// use MakeVariable instruction so when ListStart is evaluated, 
			// that variable disappears afterwards
			List<Instruction> makeVariableParams = new ArrayList<Instruction>();
			// match variable with value for variable passed in to UserInstruction parameters
			makeVariableParams.add(variableParameters.get(i));
			makeVariableParams.add(this.parameters.get(i));
			actions.parameters.add(i, new MakeVariable(makeVariableParams));
		}
		return actions.evaluate(view, model);
	}
}
