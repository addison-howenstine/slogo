package instructions;

import java.util.ArrayList;
import java.util.List;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class GroupStart extends Instruction {
	
	Instruction command;
	
	public void setCommand(Instruction command){
		this.command = command;
	}

	@Override
	public int getNumRequiredParameters() {
		// Group can have an unlimited number of parameters but shouldn't look for them in parser
		return 0;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
				
		if (UnlimitedParametersInstruction.class.isAssignableFrom(command.getClass())){
			command.setParameters(parameters);
			return ((UnlimitedParametersInstruction) command).evaluateUnlimitedParameters(view, model);
		}
		else{
			List<Instruction> limitedParameters = new ArrayList<Instruction>();
			for(int i = 1; i <= command.getNumRequiredParameters(); i++){
				limitedParameters.add(0, parameters.get(parameters.size() - i));
			}
			command.setParameters(limitedParameters);
			return command.evaluate(view, model);
		}
	}

}
