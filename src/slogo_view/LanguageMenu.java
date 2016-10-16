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
	private static final String DEFAULT_LANGUAGE = "English";
	private static final String[] LANGUAGES = {"Chinese", "English", "French", "German", "Italian", "Portuguese", 
			"Russian", "Spanish"};
	
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
		s.show();
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
				new Playground(new Stage(), myLanguage);
			}
		});
		return scene;
	}
}
