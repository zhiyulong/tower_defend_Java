

import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
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

public class View extends Application implements Observer{

	private Controller controller;
	
	private BorderPane mainPane;
	private GridPane gameboard;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
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
		
		setupMenu();
		
		setupGameBoard();
		
		controller.setUpEnemies(gameboard);
	}
	
	private void setupMenu() {
		MenuBar menu = new MenuBar();
		
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
		controller.addEventForGameBoard(gameboard);
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
		
		
	}

}
