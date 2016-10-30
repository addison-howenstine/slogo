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
import slogo_view.Playground;
import slogo_view.SLOGOView;

public class TurtleController implements SLOGOController {

	private SLOGOView view;
	private List<SLOGOModel> models;
	private List<Integer> activeModels;
	private int activeModelID;
	private SLOGOParser parser;
	private AbstractMap<String, Double> myVarMap;
	private AbstractMap<String, Instruction> myInstructionMap;
	private ResourceBundle currentResourceBundle;

	public TurtleController(SLOGOView view) {
		this.view = view;
		models = new ArrayList<SLOGOModel>();
		activeModels = new ArrayList<Integer>();
		createNewModel();
		activeModels.add(0);
		this.myVarMap = new HashMap<String, Double>();
		this.myInstructionMap = new HashMap<String, Instruction>();
		parser = new SLOGOParser(this.view);
		// important that Syntax comes after languages!!!
		currentResourceBundle = view.getResourceBundle();
		parser.addPatterns(currentResourceBundle);
		parser.addPatterns("resources/Syntax");
	}

	@Override
	public void run(String command){
		List<Instruction> instructionList = null;
		try {
			instructionList = parser.parse(command, myInstructionMap);
		}catch (Exception e){
			view.showError(e.getMessage());
			return;
		}
		for(Instruction inst : instructionList){
			if (inst == null){
				continue;
			}
			try{
				activeModels.forEach( model -> {
					activeModelID = model;
					inst.evaluate(view, models.get(activeModelID));
				});
			}catch(Exception e){
				//TODO: EVALUATION FAILED - will happen if variable hasn't been created
				System.out.println(e);
			}
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

	public void changeLanguage(){
		parser.clearPatterns();
		parser.addPatterns(view.getResourceBundle());
		parser.addPatterns("resources/Syntax");
	}

	private void addModel(int ID) {
		// create as many new models as are needed
		for(int i = models.size(); i <= ID; i++){
			createNewModel();
		}
		view.updateScreen();
	}
	
	private void createNewModel(){
		SLOGOModel newTurtle = new Turtle(view.getMaxX(), view.getMaxY());
		models.add(newTurtle);
		((Turtle) newTurtle).addObserver((Playground) view);
	}

	@Override
	public double tell(List<Integer> newActives) {
		activeModels.clear();
		for ( Integer a : newActives ){
			activeModelID = a;
			if (activeModelID >= models.size() )
				addModel(activeModelID);
			activeModels.add(activeModelID);
		}
		return activeModelID;
	}

	@Override
	public double modelID() {
		return activeModelID;
	}

	@Override
	public double ask(List<Integer> tempActives, List<Instruction> instructions) {
		// TODO it would be great to pass a lambda instead of a List of instructions
		Instruction last = instructions.get(instructions.size() - 1);
		instructions.remove(instructions.size() - 1);
		tempActives.forEach(t -> {
			activeModelID = t;
			instructions.forEach(i -> i.evaluate(view, models.get(activeModelID)));
		});
		activeModelID = tempActives.get(tempActives.size() - 1);
		return last.evaluate(view, models.get(activeModelID));
	}

	@Override
	public List<SLOGOModel> getModels() {
		// TODO it would be great to get rid of this method and replace with functional programming so we don't give away pointer to models
		return models;
	}
}
