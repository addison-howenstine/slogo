package instructions;
import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class DoTimes extends Instruction{
	/**
	 * Expect first parameter to be Variable
	 * Second parameter will be Constant
	 * Expect third parameter to be ListStart
	 */
	
	public int getNumRequiredParameters(){
		return 3;
	}
	
	public double evaluate(SLOGOViewExternal view, SLOGOModel model){
		double numTimesToRepeat = parameters.get(1).evaluate(view, model);
		
		for (int i = 1; i <= numTimesToRepeat; i++){
			view.getController().getVarMap().put(((Variable)parameters.get(0)).getName(), (double) i);
			parameters.get(2).evaluate(view, model);
		}
		return view.getController().getVarMap().get(((Variable) parameters.get(0)).getName());
	}
}
