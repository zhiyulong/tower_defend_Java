import java.util.Observable;

import javax.sound.sampled.LineListener;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Enemie extends Observable{
	
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
		
		movementEvent();
	}
	
	public void movementEvent() {
		
		// if arrived home
		movement.setOnFinished(new EventHandler<ActionEvent>() {
		      @Override 
		      public void handle(ActionEvent t) {
		    	 if (blood != 0) {
		    		 setChanged();
			  		 notifyObservers(enemieID+"enemies arrive!");
		    	 }
		      }
		});
	}
	
	public void remove() {
		movement.stop();
		image.setVisible(false);
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
	
	public TranslateTransition getMovement() {
		return movement;
	}
	
	public void subBlood(int num) {
		blood -= num;
	}
	
}
