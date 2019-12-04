

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Controller {

	private Model model;

	private int settingTower;
	private int[] newTowerPos;

	private int currency;
	private Label currency_label;


	public Controller(Model model) {
		this.model = model;

		settingTower = 0;
		newTowerPos = null;

		this.currency = model.getCurrency();

	}
	
	public void buyTower(int tower) {
		if (currency >= tower) {
			settingTower = tower;
			
			currency = model.subtractCurrency(settingTower);
			setCurrencyLabel(currency_label);
		}
	}
	
	
	public void addEventForGameBoard(GridPane gameboard) {
		
		gameboard.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent event) {
                int mouseX = ((int) event.getX());
                int mouseY = ((int) event.getY());
                
                if (settingTower != 0 && mouseX < 586 && mouseX > 0 && mouseY >=5 && mouseY <= 410) {
                	setPos(mouseX, mouseY);	
                }
                else
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
						setCurrencyLabel(currency_label);
						
						settingTower = 0;
						newTowerPos = null;
					}
					else
						placeTower(gameboard, newTowerPos[0], newTowerPos[1]);
				}
	        }

			
		 });
		 
	}
	
	private void sellTower(Tower tower) {
		currency = model.addCurrency(tower.getID());
		setCurrencyLabel(currency_label);
		
		tower.remove();
	}
	
	private void placeTower(GridPane gameboard, int row, int col) {
		
		Tower tower = model.addNewTower(settingTower, row, col);
		
		if (tower != null) {
			gameboard.add(tower.getView(), col, row);
			
			settingTower = 0;
			newTowerPos = null;
			
			gameboard.add(tower.getMovement(), col, row);
		}
		
	}
	
	
	public void setCurrencyLabel(Label label) {
		currency_label = label;
		label.setText("$ " + currency);
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
		pos[0] = 0; pos[1] = 0;
		
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
	
	
}
