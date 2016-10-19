package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class Variable extends Instruction{
	private String name;
	public Variable(String s){
		this.name = s;
	}
	
	@Override
	public int getNumRequiredParameters(){
		return 0;
	}
	
	@Override
	public double evaluate(SLOGOViewExternal view, SLOGOModel model){
		//NOTE: This should never be called hopefully
		return 0;
	}
	
	public void setName(String s){
		this.name = s;
	}
	
	public String getName(){
		return this.name;
	}
}
