import java.util.ArrayList;

public class Model {

	private int currency;
	private double blood;
	
	private ArrayList<ArrayList<Integer>> board;
	
	public Model(String mode) {
		if (mode.equals("easy"))
			currency = 15;
		else 
			currency = 10;
		blood = 100;
		
		init();
	}
	
	public void init() {
		board = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			ArrayList<Integer> column = new ArrayList<>();

			for (int j = 0; j < 9; j++) {
				column.add(0);
			}

			board.add(column);
		}
		
	}
	
	public int addTower(int row, int col, int id) {
		if (board.get(row).get(col) != 0) 
			return 0;
		
		board.get(row).set(col, id);
		return id;
	}
	
	public int removeTower(int row, int col) {
		int id = board.get(row).get(col);
		if (id != 0) 
			board.get(row).set(col, 0);
		
		return id;
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
	 * Getter that returns how much currency the player has.
	 * 
	 * @return int of the player's current currency.
	 */
	public int getCurrency() {
		return currency;
	}

	public void setBlood(double blood) {
		this.blood = blood;
	}
	
	public double getBlood() {
		return blood;
	}
	
	
	
}
