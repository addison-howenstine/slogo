package instructions;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class Variable extends Instruction{
	private String name;
	public Variable(){
	}
	
	public Variable(String name){
		setName(name);
	}
	
	@Override
	public int getNumRequiredParameters(){
		return 0;
	}
	
	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) throws RuntimeException{
		if (!view.getController().getVarMap().containsKey(name)){
			throw new NullPointerException("Variable " + name + " hasn't been created yet.");
		}
		return view.getController().getVarMap().get(name);
		
	}
	
	public void setName(String s){
		this.name = s;
	}
	
	public String getName(){
		return this.name;
	}
}
