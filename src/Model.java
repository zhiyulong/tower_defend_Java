import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable{

	private int currency;
	private double blood;
	
	private ArrayList<ArrayList<Tower>> board;
	private ArrayList<Enemie> enemies;
	
	public Model() {
		currency = 15;
		blood = 100;
		enemies=new ArrayList<>();
		board = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			ArrayList<Tower> column = new ArrayList<>();
			for (int j = 0; j < 9; j++) {
				column.add(null);
			}
			board.add(column);
		}

	}
	
	
	public Tower removeTower(int row, int col) {
		Tower tower = board.get(row).get(col);
		if (tower != null)
			board.get(row).set(col, null);
		
		return tower;
	}
	public ArrayList<Enemie> getEnemy(){
		return enemies;
	}
	public Enemie addNewEnemies(int id) {
		Enemie enemy=new Enemie(id);
		enemies.add(enemy);
		return enemy;
	}
	public Tower addNewTower(int towerID, int row, int col) {
		
		if (board.get(row).get(col) != null)
			return null;
		
		Tower tower = new Tower(towerID);
		board.get(row).set(col, tower);
		return tower;
	}
	
	/**
	 * Adds currency to the player's balance.
	 * 
	 * @param amount	int of what is to be added to the player's balance.
	 */
	public int addCurrency(int amount) {
		currency += amount;
		return currency;
	}
	
	/**
	 * Subtracts currency from the player's balance.
	 * 
	 * @param amount	int of what is to be subtracted from the player's balance.
	 */
	public int subtractCurrency(int amount) {
		currency -= amount;
		return currency;
	}
	
	/**
	 * This method checks if the player has enough currency to spend on a 
	 * particular purchase.
	 * 
	 * @param amount	int of the cost of the purchase.
	 * @return			boolean of whether or not the player has enough
	 * 					currency.
	 */
	public boolean hasCurrency(int amount) {
		return currency - amount < 0;
	}
	
	/**
	 * Getter that returns how much currency the player has.
	 * 
	 * @return	int of the player's current currency.
	 */
	public int getCurrency() {
		return currency;
	}
	
	
	public double getBlood() {
		return blood;
	}
	
	
}
