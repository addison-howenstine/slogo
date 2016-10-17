package slogo_controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class SLOGOInstructionNode {

	protected SLOGOInstructionNode left, right;
	protected double value;
	protected String command;

	public static final String DEFAULT_COMMAND = "doNothing";

	public SLOGOInstructionNode(){
		this.left = null;
		this.right = null;
		this.value = 0;
		this.command = DEFAULT_COMMAND;
	}

	public SLOGOInstructionNode(SLOGOInstructionNode left, SLOGOInstructionNode right, double value, String command) {
		this.left = left;
		this.right = right;
		this.value = value;
		this.command = command;
	}


	public double evaluate() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		String commandType = ResourceBundle.getBundle("resources/InstructionPaths").getString(command);
		Class<?> c = Class.forName(commandType);
		Object o = c.newInstance();
		Method commandMethod = c.getMethod(command);
		return (double) commandMethod.invoke(o, left, right);
	}

	private void doNothing(){
		// do nothing
	}

}
