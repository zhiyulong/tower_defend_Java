
import java.util.ArrayList;
import java.util.Observable;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Tower extends Observable {

	private int towerID;
	private ImageView image;
	private Circle movingPower;
	private TranslateTransition movement;

	private Color[] powerColor;
	private double[] powerSpeed;

	private int col;
	private int row;
	private ArrayList<Enemie> targets;

	private ChangeListener listener;

	public Tower(int num, int col, int row) {
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
		movement.setToX(584);
		movement.setCycleCount(Animation.INDEFINITE);
		movement.setNode(movingPower);
		movement.play();

		targets = new ArrayList<Enemie>();
		movementEvent();

	}

	public void movementEvent() {
		
		listener = new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				try {
					for (Enemie target : targets) {
						if (target.getBlood() > 0) {
							int targetX = getCol(target.getTransX()) + 1;
							int x = getCol(movingPower.getTranslateX()) + col;
							if (targetX < 9 && x < 9 && targetX == x) {
								target.remove();
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

	public void setTarget(ArrayList<Enemie> targets) {
		this.targets = targets;
	}
	
	public void addTarget(Enemie ene) {
		targets.add(ene);
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
		movingPower.translateXProperty().removeListener(listener);
	}

	public void stop() {
		movement.pause();

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