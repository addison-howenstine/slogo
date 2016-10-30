package instructions;
import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;
import java.util.AbstractMap;

public class MakeUserInstruction extends Instruction{
	
	@Override
	//Third parameter should be a ListStart with the entire instruction set
	public int getNumRequiredParameters(){
		return 3;
	}
	
	@Override
	public double evaluate(SLOGOView view, SLOGOModel model){
		UserInstruction instr = (UserInstruction) parameters.get(0);
		view.addUserCommand(instr.getName());
		view.getController().getInstrMap().put(instr.getName(), parameters.get(2));

		//Shouldn't need to return anything
		return -1;
	}
	
	public double evaluate(SLOGOView view){
		evaluate(view, null);
		return -1;
	}
}
