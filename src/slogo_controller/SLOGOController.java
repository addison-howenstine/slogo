package slogo_controller;
import instructions.Instruction;
import java.util.AbstractMap;
import java.util.List;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public interface SLOGOController {
	
	/**
	 * Accepts a list of commands from the View to be
	 * parsed and used to control the Model instances
	 * 
	 * @param command - an input string of commands to be parsed
	 */
	public void run(String command);
	
	/**
	 * 
	 * @return
	 */
	public AbstractMap<String, Instruction> getInstrMap();
	
	
	/**
	 * 
	 * @return
	 */
	public AbstractMap<String, Double> getVarMap();
	
	/**
	 * @return ID of model currently active (which model will act next)
	 */
	public double modelID();
			
	/**
	 * @param newActives - List of model IDs to set active
	 * @return
	 */
	public double tell(List<Integer> newActives);
	
	/**
	 * if a model is currently active, make it inactive
	 * if a model is currently inactive, make it active
	 * 
	 * @param newActive - model to toggle
	 */
	public void toggleActive(Integer newActive);
	
	/**
	 * Have a specific list of models complete a list of instructions
	 * 
	 * @param tempActives
	 * @param instructions
	 * @return
	 */
	public double ask(List<Integer> tempActives, List<Instruction> instructions);
		
	/**
	 * @return a list of models this Controller is controlling
	 */
	public List<SLOGOModel> getModels();
	
	/**
	 * Controller will check the language currently in use by view
	 * to update which language it uses to read instructions
	 */
	public void changeLanguage();

	public void generateSettingsFile(String fileName);

	public void loadSettingsFile(String fileName, SLOGOView view);
}
