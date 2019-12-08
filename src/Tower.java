import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TouchPoint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Tower extends Observable{
	
	private int towerID;
	private ImageView image;
	private Circle movingPower;
	private TranslateTransition movement;
	
	private Color[] powerColor = new Color[]{Color.RED, Color.BLUE, Color.DEEPPINK, 
								Color.YELLOW, Color.GREEN, Color.ORANGE};
	private double[] powerSpeed = new double[]{2.5, 2, 1.4, 1, 0.8, 0.4};
	
	private int col;
	private ArrayList<Enemie> targets;
	
	private boolean removed;
	
	public Tower(int num, int col) {
		this.col = col;
		towerID = num;
		image = new ImageView(new Image("./images/tower"+num+".png"));

		// tower attack visualization
		movingPower = new Circle();
		movingPower.setFill(powerColor[num-1]);
		movingPower.setRadius(8);
				
		movement = new TranslateTransition();
		movement.setDuration(Duration.seconds(powerSpeed[num-1]));
		movement.setToX(584);
		movement.setCycleCount(Animation.INDEFINITE);
		movement.setNode(movingPower);
		movement.play();
		
		targets = new ArrayList<Enemie>();
		removed = false;
		movementEvent();
		
	}
	
	public void movementEvent() {
		
		movingPower.translateXProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				if (!removed) {
					for (Enemie target: targets) {
						if (target.getBlood() > 0) {
							int targetX = getCol(target.getTransX()) + 1;
							int x = getCol(movingPower.getTranslateX()) + col;
							if (targetX < 9 && x <9 && targetX==x) {
								target.remove();
								
								setChanged();
						  		notifyObservers();
							}
						}
					}
				}
			}
		});
		
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
		removed = true;
		
		movement.stop();
		image.setVisible(false);
		movingPower.setVisible(false);
	}

	public void stop() {
		movement.stop();
		
	}
	public void start() {
		movement.play();
	}
	
}
