package slogo_controller;

import instructions.Instruction;
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
		parser.addPatterns("resources/Syntax");
		parser.addPatterns(view.getResourceBundle());
	}

	@Override
	public void run(String command) {
		List<Instruction> instructionList = parser.parse(command);
		for(Instruction i : instructionList){
			i.evaluate(view, model);
		}
	}
	
	private void changeLanguage(){
		// TODO observer checking if language is changed
		//		if language is changed, removes previous language from parser, adds new one
	}
	
	// TODO have observer watching turtle's coordinates, heading, pen, and visibility
	// TODO alert view if any of the above change

}
