
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Set;

import com.sun.javafx.scene.paint.GradientUtils.Point;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * 
 * This class is an implement of a single tower.
 * It's used for GUI only, implement the visualization of each 
 * tower that shows on screen.
 *
 */
public class TowerGUI extends Observable {

	private int towerID;
	private ImageView image;
	private Circle movingPower;
	private TranslateTransition movement;

	private Color[] powerColor;
	private double[] powerSpeed;

	private int col;
	private int row;
	private ArrayList<EnemieGUI> targets;

	private ChangeListener listener;

	/**
	 * Constructor for Tower object
	 * 
	 * @param num	id of the Tower's style
	 * @param col	col the Tower should occupy
	 * @param row	row the Tower should occupy
	 */
	public TowerGUI(int num, int col, int row) {
		powerSpeed = new double[] { 5, 3, 2, 1.5, 1, 0.6 };
		powerColor = new Color[] { Color.RED, Color.BLUE, Color.DEEPPINK, Color.YELLOW, 
				Color.GREEN, Color.ORANGE };
		
		this.col = col;
		this.row = row;
		
		towerID = num;
		image = new ImageView(new Image("./images/tower" + num + ".png"));

		// tower attack visualization
		movingPower = new Circle();
		movingPower.setFill(powerColor[num - 1]);
		movingPower.setRadius(8);

		movement = new TranslateTransition();
		movement.setDuration(Duration.seconds(powerSpeed[num - 1]));
		movement.setFromX(0);
		movement.setToX(584);
		movement.setCycleCount(Animation.INDEFINITE);
		movement.setNode(movingPower);
		movement.play();
		
		targets = new ArrayList<EnemieGUI>();
		movementEvent();

	}

	/**
	 * Animation for the Tower firing and being destroyed
	 */
	public void movementEvent() {
		
		listener = new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				try {
					for (EnemieGUI target : targets) {
						if (target.getBlood() > 0) {
							int targetX = getCol(target.getTransX()) + 1;
							int x = getCol(movingPower.getTranslateX()) + col;
							
							// enemy kills tower
							if (targetX == col) {
								remove();
								setChanged();
								notifyObservers(new int[] {row, col});
							}
							
							else if (targetX < 9 && x < 9 && targetX == x) {
								
								target.remove();
								movement.playFromStart();
									
								setChanged();
								notifyObservers(target.getID());			
							
							}
						}
					}
				} catch (Exception e) {
					
				}
			}
		};
		
		movingPower.translateXProperty().addListener(listener);

	}

	/**
	 * Getter for the horizontal position of the Tower relative to the board
	 * 
	 * @param x	Tower object's position.
	 * 
	 * @return	int of the column the Tower occupies on the board.
	 */
	private int getCol(double x) {
		int col = 0;

		int minx = 0, maxx = 70;
		while (!(x >= minx && x <= maxx)) {
			col += 1;
			minx = maxx + 1;
			maxx += 65 + 1;
		}

		return col;
	}

	/**
	 * Changes the Tower's target to new enemies.
	 * 
	 * @param targets	Enemie objects that are to be targeted.
	 */
	public void setTarget(ArrayList<EnemieGUI> targets) {
		this.targets = targets;
	}
	
	/**
	 * Adds a single target to the Tower's list of targets.
	 * 
	 * @param ene	Enemie object to be targeted.
	 */
	public void addTarget(EnemieGUI ene) {
		targets.add(ene);
	}

	/**
	 * Getter for the id representing the type of Tower.
	 * 
	 * @return	int representing the type of Tower.
	 */
	public int getID() {
		return towerID;
	}

	/**
	 * Getter for the ImageView of the Tower.
	 * 
	 * @return	ImageView which holds the image representing the Tower.
	 */
	public ImageView getView() {
		return image;
	}

	/**
	 * Getter for the Tower's bullets.
	 * 
	 * @return	Circle representing the Tower's bullets.
	 */
	public Circle getMovement() {
		return movingPower;
	}

	/**
	 * Disables and removes a Tower from the board.
	 */
	public void remove() {
		movement.stop();
		image.setVisible(false);
		movingPower.setVisible(false);
		movingPower.translateXProperty().removeListener(listener);
	}

	/**
	 * Causes the Tower to stop attacking.
	 */
	public void stop() {
		movement.pause();
	}

	/**
	 * Causes the Tower to start attacking.
	 */
	public void start() {
		movement.play();
	}

	/**
	 * Sets the Tower's firing speed to its x1 rate.
	 */
	public void normal() {
		movement.setRate(1);
	}
	
	/**
	 * Sets the Tower's firing speed to its x5 rate.
	 */
	public void fast() {
		movement.setRate(5);
	}
	
	/**
	 * Getter for the Tower's vertical position on the board.
	 * 
	 * @return	int representing the Tower's row on the board.
	 */
	public int getRow() {
		return row;
	}

	
}