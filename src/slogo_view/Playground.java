package slogo_view;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Playground implements SLOGOViewExternal {
	private static final int HEIGHT = 700;
	private static final int WIDTH = 1200;
	private static final String[] LANGUAGES = {"Chinese", "English", "French", "German", "Italian", "Portuguese", 
	                                           "Russian", "Spanish"};
	private static final String DEFAULT_LANGUAGE = "English";
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private static final String TITLE = "SLOGO";
	
	
	private ResourceBundle myResources;
	private Group myRoot;
	private UIBuilder myBuilder;
	private ObservableList<String> myLanguages;
	
	public Playground(Stage s){
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_LANGUAGE);
		myLanguages = FXCollections.observableList(new ArrayList<String>());
		myLanguages.addAll(LANGUAGES);
		Scene scene = init();
		s.setScene(scene);
		s.setTitle(TITLE);
		s.show();
	}
	
	private Scene init(){
		myRoot = new Group();
		Scene scene = new Scene(myRoot, WIDTH, HEIGHT);
		myBuilder = new UIBuilder(myRoot);
		myBuilder.addText(TITLE, 0, 0, 50);
		myBuilder.addText("Language:", 200, 0, 20);
		myBuilder.addComboBox(200, 25, myLanguages, DEFAULT_LANGUAGE, new ChangeListener<String>() {
			public void changed(ObservableValue ov, String t, String t1){
				// TODO set up listener
			}
		});
		return scene;
	}

	@Override
	public void showError(String errorMessage) {
		Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(myResources.getString("ErrorTitle"));
        alert.setContentText(errorMessage);
        alert.showAndWait();
	}

	@Override
	public ResourceBundle getResourceBundle() {
		return myResources;
	}

	@Override
	public void clearTrails() {
		// TODO Auto-generated method stub
		
	}

}
