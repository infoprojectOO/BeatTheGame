package items;

import standards.Arrow;

import com.badlogic.gdx.math.Vector2;

import mapcomp.IMapComponent;

public interface Throwable extends IMapComponent {
	
	void move(float delta);
	void destroy();
	void setCoordDir(Vector2 coord, Arrow dir, boolean ini);
	Arrow getArrow();

}
