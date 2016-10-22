import javafx.application.Application;
import javafx.stage.Stage;
import slogo_controller.SLOGOController;
import slogo_controller.TurtleController;
import slogo_model.ObservableTurtle;
import slogo_model.SLOGOModel;
import slogo_model.Turtle;
import slogo_view.LanguageMenu;
import slogo_view.Playground;
import slogo_view.SLOGOViewExternal;

public class SLOGOMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// initialize model, view, controller
		LanguageMenu languageMenu = new LanguageMenu(new Stage());
		SLOGOViewExternal view = new Playground(primaryStage, languageMenu.getLanguage());
		SLOGOModel model = new ObservableTurtle(view.getMaxX(), view.getMaxY());
		SLOGOController controller = new TurtleController(view, model);
		
		// give model and view necessary pointers
		((ObservableTurtle) model).addListener(view);
		view.setController(controller);
		view.addModel(model);
		
		((Playground) view).init();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
