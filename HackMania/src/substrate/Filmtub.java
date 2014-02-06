package substrate;

import items.Book;
import items.Circuitry;
import items.Electricity;
import items.ItemShell;
import items.Throwable;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import mapcomp.Block;
import mapcomp.IMapComponent;
import mapcomp.Scenery;
import mapcomp.Void;

import active.Movable;
import active.Opponent;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

import control.Dynamics;

import standards.Arrow;
import standards.Axis;
import utilities.ArrayPrior;
import utilities.GridPoint;
import utilities.Priorator;
import geometry.FrontLine;

public class Filmtub implements Serializable {
	private static final long serialVersionUID = 757058661728658928L;
	public final static int sweepness = 10;
	private final SortedMap<Integer, TreeMap<Integer, Set<IMapComponent>>> map 
	= new TreeMap<Integer,TreeMap<Integer, Set<IMapComponent>>>();
	private Map<IMapComponent,Vector2> roots = new HashMap<IMapComponent,Vector2>();
	private Set<Movable> mobiles = new HashSet<Movable>();
	private Set<Throwable> missiles = new HashSet<Throwable>();
	public int height;
	public int width;

	public Filmtub(int width, int height) {
		if ((width | height) <= 0) {
			throw new RuntimeException("Negative Bounds");
		}
		else {
			this.height = height;
			this.width = width;
			for (int i=0;i<width;i++) {
				this.map.put(i,new TreeMap<Integer, Set<IMapComponent>>());
			}
			demobuild(true);
		}
	}
	
	public Filmtub(int width, int height, boolean b) {
		if ((width | height) <= 0) {
			throw new RuntimeException("Negative Bounds");
		}
		else {
			this.height = height;
			this.width = width;
			for (int i=0;i<width;i++) {
				this.map.put(i,new TreeMap<Integer, Set<IMapComponent>>());
			}
			demobuild(b);
		}
	}

	private void demobuild(boolean playingstate) {
		for(int i=0;i<width;i++) {
			put(new Block(new Vector2(i,0f)), i, 0);
		}
		put(new Block(new Vector2(5f, 1f)), 5, 1);
		put(new Block(new Vector2(5f, 2f)), 5, 2);
		put(new Block(new Vector2(5f, 3f)), 5, 3);
		put(new Scenery(Scenery.Type.TREE,new Vector2(7f,1f),3f,4f),7,1);
		put(new Scenery(Scenery.Type.GRASS,new Vector2(5,4),0.5f,0.5f),5,4);
		put(new Scenery(Scenery.Type.ROCK,new Vector2(0,1),1,1),0,1);
		put(new ItemShell(new Vector2(5,4), new Electricity.Holder(10)),5,4);
		put(new ItemShell(new Vector2(10,5), new Circuitry.Holder(10)),10,5);
		put(new ItemShell(new Vector2(16,5), new Book()),16,5);
		put(new Platform(new Vector2(12,4),7,1),12,4);
		Movable opp = new Opponent(new Vector2(8f,2f));
		put(opp);
		if(playingstate) {
			Dynamics.addMovable(opp);
		}
		
	}
	
	public boolean within(float w, float h) {
		return (w<=(float) this.width && h<=(float) this.height && w>=0f && h>=0f);
	}

	public boolean within(int w,int h) {
		return (w<this.width && h<this.height && w>=0 && h>=0);
	}
	
	public boolean fitsin(IMapComponent piece) {
		Vector2 pos = piece.getCoord();
		float px = piece.getBreadth(Axis.X);
		float py = piece.getBreadth(Axis.Y);
		return (within(pos.x,pos.y)&&within(pos.x+px,pos.y+py));
	}
	
	public Map<IMapComponent,Vector2> getRoots() {
		return roots;
	}
	
	public boolean isEmpty (int w, int h) {
		if (this.map.get(w).get(h)==null) {
			return true;
		} else {
			return false;
		}
	}
	
	public Set<IMapComponent> get(int w, int h) {
		Set<IMapComponent> res = new HashSet<IMapComponent>(0);
		try { 
			if (!isEmpty(w,h)) {
				res = this.map.get(w).get(h);
			}
		} catch (Exception e) {}
		return res;
	}
	
	public void put(Movable im) {
		this.mobiles.add(im);
	}
	
	public void put(IMapComponent piece, int w, int h) {
		int pw = MathUtils.ceil(piece.getBreadth(Axis.X));
		int ph = MathUtils.ceil(piece.getBreadth(Axis.Y));
		if (within(w,h) && within(w+pw-1,h+ph-1)) {
			this.roots.put(piece,new Vector2(w,h));
			for (int i=0;i<pw;i++) {
				for (int j=0;j<ph;j++) {
					if(isEmpty(w+i,h+j)) {
						Set<IMapComponent> ap = new HashSet<IMapComponent>(5);
						ap.add(piece);
						this.map.get(w+i).put(h+j,ap);
					} else {
						this.map.get(w+i).get(h+j).add(piece);
					}
				}
			}
		} else { throw new RuntimeException("Out of Bounds");
		}
	}
	
	public void put(IMapComponent piece) {
		float pw = piece.getBreadth(Axis.X);
		float ph = piece.getBreadth(Axis.Y);
		Vector2 pos = piece.getCoord();
		if(within(pos.x,pos.y)&&within(pos.x+pw,pos.y+ph)) {
			this.roots.put(piece,new Vector2(pos));
			for (int w=MathUtils.floor(pos.x);w<pw+pos.x;w++) {
				for (int h=MathUtils.floor(pos.y);h<ph+pos.y;h++) {
					if(isEmpty(w,h)) {
						Set<IMapComponent> ap = new HashSet<IMapComponent>(5);
						ap.add(piece);
						this.map.get(w).put(h,ap);
					} else {
						this.map.get(w).get(h).add(piece);
					}
				}
			}
		} else {throw new RuntimeException("Out of Bounds");}
	}
	
	public void remove(IMapComponent piece) {
		if (this.roots.containsKey(piece)) {
			Vector2 pos = this.roots.get(piece);
			float pw = piece.getBreadth(Axis.X);
			float ph = piece.getBreadth(Axis.Y);
			for (int w=MathUtils.floor(pos.x);w<pos.x+pw;w++) {
				for (int h=MathUtils.floor(pos.y);h<pos.y+ph;h++) {
					this.map.get(w).get(h).remove(piece);
					if(this.map.get(w).get(h).size()==0) {
						this.map.get(w).remove(h);
					}
				}
			}
			this.roots.remove(piece);
		} else {
			this.mobiles.remove(piece);
		}
	}
	
	public void remove(Movable animated) {
		this.mobiles.remove(animated);
	}
	
	public void remove(Throwable missile) {
		this.missiles.remove(missile);
	}

	public Array<IMapComponent> getObjects(Rectangle rect, IMapComponent caller) {
		Array<IMapComponent> objects = new Array<IMapComponent>();
		for(int x = MathUtils.floor(rect.x);x<=MathUtils.floor(rect.x+rect.width);x++){
			for(int y = MathUtils.floor(rect.y);y<=MathUtils.floor(rect.y+rect.height);y++) {
				if(!within(x,y)) {
					Void v = Pools.obtain(Void.class);
					v.setPosition(x,y);
					objects.add(v);
				} else if(this.map.get(x).get(y)!=null) {
					for(IMapComponent imc : this.map.get(x).get(y)) {
//						System.out.println(imc);
						if(!objects.contains(imc, false)&&imc!=caller&&!(imc instanceof Scenery)) {
							objects.add(imc);
						}
					}
				}
			}
		}
		for(Movable m : mobiles) {
			if(m!=caller) {
//				System.out.println(String.format("caller :  %s added : %s ",caller,m));
				objects.add(m);
			} 
		} return objects;		
	}

	public Collection<? extends IMapComponent> getMobiles() {
		return mobiles;
	}

	public void add(Throwable bullet) {
		this.missiles.add(bullet);		
	}

	public Collection<? extends IMapComponent> getThrowables() {
		return missiles;
	}

	public Queue<IMapComponent> hit(Vector2 vector) {
		PriorityQueue<IMapComponent> list = new PriorityQueue<IMapComponent>(5,new Priorator());
		for(Movable mobile : mobiles) {
			if(mobile.getBox().contains(vector)) {
				list.add(mobile);
			}
		} for(IMapComponent imc : this.get((int)Math.floor(vector.x),(int)Math.floor(vector.y))) {
			if(imc.getBox().contains(vector)) {
				list.add(imc);
			}
		} return list;
	}

	public Movable hitMovable(Vector2 vector) {
		for(Movable mobile : mobiles) {
			if(mobile.getBox().contains(vector)) {
				return mobile;
			}
		} return null;
	}

	public void extend(Axis axis, int extent) {
		if(axis == Axis.X) {
			this.width += extent;
			for(int i=width-extent;i<width;i++) {
				System.out.println(i);
				this.map.put(i,new TreeMap<Integer, Set<IMapComponent>>());
			} for(int i=width; i<width-extent;i++) {
				System.out.println(i);
				for(Set<IMapComponent> set : this.map.get(i).values()) {
					for(IMapComponent imc : set) {
						this.roots.remove(imc);
					}
				}
				this.map.remove(i);
			}
		} else if(axis == Axis.Y) {
			this.height += extent;
		}
		
	}
	
//	public SortedMap<Integer, TreeMap<Integer, ArrayPrior<IMapComponent>>> zoom(Rectangle rect){
//		SortedMap<Integer, TreeMap<Integer, ArrayPrior<IMapComponent>>> pack 
//		= new TreeMap<Integer,TreeMap<Integer, ArrayPrior<IMapComponent>>>();
//		if(within(rect.right,rect.top)&&/*within(rect.left,rect.top)
//				&&within(rect.right,rect.bottom)&&*/within(rect.left,rect.bottom)){
//			for(int i = rect.right; i < rect.left;i++) {
//				pack.put(i,(TreeMap<Integer, ArrayPrior<IMapComponent>>)
//						map.get(i).subMap(rect.bottom, rect.top));
//			}
//		} else {
//			throw new RuntimeException("Out of Bounds");
//		} return pack;
//	}
	
	/*public  SortedMap<Integer, TreeMap<Integer, ArrayPrior<IMapComponent>>> sweep(FrontLine line) {
		int h = Filmtub.sweepness*line.getDir();
		int w = Filmtub.sweepness*line.getDir();
		if(line.getAxis() == Axis.X) {
			w = line.getLength();
		} else {
			h = line.getLength();
		}
//		SortedMap<Integer, TreeMap<Integer, IMapComponent>> pack 
//		= new TreeMap<Integer,TreeMap<Integer,IMapComponent>>();
//		if(within(line.getOrgX(),line.getOrgY()+h)&&
//				within(line.getOrgX()+w,line.getOrgY())){
//			for(int i = line.getOrgX();i<= line.getOrgX()+w;i++) {
//				pack.put(i,(TreeMap<Integer, IMapComponent>)
//						map.get(i).subMap(line.getOrgY(), line.getOrgY() + h));
//			}
//		} else {
//			throw new RuntimeException("Out of Bounds");
//		}
		return zoom(new Rectangle(line.getOrgX(),line.getOrgY()+h,line.getOrgX()+w,line.getOrgY()));
	}
*/
}
