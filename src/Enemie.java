
import java.util.Observable;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Enemie extends Observable {

	private int enemieID;
	private ImageView image;

	private TranslateTransition movement;

	private int blood;
	private int row;

	private int[] speed;
	private int[] live;

	public Enemie(int num, int row, String mode, boolean faster) {
		this.row = row;
		
		live = new int[] { 50, 70, 80, 100 };
		if (mode.equals("easy"))
			speed = new int[] { 40, 30, 20, 15 };
		else
			speed = new int[] { 20, 15, 10, 7 };
		
		enemieID = num + 1;
		blood = live[num];

		image = new ImageView(new Image("./images/enemie" + (num + 1) + ".gif"));
		image.setFitHeight(65);
		image.setFitWidth(65);

		movement = new TranslateTransition(Duration.seconds(speed[num]), image);
		movement.setToX(-584);
		movement.setCycleCount(1);
		movement.play();

		if (faster)	fast();
			
		movementEvent();
	}

	public void movementEvent() {

		// if arrived home
		movement.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				if (blood > 0) {
					setChanged();
					notifyObservers(enemieID + "enemies arrive!");
				}
			}
		});
	}

	public double getTransX() {
		return 584 + image.getTranslateX();
	}

	public void stop() {
		movement.pause();
	}

	public void remove() {
		blood = 0;
		movement.stop();
		image.setVisible(false);
		image.getProperties().clear();
	}

	public int getID() {
		return enemieID;
	}

	public ImageView getView() {
		return image;
	}

	public int getBlood() {
		return blood;
	}

	public void setBlood(int blood) {
		this.blood = blood;
	}

	public TranslateTransition getMovement() {
		return movement;
	}

	public void subBlood(int num) {
		blood -= num;
	}

	public void start() {

		movement.play();
	}

	public void normal() {
		movement.setRate(1);
	}
	public void fast() {
		movement.setRate(5);
	}
	
	public int getRow() {
		return row;
	}


}