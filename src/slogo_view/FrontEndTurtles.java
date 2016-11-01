package slogo_view;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import slogo_model.SLOGOModel;

public class FrontEndTurtles {
	private static final int WIDTH = 1300;
	private static final int HEIGHT = 700;
	private static final int TURTLE_HEIGHT = 25;
	private static final int TURTLE_X_OFFSET = 405;
	private static final int TURTLE_Y_OFFSET = 360;
	private static final int TURTLE_AREA_X = 5;
	private static final int TURTLE_AREA_Y = 55;
	private static final int TURTLE_AREA_WIDTH = 800;
	private static final int TURTLE_AREA_HEIGHT = 670 - 5 - TURTLE_AREA_Y;
	
	private ArrayList<Animation> myAnimations = new ArrayList<Animation>();
	private Playground myPlayground;
	private int counter = 0;
	private Canvas myCanvas;
	private List<ImageView> visualTurtles;

	protected FrontEndTurtles(Playground playground) {
		myPlayground = playground;
		visualTurtles = new ArrayList<ImageView>();
		myCanvas = new Canvas(WIDTH, HEIGHT);
		myPlayground.getRoot().getChildren().add(myCanvas);
	}
	
	protected void setUpTurtle() {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(myPlayground.getScreen().getComboBoxesData().getImagesMap()
				.get(myPlayground.getScreen().getComboBoxesData().getImageSelector().getValue())));
		ImageView visT = new ImageView(image);
		visT.setFitHeight(TURTLE_HEIGHT);
		visT.setPreserveRatio(true);
		visT.setTranslateX(TURTLE_X_OFFSET - visT.getBoundsInLocal().getWidth()/2); 
		visT.setTranslateY(TURTLE_Y_OFFSET - visT.getBoundsInLocal().getHeight()/2);
		myPlayground.getX().add(0.0);
		myPlayground.getY().add(0.0);
		myPlayground.getRoot().getChildren().add(visT);
		visualTurtles.add(visT);
		myPlayground.getHeadings().add(0.0);
	}

	protected void setUpRotateMethod(int i, ImageView myTurtle, SLOGOModel myModel) {
		RotateTransition rt = new RotateTransition(Duration.seconds(1));
		rt.setNode(myTurtle);
		rt.setToAngle(myModel.heading());
		myAnimations.add(rt);
		myPlayground.getHeadings().set(i, myModel.heading());
	}

	protected void setUpPathAnimation(GraphicsContext gc, int i, ImageView myTurtle, SLOGOModel myModel) {
		Path path = new Path();
		path.getElements().add(new MoveTo(myPlayground.getX().get(i) + TURTLE_X_OFFSET, TURTLE_Y_OFFSET - myPlayground.getY().get(i)));
		path.getElements().add(new LineTo(myModel.xCor() + TURTLE_X_OFFSET, 
				TURTLE_Y_OFFSET - myModel.yCor()));
		PathTransition pt = new PathTransition(Duration.millis(1000), path, myTurtle);
		int j = i;
		if (myModel.isPenDown() == 1){
			pt.currentTimeProperty().addListener(new ChangeListener<Duration>() {
				double oldX = myTurtle.getTranslateX() + myTurtle.getBoundsInLocal().getWidth()/2;
				double oldY = myTurtle.getTranslateY() + myTurtle.getBoundsInLocal().getHeight()/2;
				double newX = myTurtle.getTranslateX() + myTurtle.getBoundsInLocal().getWidth()/2;
				double newY = myTurtle.getTranslateY() + myTurtle.getBoundsInLocal().getHeight()/2;
				double count = -1;
				public void changed(ObservableValue ov, Duration t, Duration t1){
					count++;
					if (t.equals(Duration.ZERO))
						return;
					if (count > 2){
						gc.setStroke(myPlayground.getScreen().getComboBoxesData().getPenOptions().getColor());
						gc.setLineWidth(myPlayground.getScreen().getComboBoxesData().getPenOptions().getWidth());
						gc.strokeLine(oldX, oldY, newX, newY);
					}
					oldX = newX;
					oldY = newY;
					newX = myTurtle.getTranslateX() + myTurtle.getBoundsInLocal().getWidth()/2;
					newY = myTurtle.getTranslateY() + myTurtle.getBoundsInLocal().getHeight()/2;
				}
			});
		}
		myPlayground.getX().set(j, myModel.xCor());
		myPlayground.getY().set(j, myModel.yCor());
		myAnimations.add(pt);
	}

	protected void runAnimation(Animation animation, int index){
		if (animation == null){
			myCanvas.getGraphicsContext2D().clearRect(TURTLE_AREA_X, TURTLE_AREA_Y, TURTLE_AREA_WIDTH, TURTLE_AREA_HEIGHT);
			animationsChecker(index);
		}
		else {
			animation.setRate(myPlayground.getScreen().getSpeedSlider().getValue());
			animation.play();
			animation.setOnFinished(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event){
					animationsChecker(index);
				}
			});
		}
	}

	private void animationsChecker(int index) {
		if (index < myAnimations.size()){
			runAnimation(myAnimations.get(index), index + 1);
			counter++;
		}
		else {
			myAnimations.clear();
			counter = 0;
		}
	}
	
	protected void changeTurtleImages(String image){
		visualTurtles.forEach(visT ->  visT.setImage(new Image(getClass().getClassLoader()
				.getResourceAsStream(myPlayground.getScreen().getComboBoxesData().getImagesMap().get(image)))));
	}
	
	protected ArrayList<Animation> getAnimations(){
		return myAnimations;
	}
	
	protected Canvas getCanvas(){
		return myCanvas;
	}
	
	protected int getCounter(){
		return counter;
	}
	
	protected List<ImageView> getVisualTurtles(){
		return visualTurtles;
	}
}