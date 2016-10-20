import javafx.application.Application;
import javafx.stage.Stage;
import slogo_controller.SLOGOController;
import slogo_controller.TurtleController;
import slogo_model.SLOGOModel;
import slogo_model.Turtle;
import slogo_view.LanguageMenu;
import slogo_view.Playground;

public class SLOGOMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		SLOGOModel model = new Turtle();
		LanguageMenu languageMenu = new LanguageMenu(new Stage());
		Playground playground = new Playground(primaryStage, languageMenu.getLanguage());
		SLOGOController controller = new TurtleController(playground, model);
		playground.setController(controller);
		playground.init();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
