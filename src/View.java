

import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.stage.Stage;

public class View extends Application implements Observer{

	private Controller controller;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// set MVC relation first
		controller = TowerDefense.setRelations(this);
		
	}

	@Override
	public void update(Observable o, Object arg) {
		
		
	}

}
