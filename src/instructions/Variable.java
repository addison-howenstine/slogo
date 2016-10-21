package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class Variable extends Instruction{
	private String name;
	public Variable(){
		
	}
	
	@Override
	public int getNumRequiredParameters(){
		return 0;
	}
	
	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model){
		//TODO: NEED ERROR CHECKING FOR WHEN VARIABLE DOESN'T EXIST IN MAP
		return model.getVarMap().get(name);
	}
	
	public void setName(String s){
		this.name = s;
	}
	
	public String getName(){
		return this.name;
	}
}
