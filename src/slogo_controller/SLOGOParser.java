package slogo_controller;

import instructions.Constant;
import instructions.*;
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
	private final String ERROR = "NO MATCH";


	public SLOGOParser () {
		mySymbols = new ArrayList<>();
	}

	// TODO make useless back end Main to test this method

	public List<Instruction> parse(String command){
		List<Instruction> instructionList = new ArrayList<Instruction>();

		String[] commandLines = command.split("\\n");		
		for(String line : commandLines){
			if( ! getSymbol(line).equals("Comment")){
				Scanner instructionScanner = new Scanner(line).useDelimiter("\\s+");
				while(instructionScanner.hasNext()){
					instructionList.add(createNextInstructionFromText(instructionScanner));
				}
				instructionScanner.close();
			}
		}
		return instructionList;
	}
	
	public Instruction createNextInstructionFromText(Scanner instructionScanner){
		String typedInstruction = instructionScanner.next();
		String actualInstruction = getSymbol(typedInstruction);
		if(actualInstruction.equals(ERROR))
			;//throw CommandNotFound error
		System.out.println(typedInstruction);
		System.out.println(actualInstruction);
		Instruction instruction = null;
		try {
			// instantiate a class and object for command instructions
			Class<?> c = Class.forName("instructions." + actualInstruction);
			Object o = c.newInstance();
			// We can do this so long as we ensure getSymbol(String) will return the name of an Instruction subclass for *any* String
			// TODO ^is that true tho?
			instruction = (Instruction) o;
			
			System.out.println(o.getClass().toString());


		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
				
		if(instruction instanceof Constant){
			System.out.println("Parsed Constant : " + Integer.parseInt(typedInstruction));
			((Constant) instruction).setValue(Integer.parseInt(typedInstruction));
		}
		List<Instruction> parameters = new ArrayList<Instruction>();
		for(int i = 0; i < instruction.getNumRequiredParameters(); i++){
			System.out.println("Adding new param");
			parameters.add(createNextInstructionFromText(instructionScanner));
		}
		instruction.setParameters(parameters);
		return instruction;
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
