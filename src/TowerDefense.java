import javafx.application.Application;

public class TowerDefense {

	public static void main(String[] args) {
<<<<<<< HEAD

//		////Application.launch(View.class, args);
		Application.launch(Menu.class, args);
=======
		
		Application.launch(View.class, args);
		//Application.launch(Menu.class, args);
>>>>>>> branch 'master' of https://github.com/csc335-fall-2019/csc335-towerdef-markhardy-zhiyulong-donshawhu-jiaxukang.git

	}

	public static Controller setRelations(View view) {

		Model model = new Model();
		Controller controller = new Controller(model);

		return controller;
	}

}
