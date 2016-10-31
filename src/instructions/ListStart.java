package instructions;

import java.util.ArrayList;
import java.util.List;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

/**
 * A ListStart holds everything within a List denoted by [ brackets ]
 * These can be used to group parameter inputs or for variable scope.
 * All :variables declared within List brackets will be deleted after
 * everything within brackets is evaluated.
 * 
 * @author Addison
 *
 */
public class ListStart extends Instruction{

	@Override
	public int getNumRequiredParameters() {
		// List can have an unlimited number of parameters but shouldn't look for them in parser
		return 0;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		// For a ListStart, parameters are set by the parser to include everything between brackets
		int numParams = parameters.size();
		List<String> variablesToRemove = new ArrayList<String>();
		double toReturn = 0;
		for (int i = 0; i < numParams; i++){
			Instruction nextI = parameters.get(i);
			if(nextI instanceof MakeVariable){
				// if a variable is being declared within brackets, keep track of the name
				// so it can be deleted later
				variablesToRemove.add(((Variable) nextI.parameters.get(0)).getName());
			}
			toReturn = parameters.get(i).evaluate(view, model);
		}
		variablesToRemove.forEach(key -> {
			view.getController().getVarMap().remove(key);
			view.removeUserVariable(key);
		});
		return toReturn;

	}

}
