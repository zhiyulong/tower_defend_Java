public class Controller {

	private Model model;
	
	private int settingTower;
	private int[] newTowerPos;
	
	/**
	 * Constructor for a Controller object
	 * @param model	a Model object representing the current state of game
	 */
	public Controller(Model model) {
		this.model = model;
		
		settingTower = 0;
		newTowerPos = null;

	}
	
	/**
	 * Helper method to help translate the position of the mouse into its
	 * location on the game board.
	 * 
	 * @param mouseX	an int of the mouse's horizontal position in the game
	 * @param mouseY	an int of the mouse's vertical position in the game
	 */
	public void mouseMoved(int mouseX, int mouseY) {
		if (settingTower != 0 && mouseX < 586 && mouseX > 0 && mouseY >= 5 && mouseY <= 410) {
			setPos(mouseX, mouseY);
		} else
			newTowerPos = null;
	}
	
	/**
	 * Allows the player to cancel a purchase.
	 * 
	 * @return	int of the player's current currency
	 */
	public int cancelBuying() {
	
			model.addCurrency(settingTower);
			
			settingTower = 0;
			newTowerPos = null;
		
		return model.getCurrency();
	}
	
	/**
	 * Adds a new tower to the game board.
	 * 
	 * @return	int of the position on the board of the new tower
	 */
	public int addTower() {
		int id = model.addTower(newTowerPos[0], newTowerPos[1], settingTower);
		if (id != 0) {
			settingTower = 0;
		}
		return id;
	}
	
	/**
	 * Removes a tower from the game board presumably due to selling.
	 * 
	 * @param row	row coordinate of the square to be cleared.
	 * @param col	col coordinate of the square to be cleared.
	 * 
	 * @return		int of id of the square that has been cleared.
	 */
	public int removeTower(int row, int col) {
		
		int towerID = model.removeTower(row, col);
		model.addCurrency(towerID);
		
		return towerID;
	}
	
	/**
	 * Sets the board position for a tower that is being placed to the x and y
	 * mouse coordinates.
	 * 
	 * @param x	int of horizontal coordinate in the game window.
	 * @param y	int of vertical coordinate in the game window.
	 */
	private void setPos(int x, int y) {

		int[] pos = getPos(x, y);

		if (pos[0] < 6 && pos[1] < 9) {
			newTowerPos = pos;
		}

	}

	/**
	 * Retrives the board position of the x and y mouse coordinates.
	 * 
	 * @param x	int of horizontal coordinate in the game window.
	 * @param y	int of vertical coordinate in the game window.
	 * 
	 * @return	array of ints of board coordinates corresponding to the mouse
	 * 			coordinates.
	 */
	public int[] getPos(int x, int y) {

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
	
	/**
	 * Adjusts player's currency when they purchase a tower.
	 * 
	 * @param towerID	int of the id of the tower to be purchased.
	 * 
	 * @return	int of the amount of currency subtracted or -1 if they do not
	 * 			have enough currency.
	 */
	public int buyTower(int towerID) {
		if (getCurrency() >= towerID && getBlood() > 0) {
			settingTower = towerID;
			
			return model.subtractCurrency(settingTower);
		}
		return -1;
	}
	
	/**
	 * Retrieves the amount of currency the player has.
	 * 
	 * @return	int of the player's currency.
	 */
	public int getCurrency() {
		return model.getCurrency();
	}
	
	/**
	 * Retrieves the amount of blood the player has.
	 * 
	 * @return	int of the player's blood.
	 */
	public double getBlood() {
		return model.getBlood();
	}
	
	/**
	 * Retrieves the position of the last added tower on the board.
	 * 
	 * @return	int of the id of the latest tower's position.
	 */
	public int[] getNewTowerPos() {
		return newTowerPos;
	}
	
	/**
	 * Sets the blood value of newly created enemies.
	 * 
	 * @param enemyID	int of the enemy's ID.
	 */
	public void arrived(int enemyID) {
		model.setBlood(model.getBlood() - enemyID * 10);
	}
	
	
}
