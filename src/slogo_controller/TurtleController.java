package slogo_controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

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
		SLOGOInstructionNode root = parser.parse(command);
		
		// TODO start from bottom of InstructionTree defined by root and pop all instructions
	}
	
	private double evaluateNode(SLOGOInstructionNode curr){
		String command = curr.command;
		SLOGOInstructionNode left = curr.left;
		SLOGOInstructionNode right = curr.right;
		
		// get name of class for command instructions
		String instructionType = ResourceBundle.getBundle("resources/InstructionPaths").getString(command);
		Class<?> c;
		try {
			// instantiate a class and object for command instructions
			c = Class.forName(instructionType);
			Object o = c.newInstance();
			
			// invoke method in instruction class by the same name as [command]
			// give this method view, model, left, and right so it can generically decide what it needs
			// to complete instruction
			Method commandMethod = c.getMethod(command);
			return (double) commandMethod.invoke(o, view, model, left, right);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	private void changeLanguage(){
		// TODO observer checking if language is changed
		//		if language is changed, removes previous language from parser, adds new one
	}
	
	// TODO have observer watching turtle's coordinates, heading, pen, and visibility
	// TODO alert view if any of the above change

}
