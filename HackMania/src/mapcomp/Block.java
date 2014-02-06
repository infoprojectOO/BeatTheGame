package mapcomp;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import standards.Axis;
import standards.Prior;

public class Block extends MapComponent {
	private static final long serialVersionUID = 350493075921556962L;
	private int width,height;
	private float[] x={0.5f,0f},y={0f,1f};
	private Entity entity = new Entity(x,y);

	public Block(Vector2 coord) {
		super(coord, new Rectangle(coord.x, coord.y, 1f, 1f));
		super.setPrior(Prior.AVERAGE);
		this.width = 1;
		this.height = 1;
	
	}

	@Override
	public float getBreadth(Axis axis) {
		if(axis.equals(Axis.X)) {
			return width;
		} else {
			return height;
		}
	}

	@Override
	public String getID() {
		return "block";
	}
	
	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public Block copy() {
		return new Block(new Vector2());
	}

}
