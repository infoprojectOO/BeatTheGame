package active;

import mapcomp.Entity;
import mapcomp.IMapComponent;
import mapcomp.MapComponent;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import standards.Access;
import standards.Arrow;
import standards.LivingState;
import standards.Prior;
import utilities.Speed;

public abstract class Movable extends MapComponent {
	private int width,height;
	protected Rectangle box = new Rectangle();
	protected Speed vel = new Speed();
	protected Vector2 acc = new Vector2();
	protected Vector2 pos = new Vector2();
	protected Arrow arrow;
	protected LivingState state;
	protected float statetime;
	private Access access;
	private Entity entity = new Entity(0, 1);
	
	public Movable(Vector2 coord) {
		this.pos = coord;
		this.arrow = Arrow.LEFT;
		this.access = new Access(Access.Allowance.CHARACTER);
		super.setPrior(Prior.HIGH);
	}

	public abstract void move(float delta);
	
	@Override
	public Vector2 getCoord() {
		return pos;
	}
	
	@Override
	public void setCoord(Vector2 coord) {
		this.pos.set(coord);
		this.box.setPosition(coord);
	}
	
	public Vector2 getAcceleration() {
		return acc;
	}
	
	@Override
	public Rectangle getBox() {
		return box;
	}
	
	public void setArrow(Arrow arr) {
		this.arrow = arr;
	}
	
	public Arrow getArrow() {
		return arrow;
	}
	
	public void setState(LivingState state) {
		this.state = state;
	}
	
	public LivingState getState() {
		return state;
	}

	public float getStateTime() {
		return statetime;
	}
	
	@Override
	public String getRootFolder() {
		return "animations";
	}
	
	public Access getAccess() {
		return access;
	}
	
	@Override
	public Entity getEntity() {
		return entity;
	}

}
