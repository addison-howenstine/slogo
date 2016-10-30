package slogo_library;
import java.util.AbstractMap;
import instructions.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SettingsGenerator {

	public void generateSerializedObj(String fileName, AbstractMap<String, Double> varMap, 
			AbstractMap<String, Instruction> instrMap){
		
		SavedSettings currentState = new SavedSettings(varMap, instrMap);
		try{
			FileOutputStream fout = new FileOutputStream("lib/" + fileName + ".ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(currentState);
			oos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
	
