import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Tower {
	
	private int towerID;
	private ImageView image;
	private Circle movingPower;
	private TranslateTransition movement;
	
	private Color[] powerColor = new Color[]{Color.RED, Color.BLUE, Color.DEEPPINK, 
								Color.YELLOW, Color.GREEN, Color.ORANGE};
	
	public Tower(int num) {
		towerID = num;
		image = new ImageView(new Image("./images/tower"+num+".png"));
		
		// tower attack visualization
		movingPower = new Circle();
		movingPower.setFill(powerColor[num-1]);
		movingPower.setRadius(8);
				
		movement = new TranslateTransition();
		movement.setDuration(Duration.seconds(2));
		movement.setToX(584);
		movement.setCycleCount(Animation.INDEFINITE);
		movement.setNode(movingPower);
		movement.play();
	}
	
	public int getID() {
		return towerID;
	}
	
	public ImageView getView() {
		return image;
	}
	
	public Circle getMovement() {
		return movingPower;
	}
	
	public void remove() {
		movement.stop();
		image.setVisible(false);
		movingPower.setVisible(false);
	}
	
}
