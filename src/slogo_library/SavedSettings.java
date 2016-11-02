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
	private int myBackground;
	private int myPenColor;
	private int myPenSize;
	private int myImage;
	private String myLanguage;
	
	public SavedSettings(AbstractMap<String, Double> varMap, AbstractMap<String, Instruction> instrMap, 
			int background, int penColor, int penSize, int image, String language){
		this.myVarMap = varMap;
		this.myInstrMap = instrMap;
		System.out.println(background);
		this.myBackground = background;
		System.out.println(penColor);
		this.myPenColor = penColor;
		System.out.println(penSize);
		this.myPenSize = penSize;
		System.out.println(image);
		this.myImage = image;
		System.out.println(language);
		this.myLanguage = language;
	}
	
	public AbstractMap<String, Double> getVarMap(){
		return myVarMap;
	}
	
	public AbstractMap<String, Instruction> getInstrMap(){
		return myInstrMap;
	}
	
	public int getBackground(){
		return myBackground;
	}
	
	public int getPenColor(){
		return myPenColor;
	}
	
	public int getPenSize(){
		return myPenSize;
	}
	
	public int getImage(){
		return myImage;
	}
	
	public String getLanguage(){
		return myLanguage;
	}
}
