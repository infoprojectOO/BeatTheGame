package mapcomp;

import standards.Axis;
import standards.Prior;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Scenery extends MapComponent {
	private static final long serialVersionUID = -3673219496705700554L;
	private float width, height;
	private Type type;

	public static enum Type {
		GRASS,
		TREE,
		ROCK;
	}
	public Scenery() {
		// TODO Auto-generated constructor stub
	}

	public Scenery(Type type, Vector2 coord,float width, float height) {
		super(coord);
		super.box = new Rectangle(coord.x, coord.y, width, height);
		super.setPrior(Prior.NONE);
		this.type = type;
		this.width = width;
		this.height = height;
	}

	public Scenery(Type type, Rectangle rect) {
		super(rect.getPosition(null));
		this.type = type;
		this.width = rect.getWidth();
		this.height = rect.getHeight();
	}

	@Override
	public float getBreadth(Axis axis) {
		if(axis==Axis.X) {
			return width;
		} else {
			return height;
		}
	}

	@Override
	public String getID() {
		if(type==Type.TREE) return "tree_ground";
		else if(type==Type.GRASS) return "grass";
		else return "rock_ground";
	}
	
	@Override
	public String getRootFolder() {
		return "background";
	}

	@Override
	public Scenery copy() {
		return new Scenery(this.type,new Vector2(),this.width,this.height);
	}

}
