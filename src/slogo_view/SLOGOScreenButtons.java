package slogo_view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import slogo_controller.TurtleController;
import slogo_model.SLOGOModel;

public class SLOGOScreenButtons {
	private static final int RUN_X = 815;
	private static final int HEIGHT = 700;
	private static final int WIDTH = 1300;
	private static final int TEXT_AREA_HEIGHT = HEIGHT/2 - 25;
	private static final int TEXT_FIELD_Y = HEIGHT - 30;
	private static final int MIN_BOUNDARY = 0;
	private static final int CONTROL_X_OFFSET = 150;
	private static final int BUTTON_1_X = 800 + CONTROL_X_OFFSET;
	private static final int BUTTON_2_X = BUTTON_1_X + CONTROL_X_OFFSET - 40;
	private static final int BUTTON_3_X = BUTTON_2_X + CONTROL_X_OFFSET - 40;
	private static final int ROW_2_Y = 25;
	private static final int ROW_3_Y = 2*ROW_2_Y;
	
	private Button myHelpButton;
	private Button myNewWindowButton;
	private Button myRunButton;
	private Button myMultilineButton;
	private Button myPenDownButton;
	private Button myPenUpButton;
	private Button myStatesButton;
	private Button mySaveButton;
	private Button myLoadButton;
	private int mySaveCounter = 0;
	private SLOGOScreen myScreen;
	
	protected SLOGOScreenButtons(SLOGOScreen screen){
		myScreen = screen;
	}
	
	protected void setUpButtons() {
		setUpHelpButton();
		setUpMultilineButton();
		setUpNewWindowButton();
		setUpPenDownButton();
		setUpPenUpButton();
		setUpStatesButton();
		setUpSaveLoadButtons();
	}
	
	protected void setUpRunButton() {
		myRunButton = myScreen.getBuilder().addButton(myScreen.getResources().getString("Run"), RUN_X, TEXT_FIELD_Y, 
				new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				myScreen.getPlayground().runCommandFromTextInput(myScreen.getTextField());
			}
		});
	}

	private void setUpHelpButton() {
		myHelpButton = myScreen.getBuilder().addButton(myScreen.getResources().getString("Help"), BUTTON_1_X, MIN_BOUNDARY, 
				new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				File htmlFile = new File("src/help.html");
				try {
					Desktop.getDesktop().browse(htmlFile.toURI());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void setUpMultilineButton() {
		myMultilineButton = myScreen.getBuilder().addButton(myScreen.getResources().getString("Multiline"), BUTTON_1_X, ROW_2_Y, 
				new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.initOwner(myScreen.getStage());
				VBox root = new VBox();
				TextArea input = new TextArea();
				input.setMinHeight(TEXT_AREA_HEIGHT);
				Button submit = new Button(myScreen.getResources().getString("Run"));
				submit.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						myScreen.getPlayground().runCommandFromTextInput(input);
					}
				});
				root.getChildren().add(input);
				root.getChildren().add(submit);
				Scene dialogScene = new Scene(root, WIDTH/2, HEIGHT/2);
				dialog.setScene(dialogScene);
				dialog.show();
			}
		});
	}
	
	private void setUpNewWindowButton(){
		myNewWindowButton = myScreen.getBuilder().addButton(myScreen.getResources().getString("NewWindow"), BUTTON_1_X, ROW_3_Y, 
				new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				Playground playground = new Playground(new Stage(), myScreen.getComboBoxesData().getLanguage());
				TurtleController controller = new TurtleController(playground);
				playground.setController(controller);
				playground.init();
			}
		});
	}
	
	private void setUpPenDownButton(){
		myPenDownButton = myScreen.getBuilder().addButton(myScreen.getResources().getString("PenDownButton"), BUTTON_2_X, MIN_BOUNDARY, 
				new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				myScreen.getPlayground().getController().run(myScreen.getResources().getString("PenDown").split("\\|")[0]);
			}
		});
	}
	
	private void setUpPenUpButton(){
		myPenUpButton = myScreen.getBuilder().addButton(myScreen.getResources().getString("PenUpButton"), BUTTON_2_X, ROW_2_Y, 
				new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				myScreen.getPlayground().getController().run(myScreen.getResources().getString("PenUp").split("\\|")[0]);
			}
		});
	}
	
	private void setUpStatesButton(){
		myStatesButton = myScreen.getBuilder().addButton(myScreen.getResources().getString("TurtleStates"), BUTTON_2_X, ROW_3_Y, 
				new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.initOwner(myScreen.getStage());
				VBox root = new VBox();
				int i = 0;
				for (SLOGOModel turtle: myScreen.getPlayground().getController().getModels()){
					StringBuilder sb = new StringBuilder("");
					sb.append(myScreen.getResources().getString("Turtle") + " ");
					sb.append(i);
					sb.append(": x = ");
					sb.append(turtle.xCor());
					sb.append(", y = ");
					sb.append(turtle.yCor());
					sb.append(", ");
					if (turtle.isPenDown() == 1)
						sb.append(myScreen.getResources().getString("PenDownButton"));
					else
						sb.append(myScreen.getResources().getString("PenUpButton"));
					sb.append(", " + myScreen.getResources().getString("Heading").split("\\|")[0] + " = ");
					sb.append(turtle.heading());
					Text text = new Text(sb.toString());
					root.getChildren().add(text);
					i++;
				}
				Scene scene = new Scene(root, WIDTH/2, HEIGHT/2);
				stage.setScene(scene);
				stage.show();
			}
		});
	}
	
	private void setUpSaveLoadButtons() {
		mySaveButton = myScreen.getBuilder().addButton(myScreen.getResources().getString("Save"), BUTTON_3_X, MIN_BOUNDARY, 
				new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				myScreen.getPlayground().getController().generateSettingsFile("SavedFile" + mySaveCounter);
				mySaveCounter++;
			}
		});
		myLoadButton = myScreen.getBuilder().addButton(myScreen.getResources().getString("Load"), BUTTON_3_X, ROW_2_Y, 
				new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SER", "*.ser"));
				File file = fileChooser.showOpenDialog(new Stage());
				if (file != null){
					myScreen.getPlayground().getController().loadSettingsFile(file.getName(), myScreen.getPlayground());
				}
			}
		});
	}
	
	protected void changeLanguage(){
		myRunButton.setText(myScreen.getResources().getString("Run"));
		myHelpButton.setText(myScreen.getResources().getString("Help"));
		myNewWindowButton.setText(myScreen.getResources().getString("NewWindow"));
		myMultilineButton.setText(myScreen.getResources().getString("Multiline"));
		myPenDownButton.setText(myScreen.getResources().getString("PenDownButton"));
		myPenUpButton.setText(myScreen.getResources().getString("PenUpButton"));
		myStatesButton.setText(myScreen.getResources().getString("TurtleStates"));
		mySaveButton.setText(myScreen.getResources().getString("Save"));
		myLoadButton.setText(myScreen.getResources().getString("Load"));
	}
}