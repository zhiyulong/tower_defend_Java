import javafx.geometry.Bounds;
import javafx.scene.shape.Circle;

public class Bullet extends Circle {
	
	public double getX() {
		Bounds coordinates = this.localToScene(this.getBoundsInLocal());
		
		return coordinates.getMinX();
	}
	
	public double getY() {
		Bounds coordinates = this.localToScene(this.getBoundsInLocal());
		
		return coordinates.getMinY();
	}

}
