package substrate;

import geometry.FrontLine;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

import mapcomp.EmptySlot;
import mapcomp.IMapComponent;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import control.Observer;

import standards.Arrow;
import standards.Axis;
import utilities.ArrayPrior;
import utilities.GridPoint;
import utilities.Priorator;

import active.Gamer;
import active.Movable;
import android.graphics.Point;
import android.graphics.Rect;

public class Zoom implements Observer {
	public final int extwidth = 12, extheight = 10;
	public final int intwidth = 10,intheight = 8;
	private Vector2 intorigin;
	private GridPoint2 extorigin;
	private SortedMap<Integer, TreeMap<Integer,  ArrayPrior<IMapComponent>>> map;
	private Queue<IMapComponent> roots = new PriorityQueue<IMapComponent>(80,new Priorator());
//	private SortedSet<IMapComponent> roots = new TreeSet<IMapComponent>(new Priorator());
	private Filmtub backzoom;
	private Movable subject;
	
	public Zoom(Filmtub target, GridPoint2 org) {
		if(target.within(org.x, org.y)&&target.within(org.x+intwidth-1,org.y+intheight-1)) {
			backzoom = target;
			intorigin = new Vector2(org.x,org.y);
			extorigin = new GridPoint2(org.x-(extwidth-intwidth)/2,org.y-(extheight-intheight)/2);
			retrieveObjects();
//			map = target.zoom(new Rect(extorigin.x,extorigin.y+this.extheight,
//						extorigin.x+this.extwidth,extorigin.y));
//			pickRoots(backzoom.getRoots());
		} else {
			throw new RuntimeException("Zoom in void");
		}
	}
	
	private boolean within(GridPoint2 value) {
		return (value.x>=extorigin.x && value.x<extorigin.x+extwidth
				&& value.y>=extorigin.y && value.y<extorigin.y+extheight);
	}
	
	private boolean within(Vector2 value) {
		return (value.x>=extorigin.x && value.x<extorigin.x+extwidth
				&& value.y>=extorigin.y && value.y<extorigin.y+extheight);
	}
	
//	private void pickRoots(Map<IMapComponent,GridPoint2> roots) {
//		for(Entry<IMapComponent,GridPoint2> root : roots.entrySet()) {
//			if(within(root.getValue())) {
//				this.roots.put(root.getKey(), root.getValue());
//			}
//		}
//	}
	
	public Queue<IMapComponent> getRoots() {
		return roots;
	}

//	public boolean isRoot(int w, int h) {
//		return (roots.containsValue(new GridPoint2(w,h)) && !isEmpty(w, h));
//	}
	
	public boolean isEmpty (int w, int h) {
		if (this.map.get(w).get(h)==null) {
			return true;
		} else {
			return false;
		}
	}
	
	public IMapComponent get(int w, int h) {
		IMapComponent res = new EmptySlot();
		try { 
			if (!isEmpty(w,h)) {
				res = this.map.get(w).get(h).getLast();
			}
		} catch (Exception e) {}
		return res;
	}
	
//	public void put(IMapComponent piece, int w, int h) {
//		int pw = (int) piece.getBreadth(Axis.X);
//		int ph = (int) piece.getBreadth(Axis.Y);
//		if (isEmpty(w,h) && isEmpty(w+pw-1,h+ph-1)) {
//			this.roots.put(piece,new GridPoint2(w,h));
//			for (int i=0;i<pw;i++) {
//				for (int j=0;j<ph;j++) {
//					if(isEmpty(w+i,h+j)) {
//						ArrayPrior<IMapComponent> ap = new ArrayPrior<IMapComponent>();
//						ap.add(piece);
//						this.map.get(w+i).put(h+j,ap);
//					} else {
//						this.map.get(w+i).get(h+j).add(piece);
//					}
//				}
//			}
//		} else { throw new RuntimeException("Out of Bounds");
//		}
//	}
	
//	public void remove(IMapComponent piece) {
//		if (this.roots.containsKey(piece)) {
//			GridPoint2 pos = this.roots.get(piece);
//			int pw = (int) piece.getBreadth(Axis.X);
//			int ph = (int) piece.getBreadth(Axis.Y);
//			for (int i=0;i<pw;i++) {
//				for (int j=0;j<ph;j++) {
//					this.map.get(pos.x+i).get(pos.y+j).remove(piece);
//					if(this.map.get(pos.x+i).get(pos.y+j).size()==0) {
//						this.map.get(pos.x+i).remove(pos.y+j);
//					}
//				}
//			}
//			this.roots.remove(piece);
//			this.backzoom.getRoots().remove(piece);
//		}
//	}
	
//	public boolean extendable(Arrow dir, int deepness) {
//		int dw = dir.getShifting()[0]*deepness;
//		int dh = dir.getShifting()[1]*deepness;
//		return (backzoom.within(intorigin.x + dw,intorigin.y + dh)&&
//				backzoom.within(intorigin.x + intwidth + dw,intorigin.y + dh));
//	}
	
	public void extend(Arrow dir) {
//		FrontLine line;
		/*if(dir.getAxis()==Axis.X) {
//			line = new FrontLine(dir,extorigin,extheight);
			this.extorigin.offset(backzoom.sweepness*dir.getDir(),0);			
		} else {
//			line = new FrontLine(dir,extorigin,extwidth);
			this.extorigin.offset(0,backzoom.sweepness*dir.getDir());
		}*/
		this.extorigin.set(extorigin.x+dir.getShifting()[0]*backzoom.sweepness, extorigin.y+dir.getShifting()[1]*backzoom.sweepness);
		this.map.clear();
//		this.map.putAll(backzoom.zoom(new Rect(extorigin.x,extorigin.y+this.extheight,
//					extorigin.x+this.extwidth,extorigin.y)));
	}
	
	public void shiftSight(Arrow dir1, int norm1, Arrow dir2, int norm2) {
		this.intorigin.set(intorigin.x+dir1.getShifting()[0]*norm1 + dir2.getShifting()[0]*norm2 ,
				intorigin.y+dir1.getShifting()[1]*norm1 + dir2.getShifting()[1]*norm2);
	}

	public void retrieveObjects() {
//		synchronized(roots) {
		roots.clear();
		roots.addAll(backzoom.getThrowables());
		roots.addAll(backzoom.getMobiles());
		for(int x=extorigin.x;x<extorigin.x+extwidth;x++) {
			for(int y=extorigin.y;y<extorigin.y+extheight;y++) {
				Set<IMapComponent> mcset = backzoom.get(x, y);
				for(IMapComponent mc : mcset) {
					if(!roots.contains(mc)) {
						roots.add(mc);
					}
				}
			}		
		}
//		}
	}

	public Vector2 getOrigin() {
		return intorigin;
	}

	@Override
	public void update() {
		recenter(this.subject.getCoord().cpy().sub(new Vector2(intwidth/2,intheight/2))); 
		retrieveObjects();
		
	}

	private boolean recenter(Vector2 coord) {
		intorigin.set(coord);
		if(intorigin.x<0) {
			intorigin.x = 0;
		} else if(intorigin.x>backzoom.width-intwidth) {
			intorigin.x = backzoom.width-intwidth;
		} if(intorigin.y<0) {
			intorigin.y = 0;
		} else if (intorigin.y>backzoom.height-intheight) {
			intorigin.y = backzoom.height-intheight;
		} GridPoint2 trash = new GridPoint2(extorigin);  
		extorigin.set(MathUtils.floor(intorigin.x)-(extwidth-intwidth)/2,MathUtils.floor(intorigin.y)-(extheight-intheight)/2);
		return (!(extorigin.x==trash.x&&extorigin.y==trash.y));
	}

	public void track(Movable tracked) {
		this.subject = tracked;
	}

}
