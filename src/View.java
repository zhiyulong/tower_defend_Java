

import java.time.Duration;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class View extends Application implements Observer {

	private Controller controller;
	
	private BorderPane mainPane;
	private GridPane gameboard;
	private Stage primaryStage;
	
	public View() {
		super();
		initial();
	}
	public void initial() {
		mainPane = new BorderPane();
		gameboard = new GridPane();
		controller = TowerDefense.setRelations(this);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage;
		
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
		
		setupMenu(primaryStage);
		
		setupGameBoard();
		
		controller.setUpEnemies(this);
		
		
	}

	private void setupMenu(Stage primaryStage) {
		MenuBar menu = new MenuBar();
		//button about new game, pause, fast.
		GridPane mainMenu= new GridPane();

		Button newgame= new Button("new game");
		Button pause =new Button("Pause");
		Button start = new Button("Start");
		Button fast = new Button("Fast");
		//Button start= new Button("Start");
		
		mainMenu.add(newgame, 0, 0);
		mainMenu.add(pause, 0, 1);
		mainMenu.add(start, 0, 2);
		mainMenu.add(fast, 0,3);
		
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
			tower.setText("$"+i+" Tower #" + i);
			
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
		
		//behavior of new game, pause, fast;
		newgame.setOnMouseClicked(e -> {
			
			startNewGame();
			
		});
		pause.setOnMouseClicked(e -> {
			controller.stop();
		});
		start.setOnMouseClicked(e -> {
			controller.start();
		});
		fast.setOnMouseClicked(e -> {
			
		});
	}
	
	private void startNewGame() {
		primaryStage.close();
		
		// reload
	    Platform.runLater( () -> {
			try {
				start( new Stage() );
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		} );
		
	}
	
	
	private void setupGameBoard() {
		gameboard.setVgap(4);
		gameboard.setHgap(1);
		// set margin
		gameboard.setPadding(new Insets(0,0,0,0));
		// fill the background
		gameboard.setBackground(new Background (
				new BackgroundFill(Color.HONEYDEW,CornerRadii.EMPTY,Insets.EMPTY)));
		
		for (int r = 0; r < 6; r++) {
			
			Image grass = new Image("./images/grass.png");
			Image redland = new Image("./images/red.png");
		
			for (int c = 0; c < 9; c++) {
				
				gameboard.add(new ImageView(grass), c, r);
			}
			
			gameboard.add(new ImageView(redland), 9, r);
			
		}
		controller.addEventForGameBoard(gameboard,this);
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
			controller.arrived(o,arg);

		}
		
		// enemy killed by tower
		if (o instanceof Tower) {
			controller.killed(o,arg);
			
		}
		System.out.println(controller.getBlood()<0);
		if(controller.getBlood()<0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("You Lose!");
			alert.show();
			controller.stop();	
			return;
		}
		if(controller.get_enemiesSize()==2) {
			controller.setUpEnemies(this);
		}

	}
	

}
