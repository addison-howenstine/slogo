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
	SLOGOController controller;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// initialize model, view, controller
		languageMenu = new LanguageMenu(new Stage());
		view = new Playground(primaryStage, languageMenu.getLanguage());		
		controller = new TurtleController(view);
		
		// give model and view necessary pointers
		view.setController(controller);
		controller.getModels().forEach( m -> view.addModel(m) );
		
		((Playground) view).init();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
