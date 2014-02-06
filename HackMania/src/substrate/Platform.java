package substrate;

import geometry.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mapcomp.Entity;
import mapcomp.IMapComponent;
import mapcomp.MapComponent;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import android.graphics.Point;

import standards.Access;
import standards.Arrow;
import standards.Axis;
import standards.Prior;
import utilities.IParameter;
import utilities.IProperty;
import utilities.Parameter;
import utilities.Parameter.DefParameter;
import utilities.Property;
import utilities.Property.DefProperty;

public class Platform extends MapComponent {
	private static final long serialVersionUID = -1143485379303393353L;
	private int xex,yex, thickness = 1;
	private Arrow transallowance = null;
	private Map<Axis, Integer> laydir = new HashMap<Axis,Integer>(2);
	private int length;
	private static Entity entity = new Entity(0.2f, 0.3f);

	public Platform(Point dir) {
		super(Vector2.Zero);
		this.xex = dir.x;
		this.yex = dir.y;
	}
	
	public Platform(Point dir, Arrow trans) {
		super(Vector2.Zero);
		this.xex = dir.x;
		this.yex = dir.y;
		this.transallowance = trans;
	}
	
	public Platform(Vector2 coord, int l, int t) {
		super(coord, new Rectangle(coord.x, coord.y, l, t));
		super.setPrior(Prior.AVERAGE);
		this.length = l;
		
	}

	@Override
	public float getBreadth(Axis axis) {
		if(axis==Axis.X){
			return Float.valueOf(length);			
		} else {
			return 1f;
		}
	}

	@Override
	public double getYRatio() {
		return Math.min(1.0, (double) yex/xex);
	}

	@Override
	public double getXRatio() {
		return Math.min(1.0, (double) xex/yex);
	}

	@Override
	public String getID() {
		return "platform";
	}
	
	@Override
	public boolean allowAccess(IMapComponent visitor) {
		return visitor.getCoord().y<(this.box.y+this.box.height);
	}
	
	public void setLength(int length) {
		this.length = length;
		this.box.setWidth(length);
	}
	
	public int getLength() {
		return length;
	}

	@Override
	public Entity getEntity() {
		return entity ;
	}

	@Override
	public Platform copy() {
		return new Platform(new Vector2(), this.length, this.thickness);
	}
	
	@Override
	public List<IProperty> getProperties() {
		List<IProperty> list = super.getProperties();
		IParameter length = new Parameter(DefParameter.Length);
		length.setValidation(Property.methods.get("setLength"));
		list.add(length);
		return list;
	}

}
