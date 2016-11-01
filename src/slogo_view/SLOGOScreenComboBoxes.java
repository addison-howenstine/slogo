package slogo_view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class SLOGOScreenComboBoxes {
	private static final int MIN_BOUNDARY = 0;
	private static final int ROW_2_Y = 25;
	private static final int BACKGROUND_X = 200;
	private static final int CONTROL_X_OFFSET = 150;
	private static final int PEN_COLOR_X = BACKGROUND_X + CONTROL_X_OFFSET;
	private static final int PEN_WIDTH_X = PEN_COLOR_X + CONTROL_X_OFFSET;
	private static final int IMAGE_X = PEN_WIDTH_X + CONTROL_X_OFFSET;
	private static final int LANGUAGES_X = IMAGE_X + CONTROL_X_OFFSET;
	private static final int FONT_SIZE = 20;
	private static final String DEFAULT_IMAGE = "Turtle";
	private static final String DEFAULT_BACKGROUND = "White";
	private static final String DEFAULT_PEN = "Black";
	private static final String[] LANGUAGES = {"Deutsche", "English", "Espanol", "Francais", "Italiano", "Portugues", 
			"Russkiy", "Zhongwen"};
	
	public TreeMap<String, Paint> myColorsMap;
	public ObservableList<String> myColors;
	public TreeMap<String, String> myImagesMap;
	public ObservableList<String> myImages;
	public String myLanguage;
	public ObservableList<String> myLanguages;
	public ComboBox<String> myImageSelector;
	public ComboBox<String> myPenColorSelector;
	public ComboBox<String> myBackgroundSelector;
	public ComboBox<Integer> myPenWidthSelector;
	public Text myBackgroundText;
	public Text myPenColorText;
	public Text myPenWidthText;
	public Text myImageText;
	public Text myLanguageText;
	public PenOptions myPenOptions;
	private SLOGOScreen myScreen;
	
	protected SLOGOScreenComboBoxes(SLOGOScreen screen){
		myLanguages = FXCollections.observableList(new ArrayList<String>(Arrays.asList(LANGUAGES)));
		myPenOptions = new PenOptions(Color.BLACK, 2);
		myScreen = screen;
	}

	protected void setUpComboBoxes() {
		setUpImagesMap();
		setUpColorsMap();
		setUpBackgroundSelector();
		setUpPenColorSelector();
		setUpImageSelector();
		setUpLanguageSelector();
		setUpPenWidthSelector();
	}

	private void setUpPenWidthSelector() {
		myPenWidthText = myScreen.getBuilder().addText(myScreen.getResources().getString("PenWidth"), PEN_WIDTH_X, MIN_BOUNDARY, FONT_SIZE);
		ObservableList<Integer> widths = FXCollections.observableList(new ArrayList<Integer>());
		widths.addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		myPenWidthSelector = myScreen.getBuilder().addComboBox(PEN_WIDTH_X, ROW_2_Y, widths, 2, 
				new ChangeListener<Integer>() {
			public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1){
				myPenOptions.setWidth(t1);
			}
		});
	}

	private void setUpLanguageSelector() {
		myLanguageText = myScreen.getBuilder().addText(myScreen.getResources().getString("Languages"), LANGUAGES_X, MIN_BOUNDARY, FONT_SIZE);
		myScreen.getBuilder().addComboBox(LANGUAGES_X, ROW_2_Y, myLanguages, myLanguage, new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String t, String t1) {
				myScreen.changeLanguage(t1);
			}
		});
	}

	private void setUpImageSelector() {
		myImageText = myScreen.getBuilder().addText(myScreen.getResources().getString("Image"), IMAGE_X, MIN_BOUNDARY, FONT_SIZE);
		myImageSelector = myScreen.getBuilder().addComboBox(IMAGE_X, ROW_2_Y, myImages, 
				"1. " + myScreen.getResources().getString(DEFAULT_IMAGE), new ChangeListener<String>() {
			public void changed(ObservableValue ov, String t, String t1) {
				myScreen.getPlayground().changeTurtleImages(t1);
			}
		});
	}

	private void setUpPenColorSelector() {
		myPenColorText = myScreen.getBuilder().addText(myScreen.getResources().getString("PenColor"), PEN_COLOR_X, MIN_BOUNDARY, FONT_SIZE);
		myPenColorSelector = myScreen.getBuilder().addComboBox(PEN_COLOR_X, ROW_2_Y, myColors, 
				"1. " + myScreen.getResources().getString(DEFAULT_PEN), new ChangeListener<String>() {
			public void changed(ObservableValue ov, String t, String t1){
				myScreen.getPlayground().setPenColor(t1);
			}
		});
	}

	private void setUpBackgroundSelector() {
		myBackgroundText = myScreen.getBuilder().addText(myScreen.getResources().getString("Background"), BACKGROUND_X, MIN_BOUNDARY, 
				FONT_SIZE);
		myBackgroundSelector = myScreen.getBuilder().addComboBox(BACKGROUND_X, ROW_2_Y, myColors, 
				"8. " + myScreen.getResources().getString(DEFAULT_BACKGROUND), new ChangeListener<String>() {
			public void changed(ObservableValue ov, String t, String t1){
				myScreen.getTurtleArea().setFill(myColorsMap.get(t1));
			}
		});
	}
	
	private void setUpImagesMap(){
		myImagesMap = new TreeMap<String, String>();
		myImagesMap.put("1. " + myScreen.getResources().getString(DEFAULT_IMAGE), "turtle.png");
		myImagesMap.put("2. " + myScreen.getResources().getString("Rocket"), "rocket.png");
		myImagesMap.put("3. " + myScreen.getResources().getString("Frog"), "frog.png");
		myImagesMap.put("4. " + myScreen.getResources().getString("Pencil"), "pencil.png");
		myImagesMap.put("5. " + myScreen.getResources().getString("Duke"), "duke.png");
		myImages = FXCollections.observableList(new ArrayList<String>());
		myImages.addAll(myImagesMap.keySet());
	}

	private void setUpColorsMap(){
		myColorsMap = new TreeMap<String, Paint>();
		myColorsMap.put("1. " + myScreen.getResources().getString(DEFAULT_PEN), Color.BLACK);
		myColorsMap.put("2. " + myScreen.getResources().getString("Blue"), Color.BLUE);
		myColorsMap.put("3. " + myScreen.getResources().getString("Brown"), Color.BROWN);
		myColorsMap.put("4. " + myScreen.getResources().getString("Green"), Color.GREEN);
		myColorsMap.put("5. " + myScreen.getResources().getString("Orange"), Color.ORANGE);
		myColorsMap.put("6. " + myScreen.getResources().getString("Purple"), Color.PURPLE);
		myColorsMap.put("7. " + myScreen.getResources().getString("Red"), Color.RED);
		myColorsMap.put("8. " + myScreen.getResources().getString(DEFAULT_BACKGROUND), Color.WHITE);
		myColorsMap.put("9. " + myScreen.getResources().getString("Yellow"), Color.YELLOW);
		myColors = FXCollections.observableList(new ArrayList<String>());
		myColors.addAll(myColorsMap.keySet());
	}
	
	protected void changeLanguages(){
		myBackgroundText.setText(myScreen.getResources().getString("Background"));
		myPenColorText.setText(myScreen.getResources().getString("PenColor"));
		myPenWidthText.setText(myScreen.getResources().getString("PenWidth"));
		myImageText.setText(myScreen.getResources().getString("Image"));
		myLanguageText.setText(myScreen.getResources().getString("Languages"));
		int backgroundIndex = myColors.indexOf(myBackgroundSelector.getValue());
		int penIndex = myColors.indexOf(myPenColorSelector.getValue());
		int imageIndex = myImages.indexOf(myImageSelector.getValue());
		setUpColorsMap();
		setUpImagesMap();
		changeComboBoxLanguage(myBackgroundSelector, myColors, backgroundIndex);
		changeComboBoxLanguage(myPenColorSelector, myColors, penIndex);
		changeComboBoxLanguage(myImageSelector, myImages, imageIndex);
		myImageSelector.getItems().remove("5. Duke");
	}
	
	private void changeComboBoxLanguage(ComboBox<String> selector, ObservableList<String> list, int index){
		selector.getItems().addAll(list);
		selector.setValue(list.get(index));
		selector.getItems().retainAll(list);
	}
	
	protected ComboBox<String> getImageSelector(){
		return myImageSelector;
	}
	
	protected TreeMap<String, Paint> getColorsMap(){
		return myColorsMap;
	}
	
	protected TreeMap<String, String> getImagesMap(){
		return myImagesMap;
	}
	
	protected ObservableList<String> getColors(){
		return myColors;
	}
	
	protected ObservableList<String> getImages(){
		return myImages;
	}
	
	protected PenOptions getPenOptions(){
		return myPenOptions;
	}
	
	protected String getLanguage(){
		return myLanguage;
	}
	
	protected void setLanguage(String language){
		myLanguage = language;
	}
}