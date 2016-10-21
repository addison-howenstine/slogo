package instructions;
import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class UserInstruction extends Instruction{
	private String name;
	public UserInstruction(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public int getNumRequiredParameters(){
		return 0;
	}
	
	public double evaluate(SLOGOViewExternal view, SLOGOModel model){
		Instruction list = model.getInstructionMap().get(name);
		list.evaluate(view, model);
		
		return -1;
	}
}
