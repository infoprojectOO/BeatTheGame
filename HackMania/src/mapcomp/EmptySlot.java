package mapcomp;


import com.badlogic.gdx.math.Vector2;

import standards.Axis;

public class EmptySlot extends MapComponent implements IMapComponent {
	private static final long serialVersionUID = 1L;

	public EmptySlot() {
		super(Vector2.Zero);
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
	public Vector2 getCoord() {
		return null;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmptySlot copy() {
		return this;
	}

}
