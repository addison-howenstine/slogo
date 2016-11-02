package slogo_controller;

import instructions.*;

import slogo_view.SLOGOView;

import java.util.Enumeration;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.*;


/**
 * Parses text of instructions into actual Instruction intances
 * Derived from parsing code by @author Robert Duvall
 * 
 * @author Addison
 */
public class SLOGOParser {
	// "types" and the regular expression patterns that recognize those types
	// note, it is a list because order matters (some patterns may be more generic)
	private List<Entry<String, Pattern>> mySymbols;
	private final String ERROR = "Input text cannot be parsed into instructions";
	private SLOGOView view;


	public SLOGOParser (SLOGOView view) {
		mySymbols = new ArrayList<>();
		this.view = view;
	}

	/**
	 * Reads in a line or multiple lines of instructions and creates a
	 * List of Instructions for Controller to evaluate
	 * 
	 * @param command - input text of commands to be parsed into instructions
	 * @return list of Instructions for Controller to execute
	 * 
	 */
	protected List<Instruction> parse(String command, AbstractMap<String, Instruction> instrMap) throws Exception{
		List<Instruction> instructionList = new ArrayList<Instruction>();

		String[] commandLines = command.split("\\n");
		StringBuilder sb = new StringBuilder();
		for(String line : commandLines){
			if(! ( getSymbol(line).equals("Comment") || getSymbol(line + " ").equals("Comment"))){
				sb.append(line);
			}
			sb.append(" ");
		}

		String finalInstructionSet = sb.toString();
		Scanner instructionScanner = new Scanner(finalInstructionSet).useDelimiter("\\s+");
		while (instructionScanner.hasNext()){
			try {
				instructionList.add(createNextInstructionFromText(instructionScanner, instrMap));
			}catch (Exception e){
				throw e;
			}
		}
		instructionScanner.close();

		return instructionList;
	}

	private Instruction createNextInstructionFromText (Scanner instructionScanner, AbstractMap<String, Instruction> instrMap) throws Exception{
		String typedInstruction = instructionScanner.next();

		String actualInstruction = getSymbol(typedInstruction);
		Instruction instruction;


		if(actualInstruction.equals(ERROR))
			;//throw CommandNotFound error?
		
		if (instrMap.containsKey(typedInstruction)){
			Instruction tempInstruction = instrMap.get(typedInstruction);
			try{
				//Deep copy using serialization
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(tempInstruction);
				ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
				ObjectInputStream ois = new ObjectInputStream(bais);
				instruction = (Instruction) ois.readObject();
			}catch(Exception e){
				throw e;
			}
		}
		else {
			try {
				instruction = getInstructionType(actualInstruction, typedInstruction);
			}catch (Exception e){
				throw e;
			}
		}

		List<Instruction> parameters = new ArrayList<Instruction>();
		if(instruction instanceof Constant){
			((Constant) instruction).setValue(Integer.parseInt(typedInstruction));
		}
		if(instruction instanceof ListStart){
			checkBracketSpace(typedInstruction);
			parameters = groupInstructionList(instructionScanner, instrMap);
		}
		if(instruction instanceof GroupStart){
			((GroupStart) instruction).setCommand(getInstructionType(getSymbol(instructionScanner.next()), typedInstruction));
			parameters = groupUnlimitedParameterGroup(instructionScanner, instrMap);
		}
		if (instruction instanceof Variable){
			((Variable) instruction).setName(typedInstruction);
		}
		if (instruction instanceof DoTimes || instruction instanceof For){
			String bracket = instructionScanner.next();
			checkBracketSpace(bracket);
		}

		for(int i = 0; i < instruction.getNumRequiredParameters(); i++){
			if (instruction instanceof MakeUserInstruction && i == 0){

				String instructionName = instructionScanner.next();
				parameters.add(new UserInstruction(instructionName));
				continue;
			}
			Instruction nextParameter = createNextInstructionFromText(instructionScanner, instrMap);
			if (instruction instanceof MakeUserInstruction && i == 1){
				List<Variable> variableParameters = new ArrayList<Variable>();
				
				//Adds the ListStarts list of parameters to the UserInstruction
				nextParameter.parameters.forEach(p -> {
					if (! (p instanceof Variable) ){
						//TODO throw an error
					}
					variableParameters.add((Variable) p);
				});
				// give UserInstruction a list of the parameters it will need to look for
				UserInstruction newUserInstruction = ((UserInstruction) parameters.get(0));
				newUserInstruction.setVariableParameters(variableParameters);
				view.getController().getInstrMap().put(newUserInstruction.getName(), newUserInstruction);
			}
			parameters.add(nextParameter);
		}
		instruction.setParameters(parameters);

		// If a new instruction is being made, evaluate immediately instead of adding
		// to expression tree of instructions in case new instruction is called right away
		if (instruction instanceof MakeUserInstruction){
			instruction.evaluate(view, null);
			return null;
		}

		return instruction;
	}


	private Instruction getInstructionType(String instructionClassName, String typedInstruction) throws Exception{

		Instruction instruction = null;
		try {
			// instantiate a class and object for command instructions
			Class<?> c = Class.forName("instructions." + instructionClassName);
			instruction = (Instruction) c.newInstance();
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("Method name not recognized: " + typedInstruction);
		} catch (InstantiationException e) {
			throw new InstantiationException("Instantiation Exception!");
		} catch (IllegalAccessException e) {
			throw new IllegalAccessException("Illegal Access Exception!");
		} catch (SecurityException e) {
			throw new SecurityException("Security Exception!");
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Illegal Argument Exception!");
		}

		return instruction;
	}


	private List<Instruction> groupInstructionList(Scanner s, AbstractMap<String, Instruction> instrMap) throws Exception{
		List<Instruction> groupedList = new ArrayList<Instruction>();
		while (s.hasNext()){
			try {
				Instruction toAdd = createNextInstructionFromText(s, instrMap);
				if (toAdd instanceof ListStart)
					groupedList.addAll(groupInstructionList(s, instrMap));
				if (toAdd instanceof ListEnd){
					return groupedList;
				}
				groupedList.add(toAdd);
			}catch(Exception e){
				throw e;
			}
		}
		return groupedList;
	}

	private List<Instruction> groupUnlimitedParameterGroup(Scanner s, AbstractMap<String, Instruction> instrMap) throws Exception{
		List<Instruction> groupedList = new ArrayList<Instruction>();
		while (s.hasNext()){
			Instruction toAdd;
			try {
				toAdd = createNextInstructionFromText(s, instrMap);
			}catch (Exception e){
				throw e;
			}
			if (toAdd instanceof GroupEnd){
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
					Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
		}
	}

	protected void addPatterns(String syntax){
		ResourceBundle resources = ResourceBundle.getBundle(syntax);
		addPatterns(resources);
	}

	// removes the given resource file to this language's recognized types
	protected void clearPatterns () {
		mySymbols.clear();
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

	private void checkBracketSpace(String s) throws Exception{
		assert(s.charAt(0) == '[');
		if (s.length() > 1){
			throw new Exception("Need a space between bracket at " + s);
		}
	}
}
