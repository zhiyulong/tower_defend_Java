import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Controller {

	private Model model;
	private int settingTower = 0;
	private int[] newTowerPos = null;

	
	public Controller(Model model) {
		this.model = model;
	}
	
	public void buyTower(int tower) {
		
		settingTower = tower;
	}
	
	Image green = new Image("./images/green.png");
	public void addEventForGameBoard(GridPane gameboard) {
		
		gameboard.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent event) {
                int mouseX = ((int) event.getX());
                int mouseY = ((int) event.getY());
                
                if (settingTower != 0 && mouseX < 586 && mouseX > 0 && mouseY >=5 && mouseY <= 410) {
            
                	setPos(mouseX, mouseY);	
                }
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
		Image green = new Image("./images/enemie"+settingTower+".png");
		
		gameboard.add(new ImageView(green), col, row);
		
		settingTower = 0;
		newTowerPos = null;
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
