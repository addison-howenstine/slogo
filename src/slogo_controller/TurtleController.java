package slogo_controller;

import instructions.Instruction;
import instructions.Error;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class TurtleController implements SLOGOController {
	
	private SLOGOViewExternal view;
	private SLOGOModel model;
	private SLOGOParser parser;
	private AbstractMap<String, Double> myVarMap;
	private AbstractMap<String, Instruction> myInstructionMap;
	private ResourceBundle currentResourceBundle;

	public TurtleController(SLOGOViewExternal view, SLOGOModel model) {
		this.view = view;
		this.model = model;
		this.myVarMap = new HashMap<String, Double>();
		this.myInstructionMap = new HashMap<String, Instruction>();
		parser = new SLOGOParser();
		// important that Syntax comes after languages!!!
		currentResourceBundle = view.getResourceBundle();
		parser.addPatterns(currentResourceBundle);
		parser.addPatterns("resources/Syntax");
	}

	@Override
	public void run(String command) {
		List<Instruction> instructionList = parser.parse(command, myInstructionMap);
		for(Instruction i : instructionList){
			try{
				i.evaluate(view, model);
			}catch(Exception e){
				//TODO: EVALUATION FAILED - will happen if variable hasn't been created
			}
			// System.out.println("Turtle xCor: " + model.xCor() + " Turtle yCor: " + model.yCor());
		}
	}
	
	@Override
	public AbstractMap<String, Instruction> getInstrMap(){
		return myInstructionMap;
	}
	
	@Override
	public AbstractMap<String, Double> getVarMap(){
		return myVarMap;
	}
	
	private void changeLanguage(){
		parser.removePatterns(currentResourceBundle);
		parser.removePatterns("resources/Syntax");
		currentResourceBundle = view.getResourceBundle();
		parser.addPatterns(currentResourceBundle);
		parser.addPatterns("resources/Syntax");
	}
}
