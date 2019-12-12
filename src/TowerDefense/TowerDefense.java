package TowerDefense;
import GUI.MenuGUI;
import GUI.View;
import javafx.application.Application;

/**
 * 
 * This is the main method class of the application.
 *
 */
public class TowerDefense {
	
	/**
	 * The main method directly launch the begin menu 
	 * @param args from the command line
	 */
	public static void main(String[] args) {

		Application.launch(MenuGUI.class, args);

	}

	/**
	 * This method set the MVC relationships, 
	 * it will be called by View, set model for controller first,
	 * then return a controller for view.
	 * 
	 * @param view is the View of MVC
	 * @param mode is the Mode of MVC
	 * @return controller for View
	 */
	public static Controller setRelations(View view, String mode) {

		Model model = new Model(mode);
		Controller controller = new Controller(model);

		return controller;
	}

}
