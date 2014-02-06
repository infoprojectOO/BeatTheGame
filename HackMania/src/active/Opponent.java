package active;

import items.Circuitry;
import items.Throwable;
import mapcomp.IMapComponent;
import mapcomp.MapComponent;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import control.Dynamics;

import android.graphics.Point;
import standards.Arrow;
import standards.Axis;
import standards.LivingState;
import substrate.Collision;
import substrate.Contact;

public class Opponent extends Movable {
	private boolean grounded;

	public Opponent(Vector2 coord) {
		super(coord);
		this.box = new Rectangle(coord.x,coord.y,1,1);
		this.state = LivingState.WALK;
		vel.set(5, 0);
	}

	@Override
	public float getBreadth(Axis axis) {
		return 1;
	}

	@Override
	public void move(float delta) {
		statetime += delta;
		vel.add(acc.cpy().scl(delta));
		Vector2 dl = vel.cpy().scl(delta);
		Rectangle foresee = new Rectangle(this.box);
		foresee.setPosition(pos.cpy().add(dl));
		Contact contact = Collision.detect(foresee,vel,this);
		this.grounded = contact.down();
		this.state = LivingState.WALK;
		if(!grounded) {
//			this.state = LivingState.AIR;
		} if(!contact.onX()&&!contact.onY()) {
			pos.set(foresee.x,foresee.y);
			this.box.setPosition(pos);
		} else if(contact.onX()!=contact.onY()) {
			this.pos.set(foresee.x,foresee.y);
			this.box.setPosition(pos);
		} if(contact.onX()) {
			vel.x = -vel.x;
			if(vel.x<0) {
				this.arrow = Arrow.LEFT;
			} else {
				this.arrow = Arrow.RIGHT;
			}
		} if(contact.onY()) {
			vel.y = 0;
		} if (vel.x==0 && vel.y==0) {
//			this.state = LivingState.IDLE;
		} for(IMapComponent imc : contact.getHit()) {
			if(imc instanceof Awakener) {
				System.out.println(imc);
				((Awakener) imc).hurt(Circuitry.LIGHT);
			}
		}
		
	}
	
	@Override
	public void hit(Throwable bullet) {
		bullet.destroy();
		Collision.destroy(this);
		Dynamics.destroy(this);
	}
	
	@Override
	public void setCoord(Vector2 coord) {
		this.pos.set(coord);
		this.box.setPosition(coord);
	}

	@Override
	public String getID() {
		return "evil";
	}
	
	@Override
	public void awaken(Awakener awakener) {
		awakener.hurt(Circuitry.LIGHT);
	}
	
	@Override
	public Opponent copy() {
		return new Opponent(new Vector2());
	}

}
