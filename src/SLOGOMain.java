import javafx.application.Application;
import javafx.stage.Stage;
import slogo_view.Playground;

public class SLOGOMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		new Playground(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
