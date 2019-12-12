import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * 
 * This class is the implementation of View
 *
 */
public class View extends Application implements Observer {

	private Controller controller;
	
	private Stage menu;
	private Stage gameStage;
	
	private BorderPane mainPane;
	private GridPane gameboard;
	
	private Label currency_label;
	private Label blood_label;
	private Label buying_label;
	
	private String mode;
	
	private ArrayList<ArrayList<TowerGUI>> board;
	private ArrayList<ArrayList<EnemieGUI>> targets;
	private int enemiesPerTime;
	private int enemiesSize;
	
	private boolean nonChangableMode;
	private boolean lose;
	
	/**
	 * Constructor for the View object governing what the player sees.
	 * 
	 * @param menu	menu that is being displayed.
	 * @param mode	The mode the player has selected.
	 */
	public View(Stage menu, String mode) {
		super();
		
		this.menu = menu;
		this.mode = mode;
		enemiesSize += 0;
		enemiesPerTime = 3;
		
		nonChangableMode = false;
		lose = false;
		
		init();
	}
	
	/**
	 * initialize the game board array list for GUI.
	 */
	public void init() {
		// set MVC relation first
		controller = TowerDefense.setRelations(this, mode);
				
		board = new ArrayList<>();
		targets = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			ArrayList<TowerGUI> column = new ArrayList<>();

			for (int j = 0; j < 9; j++) {
				column.add(null);
			}

			board.add(column);

			ArrayList<EnemieGUI> targetsPerRow = new ArrayList<EnemieGUI>();
			targets.add(targetsPerRow);
		}
	}
	
	/**
	 * Starts up the game in the View.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.gameStage = primaryStage;
		
		mainPane = new BorderPane();
		gameboard = new GridPane();
		Scene scene = new Scene(mainPane);
		
		// set up the game
		setupGame();

		gameStage.setTitle("Tower Defense: "+mode+" mode");
		gameStage.setScene(scene);
		gameStage.show();
		
	}
	
	/**
	 * Helper method to manage setting up the game to be displayed.
	 */
	private void setupGame() {
		displayHome();
		
		setupUppSideMenu();
		setupLeftSideMenu();
		
		setupGameBoard();
		
		addEnemies();
	}
	
	/**
	 * Helper method to set up the game board to be displayed.
	 */
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
		addEventForGameBoard();
		mainPane.setCenter(gameboard);
		
	}
	
	/**
	 * Adds enemies to the board in order to start the game.
	 * This method will be called when the number of enemies 
	 * is less or equal to 2.
	 */
	private void addEnemies() {
		if (controller.getBlood() > 0) {
			Random rand = new Random();
			
			for (int i = 0; i < enemiesPerTime; i++) {
				int row = rand.nextInt(6);
				int enimeID = rand.nextInt(4);
				
				EnemieGUI enemie = new EnemieGUI(enimeID, row, mode, nonChangableMode);
				enemie.addObserver(this);
				
				addTargets(row, enemie);
				gameboard.add(enemie.getView(), 9, row);
			}
			enemiesSize += enemiesPerTime;
			enemiesPerTime++;
		}
	}
	
	/**
	 * Adds Enemie objects to the list of valid targets for a specific row.
	 * 
	 * @param row	row to add targets to.
	 * @param ene	Enemie object to become a valid target for the row.
	 */
	public synchronized void addTargets(int row, EnemieGUI ene) {
		targets.get(row).add(ene);
		
		for (TowerGUI tower: board.get(row)) {
			if (tower != null) {
				tower.addTarget(ene);
			}
		}
		
	}
	
	/**
	 * Handles mouse events for the game.
	 */
	private void addEventForGameBoard() {
		
		gameboard.setOnMouseMoved(new EventHandler<MouseEvent>() {
			/**
			 * Handles mouse movement.
			 */
			@Override
			public void handle(MouseEvent event) {
				int mouseX = ((int) event.getX());
				int mouseY = ((int) event.getY());
				
				controller.mouseMoved(mouseX, mouseY);
			}

		});
		
		gameboard.setOnMouseClicked(new EventHandler<MouseEvent>() {
			/**
			 * Handles mouse clicks.
			 */
			@Override
			public void handle(MouseEvent event) {

				// check removing tower
				if (!nonChangableMode && buying_label.getText().equals("Not buying") 
						&& event.getButton() == MouseButton.SECONDARY) {
					int[] pos = controller.getPos((int) event.getX(), (int) event.getY());
					
					if (controller.removeTower(pos[0], pos[1]) != 0)
						sellTower(pos[0], pos[1]);
				}
				
				// check adding tower
				else if (!nonChangableMode && (!buying_label.getText().equals("Not buying")) 
						&& controller.getNewTowerPos() != null) {

					// right click cancel buying the tower
					if (event.getButton() == MouseButton.SECONDARY) {
						int money = controller.cancelBuying();
						currency_label.setText("$ " + money);
					} else
						placeTower();
				}
				
			}


		});
		
	}
	
	/**
	 * Places a Tower on the View.
	 */
	private void placeTower() {
		int id = controller.addTower();
		
		if (id != 0) {
			int[] pos = controller.getNewTowerPos();
			int row = pos[0];
			int col = pos[1];
			
			TowerGUI tower = new TowerGUI(id, col, row);
			tower.addObserver(this);
			
			gameboard.add(tower.getView(), col, row);
			gameboard.add(tower.getMovement(), col, row);
			
			board.get(row).set(col, tower);
			tower.setTarget(targets.get(row));
			
			buying_label.setText("Not buying");
		}
		
	}
	
	/**
	 * Sells a Tower object at the specified coordinates of the board.
	 * @param row	row coordinate of the Tower to be sold.
	 * @param col	col coordinate of the Tower to be sold.
	 */
	private void sellTower(int row, int col) {
		currency_label.setText("$ " + controller.getCurrency());
		
		// remove the tower
		TowerGUI tower = board.get(row).get(col);
		tower.remove();
		
		board.get(row).set(col, null);
		
	}

	/**
	 * Displays controls for the game to the player.
	 */
	private void setupLeftSideMenu() {

		// button about new game, pause, fast.
		GridPane mainMenu = new GridPane();
		mainMenu.setVgap(20);

		Button backToMenu = new Button("Back to Menu");
		Button newgame = new Button("Restart");
		
		VBox stopBegin = new VBox();
		Label status = new Label("Running");
		Button pause = new Button("Pause");
		Button start = new Button("Start");
		stopBegin.getChildren().addAll(status, pause, start);
		
		VBox speed = new VBox();
		Label speedStatus = new Label("Normal speed");
		Button fast = new Button("Fast");
		Button normal= new Button("Normal");
		speed.getChildren().addAll(speedStatus, fast, normal);
		
		mainMenu.add(backToMenu, 0, 0);
		mainMenu.add(newgame, 0, 1);
		mainMenu.add(stopBegin, 0, 2);
		mainMenu.add(speed, 0, 3);
		
		// behavior of new game, pause, fast;
		backToMenu.setOnMouseClicked(e -> {
			backToMenu();

		});
		newgame.setOnMouseClicked(e -> {
			startNewGame();
		});
		pause.setOnMouseClicked(e -> {
			nonChangableMode = true;
			status.setText("Pausing");
			exec(pause.getText());
		});
		start.setOnMouseClicked(e -> {
			if (speedStatus.getText().equals("Normal speed"))
				nonChangableMode = false;
			status.setText("Running");
			exec(start.getText());
		});
		fast.setOnMouseClicked(e -> {
			nonChangableMode = true;
			speedStatus.setText("Fast Forward");
			exec(fast.getText());
		});
		normal.setOnMouseClicked(e -> {
			nonChangableMode = false;
			speedStatus.setText("Normal speed");
			exec(normal.getText());

		});
		
		mainPane.setRight(mainMenu);
		
	}
	
	/**
	 * Handles incoming commands from the player and changes the board
	 * accordingly.
	 * @param text	command to be executed.
	 */
	public void exec(String text) {
		for(int i=0; i<board.size();i++) {
			for(int j=0; j<board.get(i).size();j++) {
				if(board.get(i).get(j)!=null) {
					if(text.equals("Start")) {
						board.get(i).get(j).start();
					}
					if(text.equals("Pause")) {
						board.get(i).get(j).stop();
					}					
					if(text.equals("Fast")) {
						board.get(i).get(j).fast();
					}					
					if(text.equals("Normal")) {
						board.get(i).get(j).normal();
					}

				}

			}
		}

		for(int i=0; i<targets.size();i++) {
			for(int j=0; j<targets.get(i).size();j++) {
				if(targets.get(i).get(j)!=null) {
					if(text.equals("Start")) {
						targets.get(i).get(j).start();
					}
					if(text.equals("Pause")) {
						targets.get(i).get(j).stop();
					}					
					if(text.equals("Fast")) {
						targets.get(i).get(j).fast();
					}					
					if(text.equals("Normal")) {
						targets.get(i).get(j).normal();
					}

				}
			}
		}

	}

	/**
	 * Pauses the game and displays the game's main menu.
	 */
	private synchronized void backToMenu() {
		exec("Pause");
		gameStage.hide();
		
		Platform.runLater(new Runnable() {
			public void run() {
				Button last = new Button("Last Stage: " + mode + " mode");
				last.setFont(Font.font(20));
				
				BorderPane borderPane = (BorderPane) menu.getScene().getRoot();
				HBox hbox = (HBox) borderPane.getChildren().get(0);
				VBox mainVBox = (VBox) hbox.getChildren().get(0);
				if (mainVBox.getChildren().size() == 4) {
					mainVBox.getChildren().add(last);
				}
				else 
					mainVBox.getChildren().set(4, last);
				
				last.setOnAction((ActionEvent e1) -> {
					// load Easy model
					Platform.runLater(new Runnable() {
						public void run() {
							try {
								gameStage.show();
								exec("Start");
							} catch (Exception e) {
								
								e.printStackTrace();
							}
						}
					});
					menu.hide();

				});
				
				menu.show();
			}
		});
		
	}
	
	/**
	 * Ends the current game and begins a new one.
	 */
	private synchronized void startNewGame() {
		gameStage.close();

		// reload
		Platform.runLater(() -> {
			try {
				new View(menu, mode).start(new Stage());
			} catch (Exception e) {

				e.printStackTrace();
			}
		});

	}

	/**
	 * Displays a menu of in-game controls.
	 */
	private void setupUppSideMenu() {
		MenuBar menu = new MenuBar();
		
		// buy towers
		Menu buyTowers = new Menu();
		buyTowers.setText("Buy Towers");
		
		// buying status
		buying_label = new Label("Not buying");
		Menu buyingStatus = new Menu("", buying_label);

		// currency
		currency_label = new Label("$ " + controller.getCurrency());
		Menu currency = new Menu("", currency_label);

		for (int i = 1; i < 7; i++) {
			MenuItem tower = new MenuItem();
			tower.setText("$" + i + " Tower #" + i);

			// set action for buying a tower
			tower.setOnAction(e -> {
				if (!nonChangableMode) {
					int towerID = Character.getNumericValue(tower.getText().charAt(10));
					int money = controller.buyTower(towerID);
					if (money != -1) {
						currency_label.setText("$ " + money);
						buying_label.setText("Placing #" + towerID);
					}
				}
				
			});

			buyTowers.getItems().add(tower);
		}

		// blood status
		blood_label = new Label("Blood: " + controller.getBlood());
		Menu blood = new Menu("", blood_label);

		menu.getMenus().add(buyTowers);
		menu.getMenus().add(buyingStatus);
		menu.getMenus().add(currency);
		menu.getMenus().add(blood);

		mainPane.setTop(menu);
	}
	
	/**
	 * Displays the back (left-most) column that represents the end of where
	 * enemies are trying to reach.
	 */
	private void displayHome() {
		ImageView tower = new ImageView(new Image("./images/TOWER.png"));
		tower.setFitHeight(410);
		tower.setFitWidth(100);
		mainPane.setLeft(tower);
	}

	/**
	 * Updates the observer on changes in the game.
	 */
	@Override
	public synchronized void update(Observable o, Object arg) {

		// enemy arrived home
		if (o instanceof EnemieGUI) {
			int enemyID = Character.getNumericValue(arg.toString().charAt(0));
			controller.arrived(enemyID);
			
			blood_label.setText("Blood: " + controller.getBlood());
			
			EnemieGUI ene = ((EnemieGUI) o);
			ene.remove();
			removeEnemy(ene.getRow(), ene.getID());
			enemiesSize--;
		}
		// enemy killed by tower
		else if (o instanceof TowerGUI) {
			if (arg instanceof int[]) {
				// tower met enemy
				int[] pos = (int[]) arg;
				board.get(pos[0]).set(pos[1], null);
			}else {
				enemiesSize --;
				removeEnemy(((TowerGUI) o).getRow(), (int) arg);
			}
		}		
		
		if (controller.getBlood() <= 0) {
			
			if (!lose) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("You Lose!");
				alert.show();
				
				gameboard.getChildren().clear();	
				lose = true;
			}
		}
		
		if (enemiesSize <= 2)
			addEnemies();
		
	}
	
	/**
	 * Removes an enemy from the game.
	 * 
	 * @param row	row that the enemy is currently in.
	 * @param id	id of the Tower that is attacking the enemy.
	 */
	private void removeEnemy(int row, int id) {
		EnemieGUI ene = null;
		for (EnemieGUI target: targets.get(row)) {
			if (target.getID() == id) {
				ene = target;
				targets.get(row).remove(target);
				for (TowerGUI tower: board.get(row)) {
					if (tower != null)
						tower.setTarget(targets.get(row));
				}
				break;
			}
		}
		
	}

}
