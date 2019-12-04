import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Enemie {
	
	private int enemieID;
	private ImageView image;
	
	private TranslateTransition movement;
	
	public int blood;
	
	private int[] speed = new int[]{40, 30, 20, 15};
	private int[] live = new int[]{50, 70, 80, 100};
	
	public Enemie(int num) {
		enemieID = num+1;
		blood = live[num];
		
		image = new ImageView(new Image("./images/enemie"+(num+1)+".gif"));
		image.setFitHeight(65);
		image.setFitWidth(65);
		
		movement = new TranslateTransition(Duration.seconds(speed[num]), image);
		movement.setToX(-584);
		movement.setCycleCount(1);
		movement.play();
		
	}
	
	
	public ImageView getView() {
		return image;
	}
	
	public int getBlood() {
		return blood;
	}
	
	public void subBlood(int num) {
		blood -= num;
	}
	
}
