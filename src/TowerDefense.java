import javafx.application.Application;

public class TowerDefense {

	public static void main(String[] args) {

//		//Application.launch(View.class, args);
		Application.launch(Menu.class, args);

	}

	public static Controller setRelations(View view) {

		Model model = new Model();
		Controller controller = new Controller(model);

		return controller;
	}

}
