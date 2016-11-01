package instructions;
import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class MakeUserInstruction extends ControlStructureCommand{

	@Override
	// First parameter is the UserInstruction
	// Second parameter is a ListStart of variables to be used in instruction set
	// Third parameter is a ListStart with the entire instruction set
	public int getNumRequiredParameters(){
		return 3;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model){
		if ( ! ( parameters.get(0) instanceof UserInstruction)){
			// TODO if .get(0) isn't a UserInstruction, throw an error
		}
		UserInstruction uInstruction = (UserInstruction) parameters.get(0);

		// parameters.get(1) is a ListStart of variable names
		List<Variable> variableParameters = new ArrayList<Variable>();
		parameters.get(1).parameters.forEach(i -> {
			if (! (i instanceof Variable) ){
				//TODO throw an error
			}
			variableParameters.add((Variable) i);
		});
		// give UserInstruction a list of the parameters it will need to look for
		uInstruction.setVariableParameters(variableParameters);

		// parameters.get(2) is a ListStart of actions, pass to UserInstruction
		uInstruction.setActions((ListStart) parameters.get(2));

		view.addUserCommand(uInstruction.getName());
		view.getController().getInstrMap().put(uInstruction.getName(), uInstruction);

		//Shouldn't need to return anything
		return -1;
	}
}
