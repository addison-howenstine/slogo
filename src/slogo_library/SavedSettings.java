package slogo_library;

import java.io.Serializable;
import java.util.AbstractMap;
import instructions.Instruction;

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
