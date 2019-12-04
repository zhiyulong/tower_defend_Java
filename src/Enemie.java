import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Enemie extends Node {
	
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
	
	public double getX() {
		Bounds coordinates = this.localToScene(this.getBoundsInLocal());
		
		return coordinates.getMinX();
	}
	
	public double getY() {
		Bounds coordinates = this.localToScene(this.getBoundsInLocal());
		
		return coordinates.getMinY();
	}


	@Override
	protected boolean impl_computeContains(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public BaseBounds impl_computeGeomBounds(BaseBounds arg0, BaseTransform arg1) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected NGNode impl_createPeer() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object impl_processMXNode(MXNodeAlgorithm arg0, MXNodeAlgorithmContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
