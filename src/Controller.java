import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class Controller extends Observable {

	private Model model;

	private int settingTower;
	private int[] newTowerPos;

	private int currency;
	private Label currency_label;
	private Label buying_status_label;
	private Label blood_label;

	private int enemiesPerTimes;
	private int enemiesSize;

	GridPane gameboard;

	public Controller(Model model) {
		this.model = model;

		settingTower = 0;
		newTowerPos = null;

		this.currency = model.getCurrency();
		enemiesPerTimes = 4;
		enemiesSize = 0;
	}

	public void buyTower(int tower) {
		if (currency >= tower) {
			settingTower = tower;

			currency = model.subtractCurrency(settingTower);
			currency_label.setText("$ " + currency);
			;

			buying_status_label.setText("Placing #" + tower);
		}
	}

	public void addEventForGameBoard(GridPane gameboard, View view) {

		this.gameboard = gameboard;

		gameboard.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int mouseX = ((int) event.getX());
				int mouseY = ((int) event.getY());

				if (settingTower != 0 && mouseX < 586 && mouseX > 0 && mouseY >= 5 && mouseY <= 410) {
					setPos(mouseX, mouseY);
				} else
					newTowerPos = null;

			}

		});

		gameboard.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				// check removing tower
				if (settingTower == 0 && event.getButton() == MouseButton.SECONDARY) {
					int[] pos = getPos((int) event.getX(), (int) event.getY());

					Tower tower = model.removeTower(pos[0], pos[1]);

					if (tower != null)
						sellTower(tower);
				}

				// check adding tower
				else if (settingTower != 0 && newTowerPos != null) {

					// right click cancel buying the tower
					if (event.getButton() == MouseButton.SECONDARY) {
						currency = model.addCurrency(settingTower);
						currency_label.setText("$ " + currency);
						;
						buying_status_label.setText("Not buying");

						settingTower = 0;
						newTowerPos = null;
					} else
						placeTower(newTowerPos[0], newTowerPos[1], view);
				}
			}

		});

	}

	public void setUpEnemies(View view) {

		if (model.getBlood() > 0) {

			Random rand = new Random();
			for (int i = 0; i < enemiesPerTimes; i++) {

				int row = rand.nextInt(6);
				int enimeID = rand.nextInt(4);

				Enemie enemie = new Enemie(enimeID, row);
				model.addTargets(row, enemie);
				enemie.addObserver(view);
				gameboard.add(enemie.getView(), 9, row);
			}
			enemiesSize += enemiesPerTimes;
			enemiesPerTimes++;
		}
	}

	public int get_enemiesSize() {
		return enemiesSize;
	}

	private void sellTower(Tower tower) {
		currency = model.addCurrency(tower.getID());
		currency_label.setText("$ " + currency);

		tower.remove();
	}

	private void placeTower(int row, int col, View view) {

		Tower tower = model.addNewTower(settingTower, row, col);

		if (tower != null) {
			tower.addObserver(view);
			gameboard.add(tower.getView(), col, row);

			settingTower = 0;
			newTowerPos = null;

			gameboard.add(tower.getMovement(), col, row);

			buying_status_label.setText("Not buying");
		}

	}

	public void setCurrencyLabel(Label label) {
		currency_label = label;
		currency_label.setText("$ " + currency);
	}

	public void setBuyingStatus(Label status) {
		buying_status_label = status;
	}

	public void setBloodStatus(Label blood) {
		blood_label = blood;
		blood_label.setText("Blood: " + model.getBlood());
	}

	private void setPos(int x, int y) {

		int[] pos = getPos(x, y);

		if (pos[0] < 6 && pos[1] < 9) {
			newTowerPos = pos;
		}

	}

	private int[] getPos(int x, int y) {

		// pos[0]:row, pos[1]:col
		int[] pos = new int[2];
		pos[0] = 0;
		pos[1] = 0;

		int minx = 0, maxx = 70, miny = 5, maxy = 71;
		while (!(x >= minx && x <= maxx)) {
			pos[1] += 1;
			minx = maxx + 1;
			maxx += 65 + 1;
		}

		while (!(y >= miny && y <= maxy)) {
			pos[0] += 1;
			miny = maxy + 4;
			maxy += 65 + 4;
		}

		return pos;
	}

	public void arrived(Observable o, Object arg) {
		int enemyID = Character.getNumericValue(arg.toString().charAt(0));

		model.setBlood(model.getBlood() - enemyID * 10);
		blood_label.setText("Blood: " + model.getBlood());

		((Enemie) o).remove();
		enemiesSize--;
		
		model.removeEnemy(((Enemie) o).getRow(), ((Enemie) o).getID());
	}

	public void killed(Observable o, Object arg) {
		enemiesSize--;
		
		int ID = (int) arg;
		model.removeEnemy(((Tower) o).getRow(), ID);
	}

	public double getBlood() {

		return model.getBlood();
	}


	public void newgame() {

		model.init();
	}
	
	public void removeAll() {
		model.removeAll();
		
	}



	public void exec(String text) {
		model.exec(text);

	}

}
