package instructions;
import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class DoTimes extends ControlStructureCommand{
	/**
	 * Expect first parameter to be Variable
	 * Second parameter will be Constant, or will be evaluated to constant
	 * Expect third parameter to be ListEnd
	 * Expect fourth parameter to be ListStart
	 */
	
	public int getNumRequiredParameters(){
		return 4;
	}
	
	public double evaluate(SLOGOView view, SLOGOModel model){
		double numTimesToRepeat = parameters.get(1).evaluate(view, model);
		Variable var = (Variable) parameters.get(0);
		
		for (int i = 1; i <= numTimesToRepeat; i++){
			view.getController().getVarMap().put(var.getName(), (double) i);
			view.addUserVariable(var.getName(), view.getController().getVarMap().get(var.getName()));
			
			parameters.get(3).evaluate(view, model);
		}
		return view.getController().getVarMap().get(((Variable) parameters.get(0)).getName());
	}
}
