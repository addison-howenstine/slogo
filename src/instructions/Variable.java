package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class Variable extends Instruction{
	private String name;
	public Variable(){
		
	}
	
	@Override
	public int getNumRequiredParameters(){
		return 0;
	}
	
	@Override
	public double evaluate(SLOGOView view, SLOGOModel model){
		//TODO: NEED ERROR CHECKING FOR WHEN VARIABLE DOESN'T EXIST IN MAP
		return view.getController().getVarMap().get(name);
	}
	
	public void setName(String s){
		this.name = s;
	}
	
	public String getName(){
		return this.name;
	}
}
