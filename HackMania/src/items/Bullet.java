package items;

import java.util.List;

import mapcomp.Entity;
import mapcomp.IMapComponent;
import standards.Access;
import standards.Arrow;
import standards.Axis;
import standards.LivingState;
import standards.Prior;
import substrate.Collision;
import substrate.Contact;
import utilities.IProperty;

import active.Awakener;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

import control.Dynamics;

public class Bullet implements Throwable {
	public static final float MAXSPEED = 7;
	private Vector2 pos;
	private float speed;
	private Rectangle box;
	private Prior priority;
	private Arrow arrow;
	private boolean forgotten;

	public Bullet() {
		this.priority = Prior.ABSOLUTE;
		this.box = new Rectangle(0,0,0.5f,0.25f);
	}

	public Bullet(Vector2 pos, Arrow dir) {
		this.priority = Prior.ABSOLUTE;
		this.pos = pos;
		this.arrow = dir;
		this.box = new Rectangle(pos.x,pos.y,0.5f,0.25f);
		this.speed = MAXSPEED * dir.getDir();
	}

	@Override
	public float getBreadth(Axis axis) {
		if(axis==Axis.X) {
			return 0.5f;
		} else {
			return 0.25f;
		}
	}

	@Override
	public int prior(IMapComponent rival) {
		return this.priority.above(rival.getPrior());
	}

	@Override
	public double getYRatio() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getXRatio() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector2 getCoord() {
		return pos;
	}

	@Override
	public Rectangle getBox() {
		return this.box;
	}

	@Override
	public void move(float delta) {
		this.pos.add(speed*delta,0);
		this.box.setPosition(pos);
		for(IMapComponent crash : Collision.hit(this)) {
			crash.hit(this);
		}
//		float dl = speed*delta;
//		Rectangle foresee = new Rectangle(this.box);
//		foresee.setPosition(pos);
//		foresee.setX(pos.x+dl);
//		Contact contact = Collision.detect(foresee,new Vector2(),this);
//		if(!contact.onX()&&!contact.onY()) {
//			pos.set(foresee.x,foresee.y);
//			this.box.setPosition(pos);
//		} else if(contact.onX()!=contact.onY()) {
//			this.pos.set(foresee.x,foresee.y);
//			this.box.setPosition(pos);
//		}
	}
	
	private void create() {
		forgotten = false;
		Collision.addThrowable(this);
		Dynamics.addThrowable(this);
	}

	@Override
	public void destroy() {
		if(!forgotten) {
			forgotten = true;
			Collision.destroy(this);
			Dynamics.destroy(this);
			Pools.get(Bullet.class).free(this);
		}
	}

	@Override
	public String getID() {
		return "bullet";
	}

	@Override
	public void hit(Throwable bullet) {}

	@Override
	public Prior getPrior() {
		return priority;
	}

	@Override
	public void setPrior(Prior p) {
		this.priority = p;		
	}

	@Override
	public String getRootFolder() {
		return "items";
	}

	@Override
	public void awaken(Awakener awakener) {}

	@Override
	public boolean allowAccess(IMapComponent visitor) {
		return true;
	}

	@Override
	public Access getAccess() {
		return null;
	}

	@Override
	public void setCoordDir(Vector2 coord, Arrow dir, boolean ini) {
		this.pos = coord;
		this.box.setPosition(coord);
		this.arrow = dir;
		this.speed = MAXSPEED * arrow.getDir();
		if(ini) {
			create();
		}
		
	}

	@Override
	public Arrow getArrow() {
		return arrow;
	}

	@Override
	public Entity getEntity() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void setCoord(Vector2 coord) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public Bullet copy() {
		return new Bullet();
	}

	@Override
	public void setCoord(Vector2 coord) {
		this.pos.set(coord);
		this.box.setPosition(coord);		
	}

	@Override
	public List<IProperty> getProperties() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBox(Rectangle rect) {
		// TODO Auto-generated method stub
		
	}

}
