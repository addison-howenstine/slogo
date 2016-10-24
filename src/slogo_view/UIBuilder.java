package slogo_view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UIBuilder implements SLOGOViewInternal {
	private static final Paint FONT_COLOR = Color.BLACK;
	private static final String FONT = "Helvetica Neue";
	
	private Group myRoot;
	
	public UIBuilder(Group root){
		myRoot = root;
	}

	@Override
	public Text addText(String message, double x, double y, int fontSize) {
		Text text = new Text(message);
		text.setFont(new Font(FONT, fontSize));
		text.relocate(x, y);
		text.setFill(FONT_COLOR);
		myRoot.getChildren().add(text);
		return text;
	}

	@Override
	public TextField addTextField(String message, double x, double y) {
		TextField textField = new TextField(message);
		textField.relocate(x, y);
		myRoot.getChildren().add(textField);
		return textField;
	}

	@Override
	public Button addButton(String message, double x, double y, EventHandler<ActionEvent> handler) {
		Button button = new Button(message);
		button.relocate(x, y);
		button.setOnAction(handler);
		myRoot.getChildren().add(button);
		return button;
	}

	@Override
	public ComboBox<String> addComboBox(double x, double y, ObservableList<String> items, String defaultValue, 
			ChangeListener<String> listener) {
		ComboBox<String> comboBox = new ComboBox<String>(items);
		comboBox.relocate(x, y);
		comboBox.setValue(defaultValue);
		comboBox.valueProperty().addListener(listener);
		myRoot.getChildren().add(comboBox);
		return comboBox;
	}
	
	public Rectangle addRectangle(double x, double y, double width, double height, Paint paint){
		Rectangle rectangle = new Rectangle(x, y, width, height);
		rectangle.setFill(paint);
		myRoot.getChildren().add(rectangle);
		return rectangle;
	}
	
	public Line addLine(double startX, double startY, double endX, double endY, Paint paint){
		Line line = new Line(startX, startY, endX, endY);
		line.setStroke(paint);
		myRoot.getChildren().add(line);
		return line;
	}
	
	public TextArea addTextArea(double startX, double startY, double width, double height) {
		TextArea textArea = new TextArea();
		textArea.setLayoutX(startX);
		textArea.setLayoutY(startY);
		textArea.setMinWidth(width);
		textArea.setMaxWidth(width);
		textArea.setMinHeight(height);
		textArea.setMaxHeight(height);
		textArea.textProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				textArea.setScrollTop(Double.MAX_VALUE);			
			}
		});
		myRoot.getChildren().add(textArea);
		return textArea;
	}
	
	public ScrollPane addScrollPane(double startX, double startY, double width, double height, Node content) {
		ScrollPane pane = new ScrollPane();
		VBox box = new VBox();
		pane.setContent(box);
	}

}
