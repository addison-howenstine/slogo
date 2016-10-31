package instructions;
import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class For extends ControlStructureCommand{
	/**
	 * 0th param: variable / variable name
	 * 1st param: start
	 * 2nd param: end
	 * 3rd param: increment
	 * 4th param: ListEnd (DON'T USE)
	 * 5th param: ListStart
	 */
	public int getNumRequiredParameters(){
		return 6;
	}
	
	public double evaluate(SLOGOView view, SLOGOModel model){
		double start = parameters.get(1).evaluate(view, model);
		double end = parameters.get(2).evaluate(view, model);
		double increment = parameters.get(3).evaluate(view, model);
		
		Variable var = (Variable) parameters.get(0);
		
		for (double i = start; i <= end; i += increment){
			view.getController().getVarMap().put(var.getName(), i);
			view.addUserVariable(var.getName(), view.getController().getVarMap().get(var.getName()));
			
			parameters.get(5).evaluate(view, model);
		}
		
		return view.getController().getVarMap().get(var.getName());
	}
}
