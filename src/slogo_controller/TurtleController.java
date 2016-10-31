package slogo_controller;

import instructions.Instruction;
import instructions.TurtleCommand;
import instructions.Error;
import slogo_library.*;
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
				if (inst instanceof TurtleCommand)
					activeModels.forEach( model -> {
						activeModelID = model;
						inst.evaluate(view, models.get(activeModelID));
					});
				else
					inst.evaluate(view,  models.get(activeModelID));
			}catch(Exception e){
				view.showError(e.getMessage());
				return;
			}
		}
	}
	
	/**
	 * Method which generates a serialized file at lib/fileName. Used to
	 * store current state of the SLOGO playground.
	 * @param fileName - name of the file to be generated. Will be in format fileName.ser
	 */
	public void generateSettingsFile(String fileName){
		SettingsGenerator generator = new SettingsGenerator();
		try{
			generator.generateSerializedObj(fileName, myVarMap, myInstructionMap);
		}catch(Exception e){
			view.showError(e.getMessage());
		}
	}
	
	/**
	 * Loads a selected file into memory - updates instruction map and variable map.
	 * @param fileName - name of file to be loaded
	 * @param view - SLOGOView to update the UI with vars/instructions loaded from file
	 */
	public void loadSettingsFile(String fileName, SLOGOView view){
		try{
			SettingsLoader loader = new SettingsLoader(fileName);
			loader.loadInstructionsToMap(myInstructionMap, view);
			loader.loadVariablesToMap(myVarMap, view);
		}catch(Exception e){
			view.showError(e.getMessage());
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
	public void toggleActive(Integer newActive) {
		if (newActive >= models.size())
			addModel(newActive);
		if (activeModels.contains(newActive))
			activeModels.remove(newActive);
		else
			activeModels.add(newActive);
	}

	@Override
	public double modelID() {
		return activeModelID;
	}

	@Override
	public double ask(List<Integer> tempActives, List<Instruction> instructions) {
		double toReturn = 0;
		for (Integer a : tempActives){
			activeModelID = a;
			if ( activeModelID >= models.size() )
				addModel(activeModelID);
			for (Instruction i : instructions)
				toReturn = i.evaluate(view, models.get(activeModelID));
		}
		// at the end, set activeModelID to next true active model
		activeModelID = activeModels.get(0);
		return toReturn;
	}

	@Override
	public List<SLOGOModel> getModels() {
		return models;
	}
}
