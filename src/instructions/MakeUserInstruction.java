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
		UserInstruction uInstruction = (UserInstruction) view.getController().getInstrMap().get( ( (UserInstruction) parameters.get(0) ).getName() );

		// parameters.get(2) is a ListStart of actions, pass to UserInstruction
		uInstruction.setActions((ListStart) parameters.get(2));

		view.addUserCommand(uInstruction.getName());
		view.getController().getInstrMap().put(uInstruction.getName(), uInstruction);

		//Shouldn't need to return anything
		return -1;
	}
}
