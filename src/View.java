import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class View extends Application implements Observer {

	private Controller controller;

	private BorderPane mainPane;
	private GridPane gameboard;
	private Stage gameStage;
	
	private Stage menu;

	public View(Stage menu) {
		super();
		initial();
		
		this.menu = menu;
	}

	public void initial() {
		mainPane = new BorderPane();
		gameboard = new GridPane();
		controller = TowerDefense.setRelations(this);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.gameStage = primaryStage;

		mainPane = new BorderPane();
		gameboard = new GridPane();
		Scene scene = new Scene(mainPane);

		// set MVC relation first
		controller = TowerDefense.setRelations(this);

		// set up the game
		setupGame();

		primaryStage.setTitle("Tower Defense");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void setupGame() {

		displayHome();

		setupMenu(gameStage);

		setupGameBoard();

		controller.setUpEnemies(this);

	}

	private void setupMenu(Stage primaryStage) {
		MenuBar menu = new MenuBar();
		// button about new game, pause, fast.
		GridPane mainMenu = new GridPane();
		mainMenu.setVgap(20);

		Button backToMenu = new Button("Back to Menu");
		Button newgame = new Button("Restart");
		
		VBox stopBegin = new VBox();
		Button pause = new Button("Pause");
		Button start = new Button("Start");
		stopBegin.getChildren().addAll(pause, start);
		
		VBox speed = new VBox();
		Button fast = new Button("Fast");
		Button normal= new Button("Normal");
		speed.getChildren().addAll(fast, normal);
		
		mainMenu.add(backToMenu, 0, 0);
		mainMenu.add(newgame, 0, 1);
		mainMenu.add(stopBegin, 0, 2);
		mainMenu.add(speed, 0, 3);

		// buy towers
		Menu buyTowers = new Menu();
		buyTowers.setText("Buy Towers");
		// buying status
		Label buying_label = new Label("Not buying");
		controller.setBuyingStatus(buying_label);
		Menu buyingStatus = new Menu("", buying_label);

		// currency
		Label currency_label = new Label();
		controller.setCurrencyLabel(currency_label);
		Menu currency = new Menu("", currency_label);

		for (int i = 1; i < 7; i++) {
			MenuItem tower = new MenuItem();
			tower.setText("$" + i + " Tower #" + i);

			// set action for buying a tower
			tower.setOnAction(e -> {
				controller.buyTower(Character.getNumericValue(tower.getText().charAt(10)));

			});

			buyTowers.getItems().add(tower);
		}

		// blood status
		Label blood_label = new Label();
		controller.setBloodStatus(blood_label);
		Menu blood = new Menu("", blood_label);

		menu.getMenus().add(buyTowers);
		menu.getMenus().add(buyingStatus);
		menu.getMenus().add(currency);
		menu.getMenus().add(blood);

		mainPane.setTop(menu);
		mainPane.setRight(mainMenu);

		// behavior of new game, pause, fast;
		backToMenu.setOnMouseClicked(e -> {
			backToMenu();

		});
		newgame.setOnMouseClicked(e -> {
			startNewGame();
		});
		pause.setOnMouseClicked(e -> {
			controller.exec(pause.getText());
		});
		start.setOnMouseClicked(e -> {
			controller.exec(start.getText());
		});
		fast.setOnMouseClicked(e -> {

			controller.exec(fast.getText());
		});
		normal.setOnMouseClicked(e -> {
			controller.exec(normal.getText());

		});
	}

	private void backToMenu() {
		gameStage.close();
		
		Platform.runLater(new Runnable() {
			public void run() {
				menu.show();
			}
		});
		
	}

	private void startNewGame() {
		gameStage.close();

		// reload
		Platform.runLater(() -> {
			try {
				start(new Stage());
			} catch (Exception e) {

				e.printStackTrace();
			}
		});

	}

	private void setupGameBoard() {
		gameboard.setVgap(4);
		gameboard.setHgap(1);
		// set margin
		gameboard.setPadding(new Insets(0, 0, 0, 0));
		// fill the background
		gameboard.setBackground(new Background(new BackgroundFill(Color.HONEYDEW, CornerRadii.EMPTY, Insets.EMPTY)));

		for (int r = 0; r < 6; r++) {

			Image grass = new Image("./images/grass.png");
			Image redland = new Image("./images/red.png");

			for (int c = 0; c < 9; c++) {

				gameboard.add(new ImageView(grass), c, r);
			}

			gameboard.add(new ImageView(redland), 9, r);

		}
		controller.addEventForGameBoard(gameboard, this);
		mainPane.setCenter(gameboard);
	}

	private void displayHome() {
		ImageView tower = new ImageView(new Image("./images/TOWER.png"));
		tower.setFitHeight(410);
		tower.setFitWidth(100);
		mainPane.setLeft(tower);
	}


	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		// enemy arrived home

		if (o instanceof Enemie) {
			controller.arrived(o, arg);

		}

		// enemy killed by tower
		if (o instanceof Tower) {
			controller.killed(o, arg);

		}
		System.out.println(controller.getBlood() < 0);
		if (controller.getBlood() < 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("You Lose!");
			alert.show();

			controller.removeAll();	
			return;
		}

		if (controller.get_enemiesSize() <= 2) {

			controller.setUpEnemies(this);
		}

	}

}
