package substrate;

import java.util.HashMap;
import java.util.Map;

import mapcomp.Entity;
import mapcomp.IMapComponent;

import android.R.array;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import standards.Arrow;
import standards.Axis;

public class Contact {
	private Axis[] axis = new Axis[2];
	private float[][] depth = {{0,0},{0,0}};
	private Map<Arrow,Entity> shocktable = new HashMap<Arrow, Entity>();
	private Array<IMapComponent> contacts = new Array<IMapComponent>();

	public Contact(Axis x, Axis y) {
		axis[0] = x;
		axis[1] = y;
	}
	
	public Contact() {}

	public boolean onX() {
		return (axis[0]==Axis.X);
	}
	
	public boolean onY() {
		return (axis[1]==Axis.Y);
	}
	
	public boolean full() {
		return (onX()&&onY());
	}
	

	public Entity get(Arrow arrow) {
		return shocktable.get(arrow);		
	}
	
	public boolean down() {
		return depth[1][0] != 0;
	}
	
	public boolean left() {
		return depth[0][0] != 0;
	}
	
	public boolean right() {
		return depth[1][1] != 0;
	}

	public void merge(Contact contact) {
		if(contact.onX()) axis[0] = Axis.X;
		if(contact.onY()) axis[1] = Axis.Y;
	}

	public void onX(boolean b) {
		axis[0] = (b) ? Axis.X : null;
	}
	
	public void onY(boolean b) {
		axis[1] = (b) ? Axis.Y : null;
	}

	public void onX(boolean b, float f, Entity entity) {
		axis[0] = (b) ? Axis.X : null;
		if(f>0) {
			depth[1][1] = f;
			shocktable.put(Arrow.RIGHT, entity);
		} else {
			depth[0][0] = -f;
			shocktable.put(Arrow.LEFT, entity);
		}
	}

	public void onY(boolean b, float f, Entity entity) {
		axis[1] = (b) ? Axis.Y : null;
		if(f>0) {
			depth[0][1] = f;
			shocktable.put(Arrow.UP, entity);
		} else {
			depth[1][0] = -f;
			shocktable.put(Arrow.DOWN, entity);
		}
	}

	public void addHit(IMapComponent candidate) {
		this.contacts.add(candidate);		
	}
	
	public Array<IMapComponent> getHit() {
		return contacts;
	}

	
	

}
