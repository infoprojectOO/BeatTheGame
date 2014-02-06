package active;

import mapcomp.DynamicEntity;
import mapcomp.Entity;
import mapcomp.IMapComponent;
import mapcomp.MapComponent;
import items.Attribute;
import items.Bullet;
import items.Circuitry;
import items.Electricity;
import items.Item;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pools;

import control.Observer;

import android.R.bool;
import android.graphics.Point;
import android.graphics.Rect;
import standards.Arrow;
import standards.Axis;
import standards.LivingState;
import standards.Prior;
import substrate.Collision;
import substrate.Contact;
import substrate.Zoom;
import utilities.Speed;

public class Gamer extends Movable implements Awakener {
	public static final float speed = 5f;
	public static float speedinc = 15f;
	public static float speeddec = 20f;
	private Electricity power;
	private Circuitry chipset;
	private boolean pulled;
	private boolean grounded;
	public static float jump_impulse = 7f;
	private Observer observer;
	private Vector2 emptyspace = new Vector2(0.5f,0);
	private DynamicEntity entity;

	public Gamer(Vector2 pos) {
		super(pos);
		this.chipset = new Circuitry();
		this.power = new Electricity();
		this.entity = new DynamicEntity(0, 0, DynamicEntity.EntityType.LIGHTWEIGHT);
		speeddec = entity.getDecrease();
		speedinc = entity.getIncrease();
		this.box = new Rectangle(pos.x+(1.5f*emptyspace.x/2), pos.y+(1.5f*emptyspace.y/2), 1.5f*(1-emptyspace.x), 1.5f*(1-emptyspace.y));
		this.vel.setLimits(-speed,speed);
		this.state = LivingState.IDLE;
		this.arrow = Arrow.LEFT;
	}
	
	@Override
	public void setCoord(Vector2 coord) {
		this.pos = coord;
		this.box.setPosition(coord.x+(1.5f*emptyspace.x/2), coord.y+(1.5f*emptyspace.y/2));
	}

	@Override
	public float getBreadth(Axis axis) {
		return 1.5f;
	}

	public Vector2 getVelocity() {
		return this.vel;
	}
	
	@Override
	public void move(float delta) {
		statetime += delta;
		vel.add(acc.cpy().scl(delta));
//		System.out.println(vel);
		Vector2 dl = vel.cpy().scl(delta);
		Rectangle foresee = new Rectangle(this.box);
		foresee.x += dl.x;
		foresee.y += dl.y;
		Contact contact = Collision.detect(foresee,vel,this);
		this.grounded = contact.down();
		if(!grounded) {
			this.state = LivingState.AIR;
		} pos.set(foresee.x-(emptyspace.x*getBreadth(Axis.X))/2,foresee.y-(emptyspace.y*getBreadth(Axis.Y)/2));
		this.box.setPosition(foresee.x,foresee.y);
		this.observer.update();
		interact(contact);
		
		
	}

	private void interact(Contact contact) {
		if(contact.onX()) {
			vel.x = 0;
			Entity side = contact.get(this.arrow);
			if(side!=null) {
				this.vel.friction(Axis.Y,side.getFriction(Axis.Y));
			} 
		} else {
			this.vel.friction(Axis.Y,false);
		} if(contact.onY()) {
			vel.y = 0;
			Entity support = contact.get(Arrow.DOWN);
			if(support!=null) {
				this.vel.friction(Axis.X, support.getFriction(Axis.X));
			}
		} else {
			this.vel.friction(Axis.X, false);
		} if(!pulled && vel.x==0) {
			acc.x = 0;
		} if (vel.x==0 && vel.y==0) {
			this.state = LivingState.IDLE;
		} if(!pulled) {
			this.state = LivingState.IDLE;
		}
		for(IMapComponent imc : contact.getHit()) {
			imc.awaken(this);
			Entity body = imc.getEntity();
			if(body!=null&&grounded) {
				jump_impulse = entity.getImpulse()*body.getRestitution();			
			} else {
				jump_impulse = entity.getImpulse();
			}
		}
}

	public void pull(boolean b) {
		this.pulled = b;
		vel.setLimits(-speed, speed);
		if(!pulled) {
			if(vel.x>0) {
				vel.setLimits(0, speed);
			} else if(vel.x<0) {
				vel.setLimits(-speed, 0);
			}
		}
	}

//	public Arrow getArrow() {
//		return this.arrow;
//	}
	
	public void shoot() {
		Vector2 center = this.box.getCenter(new Vector2());
		if(arrow==Arrow.LEFT) {
			Pools.obtain(Bullet.class).setCoordDir(center.add(new Vector2(this.getBreadth(Axis.X)/2f*arrow.getDir()-0.5f,0)),arrow, true);
		} else {
			Pools.obtain(Bullet.class).setCoordDir(center.add(new Vector2(this.getBreadth(Axis.X)/2f*arrow.getDir(),0)),arrow,true);
		}
//		
	}

	public boolean isGrounded() {
		return grounded;
	}

	public void addObserver(Observer view) {
		this.observer = view;		
	}

	@Override
	public String getID() {
		return "gamer";
	}

	public Circuitry getCircuitry() {
		return chipset;
	}

	public Electricity getPower() {
		return power;
	}

	public Attribute[] getAttributes() {
		Attribute[] attr =  {power,chipset};
		return attr;
	}

	@Override
	public void take(Item item) {
		if(item.getID()=="chip") {
			chipset.consume(item.getAmount());
		} else {
			power.consume(item.getAmount());
		}
		
	}

	@Override
	public void hurt(int damage) {
		this.chipset.consume(-damage);		
	}
	
	@Override
	public Gamer copy() {
		return this;
	}

}
