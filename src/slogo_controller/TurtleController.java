package slogo_controller;

import instructions.Instruction;
import instructions.Error;
import java.util.List;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class TurtleController implements SLOGOController {
	
	SLOGOViewExternal view;
	SLOGOModel model;
	SLOGOParser parser;

	public TurtleController(SLOGOViewExternal view, SLOGOModel model) {
		this.view = view;
		this.model = model;
		parser = new SLOGOParser();
		// important that Syntax comes after languages!!!
		parser.addPatterns(view.getResourceBundle());
		parser.addPatterns("resources/Syntax");
	}

	@Override
	public void run(String command) {
		List<Instruction> instructionList = parser.parse(command, model);
		for(Instruction i : instructionList){
			try{
				i.evaluate(view, model);
			}catch(Exception e){
				//TODO: EVALUATION FAILED - will happen if variable hasn't been created
			}
			// System.out.println("Turtle xCor: " + model.xCor() + " Turtle yCor: " + model.yCor());
		}
	}
	
	private void changeLanguage(){
		// TODO observer checking if language is changed
		//		if language is changed, removes previous language from parser, adds new one
	}
}
