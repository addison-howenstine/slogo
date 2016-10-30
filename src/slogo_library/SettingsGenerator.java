package slogo_library;
import java.util.AbstractMap;
import instructions.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Takes in a desired file name, a variable map, and an instruction map.
 * Generates a new serializable object, SavedSettings, which contains both maps,
 * and then writes this object to a file with the desired filename.
 * 
 * @author philipfoo
 *
 */
public class SettingsGenerator {

	public void generateSerializedObj(String fileName, AbstractMap<String, Double> varMap, 
			AbstractMap<String, Instruction> instrMap) throws Exception{
		
		SavedSettings currentState = new SavedSettings(varMap, instrMap);
		try{
			FileOutputStream fout = new FileOutputStream("lib/" + fileName + ".ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(currentState);
			oos.close();
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}
}
	
