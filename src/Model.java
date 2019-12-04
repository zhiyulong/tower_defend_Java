import java.util.Observable;

public class Model extends Observable{

	private int currency;
	
	public Model() {
		currency = 2;
	}
	
	
	/**
	 * Adds currency to the player's balance.
	 * 
	 * @param amount	int of what is to be added to the player's balance.
	 */
	public void addCurrency(int amount) {
		currency += amount;
	}
	
	/**
	 * Subtracts currency from the player's balance.
	 * 
	 * @param amount	int of what is to be subtracted from the player's balance.
	 */
	public void subtractCurrency(int amount) {
		currency -= amount;
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
	
	
	
}
