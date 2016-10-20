package slogo_model;
import java.util.*;
import instructions.*;

public class SLOGOVariableMap {
	private AbstractMap<String, Double> myVarMap = new HashMap<String, Double>();
	
	public SLOGOVariableMap(){
	}
	
	public void addMapping(String s, Double i){
		myVarMap.put(s, i);
	}
	
	public double getMapping(String s){
		return myVarMap.get(s);
	}
}
