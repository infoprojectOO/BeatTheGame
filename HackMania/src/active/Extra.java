package active;

import mapcomp.IMapComponent;
import mapcomp.MapComponent;

import com.badlogic.gdx.math.Vector2;

import android.graphics.Point;
import standards.Axis;

public class Extra extends Movable {

	public Extra(Vector2 coord) {
		super(coord);
	}

	@Override
	public float getBreadth(Axis axis) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector2 getCoord() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void move(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector2 getAcceleration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Extra copy() {
		return new Extra(new Vector2());
	}

}
