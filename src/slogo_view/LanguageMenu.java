package slogo_view;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import slogo_controller.SLOGOController;
import slogo_controller.TurtleController;
import slogo_model.SLOGOModel;
import slogo_model.Turtle;

public class LanguageMenu {
	private static final String START = "Start";
	private static final int BUTTON_LOCATION = 120;
	private static final int COMBO_BOX_Y = 85;
	private static final int COMBO_BOX_X = 90;
	private static final int FONT_SIZE = 20;
	private static final String CHOOSE_LANGUAGE = "Choose Language";
	private static final int TEXT_Y = 50;
	private static final int TEXT_X = 75;
	private static final int HEIGHT = 200;
	private static final int WIDTH = 300;
	
	static final String DEFAULT_LANGUAGE = "English";
	static final String[] LANGUAGES = {"Deutsche", "English", "Espanol", "Francais", "Italiano", "Portugues", 
			"Russkiy", "Zhongwen"};
	
	private ObservableList<String> myLanguages;
	private String myLanguage;
	private Stage myStage;
	
	public LanguageMenu(Stage s){
		myStage = s;
		myLanguages = FXCollections.observableList(new ArrayList<String>());
		myLanguages.addAll(LANGUAGES);
		myLanguage = DEFAULT_LANGUAGE;
		Scene scene = init();
		s.setScene(scene);
		s.showAndWait();
	}
	
	private Scene init(){
		Group root = new Group();
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		UIBuilder builder = new UIBuilder(root);
		builder.addText(CHOOSE_LANGUAGE, TEXT_X, TEXT_Y, FONT_SIZE);
		builder.addComboBox(COMBO_BOX_X, COMBO_BOX_Y, myLanguages, DEFAULT_LANGUAGE, new ChangeListener<String>() {
			public void changed(ObservableValue ov, String t, String t1){
				myLanguage = t1;
			}
		});
		builder.addButton(START, BUTTON_LOCATION, BUTTON_LOCATION, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				myStage.close();
			}
		});
		return scene;
	}
	
	public String getLanguage(){
		return myLanguage;
	}
}
