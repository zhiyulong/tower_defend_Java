import java.util.ArrayList;

public class Model {

	private int currency;
	private double blood;

	private ArrayList<ArrayList<Tower>> board;
	private ArrayList<ArrayList<Enemie>> targets;

	public Model() {
		init();
	}

	public void init() {
		currency = 15;
		blood = 100;

		board = new ArrayList<>();
		targets = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			ArrayList<Tower> column = new ArrayList<>();

			for (int j = 0; j < 9; j++) {
				column.add(null);
			}

			board.add(column);

			ArrayList<Enemie> targetsPerRow = new ArrayList<Enemie>();
			targets.add(targetsPerRow);
		}

	}

	public Tower removeTower(int row, int col) {
		Tower tower = board.get(row).get(col);
		if (tower != null)
			board.get(row).set(col, null);

		return tower;
	}

	public Tower addNewTower(int towerID, int row, int col) {

		if (board.get(row).get(col) != null)
			return null;

		Tower tower = new Tower(towerID, col);
		board.get(row).set(col, tower);

		for (Enemie target : targets.get(row)) {
			if (target.getBlood() > 0)
				tower.addTarget(target);
		}
		return tower;
	}

	public void addTargets(int row, Enemie ene) {
		targets.get(row).add(ene);
	}

	/**
	 * Adds currency to the player's balance.
	 * 
	 * @param amount int of what is to be added to the player's balance.
	 */
	public int addCurrency(int amount) {
		currency += amount;
		return currency;
	}

	/**
	 * Subtracts currency from the player's balance.
	 * 
	 * @param amount int of what is to be subtracted from the player's balance.
	 */
	public int subtractCurrency(int amount) {
		currency -= amount;
		return currency;
	}

	/**
	 * This method checks if the player has enough currency to spend on a particular
	 * purchase.
	 * 
	 * @param amount int of the cost of the purchase.
	 * @return boolean of whether or not the player has enough currency.
	 */
	public boolean hasCurrency(int amount) {
		return currency - amount < 0;
	}

	/**
	 * Getter that returns how much currency the player has.
	 * 
	 * @return int of the player's current currency.
	 */
	public int getCurrency() {
		return currency;
	}

	public double getBlood() {
		return blood;
	}

	public void setBlood(double blood) {
		this.blood = blood;
	}

	public void clear() {
		for (int i = 0; i < board.size(); i++) {
			for (int j = 0; j < board.get(i).size(); j++) {
				if (board.get(i).get(j) != null) {

					board.get(i).get(j).remove();
				}

			}
		}
		for (int i = 0; i < targets.size(); i++) {
			for (int j = 0; j < targets.get(i).size(); j++) {
				if (targets.get(i).get(j) != null) {
					targets.get(i).get(j).remove();
				}
			}
		}
	}

	public void stop() {
		for (int i = 0; i < board.size(); i++) {
			for (int j = 0; j < board.get(i).size(); j++) {
				if (board.get(i).get(j) != null) {

					board.get(i).get(j).stop();
				}

			}
		}
		for (int i = 0; i < targets.size(); i++) {
			for (int j = 0; j < targets.get(i).size(); j++) {
				if (targets.get(i).get(j) != null) {
					targets.get(i).get(j).stop();
				}
			}
		}
	}

	public void start() {
		for (int i = 0; i < board.size(); i++) {
			for (int j = 0; j < board.get(i).size(); j++) {
				if (board.get(i).get(j) != null) {

					board.get(i).get(j).start();
				}

			}
		}
		for (int i = 0; i < targets.size(); i++) {
			for (int j = 0; j < targets.get(i).size(); j++) {
				if (targets.get(i).get(j) != null) {
					targets.get(i).get(j).start();
				}
			}
		}

	}

}