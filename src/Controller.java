
public class Controller {

	private Model model;
	
	private int settingTower;
	private int[] newTowerPos;
	
	public Controller(Model model) {
		this.model = model;
		
		settingTower = 0;
		newTowerPos = null;

	}
	
	public void mouseMoved(int mouseX, int mouseY) {
		if (settingTower != 0 && mouseX < 586 && mouseX > 0 && mouseY >= 5 && mouseY <= 410) {
			setPos(mouseX, mouseY);
		} else
			newTowerPos = null;
	}
	
	public int cancelBuying() {
	
			model.addCurrency(settingTower);
			
			settingTower = 0;
			newTowerPos = null;
		
		return model.getCurrency();
	}
	
	public int addTower() {
		int id = model.addTower(newTowerPos[0], newTowerPos[1], settingTower);
		if (id != 0) {
			settingTower = 0;
		}
		return id;
	}
	
	public int removeTower(int row, int col) {
		
		int towerID = model.removeTower(row, col);
		model.addCurrency(towerID);
		
		return towerID;
	}
	
	private void setPos(int x, int y) {

		int[] pos = getPos(x, y);

		if (pos[0] < 6 && pos[1] < 9) {
			newTowerPos = pos;
		}

	}

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
	
	public int buyTower(int towerID) {
		if (getCurrency() >= towerID && getBlood() > 0) {
			settingTower = towerID;
			
			return model.subtractCurrency(settingTower);
		}
		return -1;
	}
	
	
	public int getCurrency() {
		return model.getCurrency();
	}
	
	public double getBlood() {
		return model.getBlood();
	}
	
	public int[] getNewTowerPos() {
		return newTowerPos;
	}
	
	public void arrived(int enemyID) {
		model.setBlood(model.getBlood() - enemyID * 10);
	}
	
	
}
