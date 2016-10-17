import javafx.application.Application;
import javafx.stage.Stage;
import slogo_controller.SLOGOController;
import slogo_controller.TurtleController;
import slogo_model.SLOGOModel;
import slogo_model.Turtle;
import slogo_view.Playground;
import slogo_view.SLOGOViewExternal;
import slogo_view.LanguageMenu;

public class SLOGOMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
//		SLOGOViewExternal view = new Playground(primaryStage);
//		SLOGOModel model = new Turtle();
//		SLOGOController controller = new TurtleController(view, model);
		// TODO give view a pointer to controller
		
		new LanguageMenu(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
