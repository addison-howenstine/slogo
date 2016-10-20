package slogo_controller;

import instructions.*;
import instructions.Error;

import java.util.Enumeration;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;


public class SLOGOParser {
	// "types" and the regular expression patterns that recognize those types
	// note, it is a list because order matters (some patterns may be more generic)
	private List<Entry<String, Pattern>> mySymbols;
	private final String ERROR = "Input text cannot be parsed into instructions";


	public SLOGOParser () {
		mySymbols = new ArrayList<>();
	}

	/**
	 * Reads command text in line by line, if line is not a comment, 
	 * the line is split into words and turned into Instruction objects. 
	 * Unrelated Instructions are returned as a List to be evaluated individually in Controller
	 * If an Instruction is dependent on the return value of another necessary Instruction,
	 * the necessary Instruction on which the top level root Instruction is dependent will be linked
	 * as a parameter child of the root Instruction. This necessary Instruction will
	 * be evaluated recursively by the Controller when evaluating the root Instruction
	 * 
	 * @param command - input text of commands to be parsed into instructions
	 * @return list of Instructions for Controller to execute
	 */
	public List<Instruction> parse(String command){
		List<Instruction> instructionList = new ArrayList<Instruction>();

		String[] commandLines = command.split("\\n");
		for(String line : commandLines){
			// TODO fix this line, it's not actually catching #'s, maybe char is different somehow?
			if(!getSymbol(line).equals("Comment")){
				Scanner instructionScanner = new Scanner(line).useDelimiter("\\s+");
				while(instructionScanner.hasNext()){
					instructionList.add(createNextInstructionFromText(instructionScanner));
				}
				instructionScanner.close();
			}
		}
		return instructionList;
	}
	
	private Instruction createNextInstructionFromText(Scanner instructionScanner){
		String typedInstruction = instructionScanner.next();
		String actualInstruction = getSymbol(typedInstruction);
		if(actualInstruction.equals(ERROR))
			;//throw CommandNotFound error?

		System.out.println("TYPED INSTRUCTION: " + typedInstruction);
		System.out.println("ACTUAL INSTRUCTION: "+ actualInstruction);
		Instruction instruction = null;
		try {
			// instantiate a class and object for command instructions
			Class<?> c = Class.forName("instructions." + actualInstruction);
			instruction = (Instruction) c.newInstance();
			} 
		catch (ClassNotFoundException e) {
			// instead of throwing an exception, pass error method
			instruction = new Error("Method name not found!");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
			
		List<Instruction> parameters = new ArrayList<Instruction>();
		if(instruction instanceof Constant){
			((Constant) instruction).setValue(Integer.parseInt(typedInstruction));
		}
		if(instruction instanceof ListStart)
			parameters = groupInstructionList(instructionScanner);
		for(int i = 0; i < instruction.getNumRequiredParameters(); i++){
			parameters.add(createNextInstructionFromText(instructionScanner));
		}
		instruction.setParameters(parameters);
		return instruction;
	}
	
	private List<Instruction> groupInstructionList(Scanner s){
		List<Instruction> groupedList = new ArrayList<Instruction>();
		while (s.hasNext()){
			Instruction toAdd = createNextInstructionFromText(s);
			if (toAdd instanceof ListStart)
				groupedList.addAll(groupInstructionList(s));
			if (toAdd instanceof ListEnd){
				System.out.println("does this happen");
				return groupedList;
			}
			groupedList.add(toAdd);
		}
		return groupedList;
	}

	// adds the given resource file to this language's recognized types
	public void addPatterns (ResourceBundle resources) {
		Enumeration<String> iter = resources.getKeys();
		while (iter.hasMoreElements()) {
			String key = iter.nextElement();
			String regex = resources.getString(key);
			mySymbols.add(new SimpleEntry<>(key,
					// THIS IS THE IMPORTANT LINE
					Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
		}
	}

	public void addPatterns(String syntax){
		ResourceBundle resources = ResourceBundle.getBundle(syntax);
		addPatterns(resources);
	}

	// removes the given resource file to this language's recognized types
	public void removePatterns (ResourceBundle resources) {
		Enumeration<String> iter = resources.getKeys();
		while (iter.hasMoreElements()) {
			String key = iter.nextElement();
			String regex = resources.getString(key);
			mySymbols.remove(new SimpleEntry<>(key,
					Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
		}
	}

	// returns the language's type associated with the given text if one exists 
	public String getSymbol (String text) {
		for (Entry<String, Pattern> e : mySymbols) {
			if (match(text, e.getValue())) {
				return e.getKey();
			}
		}
		return ERROR;
	}

	// returns true if the given text matches the given regular expression pattern
	private boolean match (String text, Pattern regex) {
		// THIS IS THE KEY LINE
		return regex.matcher(text).matches();
	}
}
