import javafx.application.Application;
import javafx.stage.Stage;
import slogo_controller.SLOGOController;
import slogo_controller.TurtleController;
import slogo_model.SLOGOModel;
import slogo_model.Turtle;
import slogo_view.LanguageMenu;
import slogo_view.Playground;
import slogo_view.SLOGOViewExternal;

public class SLOGOMain extends Application {
	
	LanguageMenu languageMenu;
	SLOGOViewExternal view;
	SLOGOModel model;
	SLOGOController controller;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// initialize model, view, controller
		languageMenu = new LanguageMenu(new Stage());
		view = new Playground(primaryStage, languageMenu.getLanguage());
		model = new Turtle(view.getMaxX(), view.getMaxY());
		
		controller = new TurtleController(view, model);
		
		// give model and view necessary pointers
		((Turtle) model).addObserver((Playground) view);
		view.setController(controller);
		view.addModel(model);
		
		((Playground) view).init();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
