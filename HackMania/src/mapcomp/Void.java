package mapcomp;

import standards.Axis;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Void extends MapComponent {
	private static final long serialVersionUID = 4004029874360655567L;

	public Void() {
		super();
	}

	public Void(Rectangle rect) {
		super(rect);
	}
	
	public void setPosition(float x, float y) {
		super.pos = new Vector2(x, y);
		super.box = new Rectangle(x, y, 1, 1);
	}

	@Override
	public float getBreadth(Axis axis) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int prior(IMapComponent rival) {
		return 0;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void copy() {
		return this;
	}

}
