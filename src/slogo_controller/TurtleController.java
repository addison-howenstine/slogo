package slogo_controller;

import instructions.Instruction;
import instructions.Error;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import slogo_model.SLOGOModel;
import slogo_model.Turtle;
import slogo_view.SLOGOViewExternal;

public class TurtleController implements SLOGOController {

	private SLOGOViewExternal view;
	private List<SLOGOModel> models;
	private List<SLOGOModel> activeModels;
	private SLOGOParser parser;
	private AbstractMap<String, Double> myVarMap;
	private AbstractMap<String, Instruction> myInstructionMap;
	private ResourceBundle currentResourceBundle;

	public TurtleController(SLOGOViewExternal view, SLOGOModel model) {
		this.view = view;
		models = new ArrayList<SLOGOModel>();
		models.add(model);
		activeModels = new ArrayList<SLOGOModel>();
		activeModels.add(model);
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
		for(Instruction inst : instructionList){
			try{
				activeModels.forEach( model -> inst.evaluate(view, model) );
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

	private void addModel(int ID) {
		for(int i = models.size(); i <= ID; i++)
			models.add(new Turtle(view.getMaxX(), view.getMaxY()));
	}

	@Override
	public double tell(List<Integer> newActives) {
		activeModels.clear();
		double last = 0;
		for ( Integer a : newActives ){
			if (a >= models.size() )
				addModel(a);
			activeModels.add(models.get(a));
			last = a;
		}
		return last;
	}

	@Override
	public double modelID() {
		return models.indexOf(activeModels.get(0));
	}

	@Override
	public double numModels() {
		return models.size();
	}

	@Override
	public double ask(List<Integer> tempActives, List<Instruction> instructions) {
		// TODO it would be great to pass a lambda instead of a List of instructions
		Instruction last = instructions.get(instructions.size() - 1);
		instructions.remove(instructions.size() - 1);
		tempActives.forEach(t -> instructions.forEach(i -> i.evaluate(view, models.get(t))));
		return last.evaluate(view, models.get(tempActives.get(tempActives.size() - 1)));
	}
}
