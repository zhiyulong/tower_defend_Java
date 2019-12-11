import javafx.application.Application;

public class TowerDefense {

	public static void main(String[] args) {

		Application.launch(Menu.class, args);

	}

	public static Controller setRelations(View view, String mode) {

		Model model = new Model(mode);
		Controller controller = new Controller(model);

		return controller;
	}

}
