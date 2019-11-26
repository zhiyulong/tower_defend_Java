
import javafx.application.Application;
import javafx.scene.Scene;

public class TowerDefense {

	public static void main(String[] args) {
		
		Application.launch(View.class, args);

	}
	
	
	public static Controller setRelations(View view) {
		
		Model model = new Model();
		Controller controller = new Controller(model);
		
		model.addObserver(view);
		
		return controller;
	}

}
