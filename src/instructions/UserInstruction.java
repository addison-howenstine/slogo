package instructions;
import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

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
	
	public double evaluate(SLOGOView view, SLOGOModel model){
		Instruction list = view.getController().getInstrMap().get(name);
		return list.evaluate(view, model);
	}
}
