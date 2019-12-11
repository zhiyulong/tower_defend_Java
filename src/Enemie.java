
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

	/**
	 * Constructor for an Enemie object.
	 * 
	 * @param num		int of the number of enemies to be spawned.
	 * @param row		int of the row to spawn the enemies at.
	 * @param mode		String of the difficulty level.
	 * @param faster	boolean of the speed of the enemies.
	 */
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

	/**
	 * Notifies observers that enemies have made it to the last column.
	 */
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

	/**
	 * Pauses all enemies.
	 */
	public void stop() {
		movement.pause();
	}

	/**
	 * Removes an enemy from the screen.
	 */
	public void remove() {
		blood = 0;
		movement.stop();
		image.setVisible(false);
		image.getProperties().clear();
	}

	/**
	 * Getter for an enemy's ID.
	 * 
	 * @return	int of the ID of an enemy.
	 */
	public int getID() {
		return enemieID;
	}

	/**
	 * Getter for an enemy's image.
	 * 
	 * @return	ImageView containing the image of the enemy.
	 */
	public ImageView getView() {
		return image;
	}

	/**
	 * Getter for the blood/life amount of the enemy.
	 * 
	 * @return	int of the blood the enemy has.
	 */
	public int getBlood() {
		return blood;
	}

	/**
	 * Sets the blood/life amount of the enemy.
	 * 
	 * @param blood	int of the amount of blood the enemy has.
	 */
	public void setBlood(int blood) {
		this.blood = blood;
	}

	/**
	 * Getter for the TranslateTransition of the enemy.
	 * 
	 * @return	TranslateTransition of the enemy.
	 */
	public TranslateTransition getMovement() {
		return movement;
	}

	/**
	 * Subtracts the enemy's blood.
	 * 
	 * @param num	int of the amount of blood to be subtracted from the enemy
	 * 				upon hit.
	 */
	public void subBlood(int num) {
		blood -= num;
	}

	/**
	 * Begins the enemy's animation across the screen.
	 */
	public void start() {

		movement.play();
	}

	/**
	 * Sets the enemy's speed to x1.
	 */
	public void normal() {
		movement.setRate(1);
	}
	
	/**
	 * Sets the enemy's speed to x5.
	 */
	public void fast() {
		movement.setRate(5);
	}
	
	/**
	 * Getter for the row the enemy is attacking.
	 * 
	 * @return	int of the row the enemy is in.
	 */
	public int getRow() {
		return row;
	}


}