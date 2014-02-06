package mapcomp;

import java.util.ArrayList;
import java.util.List;

import items.Throwable;

import active.Awakener;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import standards.Access;
import standards.Axis;
import standards.Prior;
import utilities.IProperty;
import utilities.Property;
import utilities.Property.DefProperty;

public abstract class MapComponent implements IMapComponent {
	private static final long serialVersionUID = -5610496345710050559L;
	protected Vector2 pos;
	protected Rectangle box;
	private Prior priority;
	
	public MapComponent() {
		
	}
	
	public MapComponent(Vector2 coord) {
		this.pos = coord;
	}
	
	public MapComponent(Rectangle rect) {
		this.box = rect;
		this.pos = new Vector2(box.getX(),box.getY());
	}

	public MapComponent(Vector2 coord, Rectangle rect) {
		this.pos = coord;
		this.box = rect;
	}
	
	@Override
	public void setCoord(Vector2 coord) {
		this.pos.set(coord);
		this.box.setPosition(coord);
	}
	
	@Override
	public void setBox(Rectangle rect) {
//		this.box = rect;
	}

	@Override
	public abstract float getBreadth(Axis axis);

	@Override
	public int prior(IMapComponent rival) {
		return this.priority.above(rival.getPrior());
	}
	
	@Override
	public Prior getPrior() {
		return priority;
	}
	
	@Override
	public void setPrior(Prior p) {
		this.priority = p;
	}
	
	@Override
	public abstract String getID();
	
	@Override 
	public double getXRatio() {
		return 1.0;
	}

	@Override
	public double getYRatio() {
		return 1.0;
	}
	
//	@Override
//	public void setCoord(Vector2 coord) {
//		this.pos = coord;
//	}
	
	@Override
	public Vector2 getCoord() {
		return pos;
	}
	
	@Override
	public Rectangle getBox() {
		return box;
	}
	
	@Override
	public void hit(Throwable missile) {
		missile.destroy();
	}
	
	@Override
	public String getRootFolder() {
		return "mapcomp";
	}
	
	@Override
	public void awaken(Awakener awakener) {}
	
	@Override
	public boolean allowAccess(IMapComponent visitor) {
		return false;
	}
	
	@Override
	public Access getAccess() {
		return null;
	}
	
	@Override
	public Entity getEntity() {
		return null;
	}
	
	@Override
	public List<IProperty> getProperties() {
		List<IProperty> list = new ArrayList<IProperty>();
		IProperty position = new Property(DefProperty.POSITION,new Vector2());
		position.setValidation(Property.methods.get("setCoord"));
		IProperty box = new Property(DefProperty.BOX, new Rectangle(this.box));
		box.setValidation(Property.methods.get("setBox"));
		list.add(position);
		list.add(box);
		return list;		
	}
	
}
