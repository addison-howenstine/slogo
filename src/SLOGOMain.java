import javafx.application.Application;
import javafx.stage.Stage;
import slogo_controller.SLOGOController;
import slogo_controller.TurtleController;
import slogo_view.Playground;
import slogo_view.SLOGOView;

public class SLOGOMain extends Application {
	private static final String DEFAULT_LANGUAGE = "English";
	
	SLOGOView view;
	SLOGOController controller;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// initialize model, view, controller
		view = new Playground(primaryStage, DEFAULT_LANGUAGE);		
		controller = new TurtleController(view);
		view.setController(controller);
		((Playground) view).init();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
