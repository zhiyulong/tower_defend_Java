

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
		if (currency >= tower)
			settingTower = tower;
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
				if (settingTower != 0 && newTowerPos != null) {
					
					placeTower(gameboard, newTowerPos[0], newTowerPos[1]);
				}
	        }
			
		 });
		 
	}
	
	private void placeTower(GridPane gameboard, int row, int col) {
		Image tower = new Image("./images/tower"+settingTower+".png");
		
		gameboard.add(new ImageView(tower), col, row);
		
		currency = model.subtractCurrency(settingTower);
		setCurrencyLabel(currency_label);
		
		settingTower = 0;
		newTowerPos = null;
		
		// tower attack visualization
		Circle power = new Circle();
		power.setFill(Color.RED);
		power.setRadius(8);
		
		TranslateTransition movement = new TranslateTransition();
		movement.setDuration(Duration.seconds(3));
		movement.setToX(584);
		movement.setCycleCount(Animation.INDEFINITE);
		movement.setNode(power);
		movement.play();
		
		gameboard.add(power, col, row);
		
	}
	
	
	public void setCurrencyLabel(Label label) {
		currency_label = label;
		label.setText("$ " + currency);
	}
	
	private void setPos(int x, int y) {
		
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
		
		if (pos[0] < 6 && pos[1] < 9) {
			newTowerPos = pos;
		}
	
	}
	
	
}
