package slogo_library;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import instructions.Instruction;
import java.util.AbstractMap;
import slogo_view.SLOGOView;

/**
 * SettingsLoader class is responsible for loading an old SLOGO library from disk, given 
 * the name of the file. It stores the old settings as an attribute, and provides two public 
 * functions, which allow old instructions and variables to be added to an already existing map. 
 * 
 * @author philipfoo
 *
 */
public class SettingsLoader {
	SavedSettings settingsToLoad;
	
	/**
	 * Loads a given file into memory using serializable objects
	 * @param fileName: the file name of the desired file to be loaded
	 */
	public SettingsLoader(String fileName){
		try {
			FileInputStream fin = new FileInputStream("lib/" + fileName + ".ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			this.settingsToLoad = (SavedSettings) ois.readObject();
			ois.close();
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadInstructionsToMap(AbstractMap<String, Instruction> toLoad, SLOGOView view){
		AbstractMap<String, Instruction> map = settingsToLoad.getInstrMap();
		map.forEach((k, v) -> {
			toLoad.put(k, v);
			view.addUserCommand(k);
		});
	}
	
	public void loadVariablesToMap(AbstractMap<String, Double> toLoad, SLOGOView view){
		AbstractMap<String, Double> map = settingsToLoad.getVarMap();
		map.forEach((k, v) -> {
			toLoad.put(k, v);
			view.addUserVariable(k, v);
		});
	}
}
