package slogo_library;

import java.io.Serializable;
import java.util.AbstractMap;
import instructions.Instruction;

/**
 * SavedSettings serves as an intermediary serializable object which contains both
 * the instruction map and the variable map. This object is written to a file in
 * SettingsGenerator, and then re-formed using SettingsLoader.
 * 
 * @author philipfoo
 *
 */
public class SavedSettings implements Serializable{
	private AbstractMap<String, Double> myVarMap;
	private AbstractMap<String, Instruction> myInstrMap;
	
	public SavedSettings(AbstractMap<String, Double> varMap, AbstractMap<String, Instruction> instrMap){
		this.myVarMap = varMap;
		this.myInstrMap = instrMap;
	}
	
	public AbstractMap<String, Double> getVarMap(){
		return myVarMap;
	}
	
	public AbstractMap<String, Instruction> getInstrMap(){
		return myInstrMap;
	}
}
