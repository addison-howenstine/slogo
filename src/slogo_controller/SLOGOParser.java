package slogo_controller;

import instructions.*;
import instructions.Error;
import slogo_model.SLOGOModel;

import java.util.Enumeration;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap;
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
	 * TODO: Change this javadoc description 
	 *
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
	 * 
	 */
	protected List<Instruction> parse(String command, AbstractMap<String, Instruction> instrMap){
		List<Instruction> instructionList = new ArrayList<Instruction>();

		String[] commandLines = command.split("\\n");
		StringBuilder sb = new StringBuilder();
		for(String line : commandLines){
			// TODO fix this line, it's not actually catching #'s, maybe char is different somehow?
			if(!getSymbol(line).equals("Comment")){
				sb.append(line);
			}
			sb.append(" ");
		}
		
		String finalInstructionSet = sb.toString();
		Scanner instructionScanner = new Scanner(finalInstructionSet).useDelimiter("\\s+");
		while (instructionScanner.hasNext()){
			instructionList.add(createNextInstructionFromText(instructionScanner, instrMap));
		}
		instructionScanner.close();
		
		return instructionList;
	}
	
	private Instruction createNextInstructionFromText(Scanner instructionScanner, AbstractMap<String, Instruction> instrMap){
		String typedInstruction = instructionScanner.next();
		//Check if instruction is a custom user instruction
		if (instrMap.containsKey(typedInstruction)){
			return instrMap.get(typedInstruction);
		}
		
		String actualInstruction = getSymbol(typedInstruction);
		if(actualInstruction.equals(ERROR))
			;//throw CommandNotFound error?

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
		if(instruction instanceof ListStart){
			parameters = groupInstructionList(instructionScanner, instrMap);
		}
		if (instruction instanceof Variable){
			((Variable) instruction).setName(typedInstruction);
		}
		
		for(int i = 0; i < instruction.getNumRequiredParameters(); i++){
			if (instruction instanceof MakeUserInstruction && i == 0){
				parameters.add(new UserInstruction(instructionScanner.next()));
				continue;
			}
			parameters.add(createNextInstructionFromText(instructionScanner, instrMap));
		}
		
		instruction.setParameters(parameters);
		return instruction;
	}
	
	private List<Instruction> groupInstructionList(Scanner s, AbstractMap<String, Instruction> instrMap){
		List<Instruction> groupedList = new ArrayList<Instruction>();
		while (s.hasNext()){
			Instruction toAdd = createNextInstructionFromText(s, instrMap);
			if (toAdd instanceof ListStart)
				groupedList.addAll(groupInstructionList(s, instrMap));
			if (toAdd instanceof ListEnd){
				return groupedList;
			}
			groupedList.add(toAdd);
		}
		return groupedList;
	}

	// adds the given resource file to this language's recognized types
	protected void addPatterns (ResourceBundle resources) {
		Enumeration<String> iter = resources.getKeys();
		while (iter.hasMoreElements()) {
			String key = iter.nextElement();
			String regex = resources.getString(key);
			mySymbols.add(new SimpleEntry<>(key,
					// THIS IS THE IMPORTANT LINE
					Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
		}
	}

	protected void addPatterns(String syntax){
		ResourceBundle resources = ResourceBundle.getBundle(syntax);
		addPatterns(resources);
	}

	// removes the given resource file to this language's recognized types
	protected void removePatterns (ResourceBundle resources) {
		Enumeration<String> iter = resources.getKeys();
		while (iter.hasMoreElements()) {
			String key = iter.nextElement();
			String regex = resources.getString(key);
			mySymbols.remove(new SimpleEntry<>(key,
					Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
		}
	}
	
	protected void removePatterns(String syntax){
		ResourceBundle resources = ResourceBundle.getBundle(syntax);
		removePatterns(resources);
	}

	// returns the language's type associated with the given text if one exists 
	protected String getSymbol (String text) {
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
