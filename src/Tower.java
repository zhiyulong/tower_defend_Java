import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Tower {
	
	private int towerID;
	private Image image;
	private Circle movingPower;
	
	public Tower(int num) {
		towerID = num;
		image = new Image("./images/tower"+num+".png");
		
		// tower attack visualization
		movingPower = new Circle();
		movingPower.setFill(Color.RED);
		movingPower.setRadius(8);
				
		TranslateTransition movement = new TranslateTransition();
		movement.setDuration(Duration.seconds(3));
		movement.setToX(584);
		movement.setCycleCount(Animation.INDEFINITE);
		movement.setNode(movingPower);
		movement.play();
	}
	
	public int getID() {
		return towerID;
	}
	
	public ImageView getView() {
		return new ImageView(image);
	}
	
	public Circle getMovement() {
		return movingPower;
	}
	
}
