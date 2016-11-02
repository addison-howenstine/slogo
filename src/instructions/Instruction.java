package instructions;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

/**
 * Instructions are used as nodes in a tree of Instructions 
 * for the Controller to execute recursively by calling evaluate
 * passing the view and model to the Instruction. Parameters are parsed in
 * by the SLOGOParser and assigned to the Instruction before it is evaluated
 * in the Controller.
 * 
 * @author Addison
 *
 */
public abstract class Instruction implements Serializable{

	public List<Instruction> parameters;
	private static final long serialVersionUID = 7526471155622776147L;
	
	public Instruction(){
		parameters = new ArrayList<Instruction>();
	}

	public void setParameters(List<Instruction> parameters){
		this.parameters = parameters;
	}

	/**
	 * @return number of parameters needed to satisfy the evaluate method
	 */
	public abstract int getNumRequiredParameters();

	/**
	 * Every instruction has a double return, some methods are performed
	 * on a model, some on a view, and some on neither. This abstract method
	 * allows for any of these situations, but does not require one.
	 * 
	 * @param view - view to which current Control is pointing
	 * @param model - model to which current Control is pointing
	 * @return value returned by the method associated with Instruction
	 */
	public abstract double evaluate(SLOGOView view, SLOGOModel model);

	public boolean canEvaluate(){
		return parameters.size() == getNumRequiredParameters();
	}

}
