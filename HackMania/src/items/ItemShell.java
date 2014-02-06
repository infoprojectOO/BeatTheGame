package items;

import java.util.Collection;

import mapcomp.IMapComponent;
import mapcomp.MapComponent;

import active.Awakener;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;

import standards.Access;
import standards.Axis;
import standards.Prior;
import substrate.Collision;

public class ItemShell extends MapComponent implements IMapComponent {
	private static final long serialVersionUID = -7947723606597014799L;
	private Item kernel;

	public ItemShell(Vector2 coord, Item item) {
		super(new Rectangle(coord.x, coord.y, 1, 1));
		super.setPrior(Prior.HIGH);
		this.kernel = item;
	}

	public ItemShell() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getBreadth(Axis axis) {
		if(axis.equals(Axis.X)) {
			return 1;
		} else {
			return 1;
		}
	}
	
	@Override
	public String getID() {
		return this.kernel.getID();
	}
	
	@Override
	public String getRootFolder() {
		return "items";
	}

	@Override
	public void awaken(Awakener awakener) {
		awakener.take(this.kernel);
		Collision.destroy(this);
//		Pools.free(this);
	}
	
	@Override
	public boolean allowAccess(IMapComponent visitor){
		return true;
	}

	@Override
	public ItemShell copy() {
		return new ItemShell(new Vector2(),this.kernel);
	}
	

}
