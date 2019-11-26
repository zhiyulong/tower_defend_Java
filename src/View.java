

import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
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
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class View extends Application implements Observer{

	private Controller controller;
	
	private BorderPane mainPane;
	private GridPane gameboard;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// set MVC relation first
		controller = TowerDefense.setRelations(this);
		
		// set up the game board
		setupWindow();
		
		
		Scene scene = new Scene(mainPane);
		primaryStage.setTitle("Tower Defense");
		primaryStage.setScene(scene); 
		primaryStage.show();
	}

	
	private void setupWindow() {
		
		mainPane = new BorderPane();
		gameboard = new GridPane();
		
		setupMenu();
		
		setupGameBoard();
		
	}
	
	private void setupMenu() {
		MenuBar menu = new MenuBar();
		
		Menu buyTowers = new Menu();
		buyTowers.setText("Buy Towers");
		
		for (int i = 1; i < 7; i++) {
			MenuItem tower = new MenuItem();
			tower.setText("Tower #" + i);
			
			// set action for buying a tower
			tower.setOnAction(e -> {
				System.out.println("buy");
			});
			
			buyTowers.getItems().add(tower);
		}
		
		menu.getMenus().add(buyTowers);
		mainPane.setTop(menu);
	}
	
	
	private void setupGameBoard() {
		
		gameboard.setVgap(4);
		gameboard.setHgap(1);
		// set margin
		gameboard.setPadding(new Insets(5,0,0,0));
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
		mainPane.setCenter(gameboard);
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		
		
	}

}
