package mainbuilder;

import java.util.HashSet;
import java.util.Set;

import mainmodel.StaticCollision;
import mapcomp.IMapComponent;

import active.Movable;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import control.Observer;
import standards.Axis;
import substrate.Filmtub;

public class MapAssistant {
	public static final String HIGHLIGHT = "highlight", SIZE = "size";
	private final OuterShell objectassistant;
	private MapComponentFactory factory;
	private Filmtub map;
	private Rectangle highlight;
	private Observer display;
	private String changecode;
	private Set<IMapComponent> selection;
	private StaticCollision collision;


	public MapAssistant() {
		this.highlight = new Rectangle();
		this.objectassistant = new OuterShell();
		this.selection = new HashSet<IMapComponent>();
		this.collision = new StaticCollision();
	}
	
	public void load(Filmtub map) {
		this.map = map;
		StaticCollision.setMap(map);
	}
	
	public void setFactory(MapComponentFactory factory) {
		this.factory = factory;
	}
	
	public void extend(Axis axis, int extent) {
		this.map.extend(axis, extent);
		this.changecode = MapAssistant.SIZE;
		this.display.update();
	}
	
	public void moveSelection(Vector2 distance) {
		this.highlight.x += distance.x;
		this.highlight.y += distance.y;
		this.changecode = MapAssistant.HIGHLIGHT;
		this.display.update();
	}
	
	public Rectangle getSelection() {
		return highlight;
	}
	
	public Set<IMapComponent> getSelected() {
		return selection;
	}
	
	public String getChangeCode() {
		return changecode;
	}
	
	public void setObserver(Observer o) {
		this.display = o;
	}
	
	public void select(Vector2 pos) {
		selection.clear();
		IMapComponent imc = this.map.hit(pos).peek();
		if(imc!=null) {
			this.highlight.set(imc.getBox());
			this.selection.add(imc);
		} else {
			this.highlight.setSize(00);
		}
		this.changecode = MapAssistant.HIGHLIGHT;
		this.display.update();
	}

	public void select(Rectangle zone) {
		selection.clear();
		this.changecode = MapAssistant.HIGHLIGHT;
		highlight.setSize(00);
		for(int x=MathUtils.floor(zone.x); x<=MathUtils.floor(zone.x+zone.width);x++) {
			for(int y=MathUtils.floor(zone.y); y<=MathUtils.floor(zone.y+zone.height);y++) {
				for(IMapComponent imc : this.map.get(x, y)) {
					if(zone.contains(imc.getBox())) {
						selection.add(imc);
						if(highlight.getWidth()==0) {
							highlight.set(imc.getBox());
						} else {
							highlight.merge(imc.getBox());
						}
					} 
				}
			}
		} for(IMapComponent m : this.map.getMobiles()) {
			if(zone.contains(m.getBox())) {
				selection.add(m);
				if(highlight.getWidth()==0) {
					highlight.set(m.getBox());
				} else {
					highlight.merge(m.getBox());
				}
			}
		} this.display.update();
	}

	

	public void setHighlight(Rectangle zone) {
		this.highlight.set(zone);
		this.changecode = MapAssistant.HIGHLIGHT;
		this.display.update();
	}

	public IMapComponent hit(Vector2 deletion) {
		IMapComponent[] array = this.map.hit(deletion).toArray(new IMapComponent[0]);
		if(array.length!=0) {
			return array[array.length-1];
		} else {
			return null;
		}
		
	}

	public void remove(IMapComponent hit) {
		this.changecode = null;
		this.map.remove(hit);
		this.display.update();		
	}

	public void loadPrototype() {
		try {
			this.objectassistant.load(factory.getPrototype());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public IMapComponent generateObject() {
		this.changecode = null;
		IMapComponent imc = objectassistant.retrieveProduct();
		if(this.collision.authorize(imc)) {
			if(imc instanceof Movable) {
				this.map.remove((Movable) imc);
				this.map.put((Movable) imc);
			} else {
				this.map.remove(imc);
				this.map.put(imc);
			}
		} else {
			this.map.remove(imc);
			imc = null;
		} this.display.update();
		return imc;		
	}

	public OuterShell getShell() {
		return objectassistant;
	}

	public void put(IMapComponent imc) {
		this.changecode = null;
		if(imc instanceof Movable) {
			this.map.put((Movable) imc);
		} else {
			this.map.put(imc);
		} this.display.update();		
	}
	
}
