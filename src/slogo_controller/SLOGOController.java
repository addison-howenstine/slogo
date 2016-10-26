package slogo_controller;
import instructions.Instruction;

import java.util.AbstractMap;
import java.util.List;

import slogo_model.SLOGOModel;

public interface SLOGOController {
	
	/**
	 * Accepts a list of commands from the View to be
	 * parsed and handled by the model to control the
	 * turtle's position, heading, pen, visibility, etc.
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
	
	public double modelID();
			
	public double tell(List<Integer> newActives);
	
	public double ask(List<Integer> tempActives, List<Instruction> instructions);
	
	public List<SLOGOModel> getModels();
}
