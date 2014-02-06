package mainbuilder;

import items.Circuitry;
import items.Electricity;
import items.ItemShell;
import items.Electricity.Holder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import substrate.Filmtub;
import substrate.Platform;

import com.badlogic.gdx.math.Vector2;

import active.Extra;
import active.Gamer;
import active.Movable;
import active.Opponent;

import mapcomp.Block;
import mapcomp.IMapComponent;
import mapcomp.IPrototype;
import mapcomp.Scenery;

public class MapComponentFactory {
	public final static Map<String,IPrototype<IMapComponent>> abstractproto = new HashMap<String,IPrototype<IMapComponent>>();
//	public final static Map<String,IPrototype<Movable>> movableproto = new HashMap<String,IPrototype<Movable>>();
//	private Movable mp;
	private IPrototype<IMapComponent> mcp;

	public MapComponentFactory() {
		abstractproto.put(Proto.BLOCK.name,(new Block(new Vector2())));
		abstractproto.put(Proto.PLATFORM.name,(new Platform(new Vector2(),1,1)));
		abstractproto.put(Proto.THUNDER.name,(new ItemShell(new Vector2(), new Electricity.Holder(0))));
		abstractproto.put(Proto.CHIPSET.name,(new ItemShell(new Vector2(), new Circuitry.Holder(0))));
		abstractproto.put(Proto.PLAYER.name,(new Gamer(new Vector2())));
		abstractproto.put(Proto.OPPONENT.name,(new Opponent(new Vector2())));
		abstractproto.put(Proto.EXTRA.name,(new Extra(new Vector2())));
		abstractproto.put(Proto.TREE.name, new Scenery(Scenery.Type.TREE,new Vector2(),3f,4f));
		abstractproto.put(Proto.ROCK.name, new Scenery(Scenery.Type.ROCK,new Vector2(),1f,1f));
		abstractproto.put(Proto.GRASS.name, new Scenery(Scenery.Type.GRASS,new Vector2(),0.5f,0.5f));
	}
	
	public static enum Proto {
		BLOCK("block"),
		PLATFORM("platform"),
		THUNDER("thunder"),
		CHIPSET("chipset"),
		PLAYER("player"),
		OPPONENT("opponent"),
		EXTRA("extra"),
		TREE("tree"),
		ROCK("rock"),
		GRASS("grass");
		
		public final String name;
		
		private Proto(String name) {
			this.name = name;
		}
		
	}

	public void loadPrototype(String prototype) {
		mcp = null;
		for(String key : abstractproto.keySet()) {
			if(key.matches(prototype)) {
				mcp = abstractproto.get(key);
				break;
			}
		} 
	}
	
	public IMapComponent getPrototype() {
		if(mcp!=null) {
			return mcp.copy();
		} else {
			return null;
		}
	}
	
//	public IMapComponent generateAt(float x, float y) {
//		IMapComponent created = null;
//		if(mcp != null) {
//			mcp.setCoord(new Vector2(x,y));
//			target.put(mcp);
//			created = mcp;
//			mcp = (IMapComponent) mcp.copy();
//		} else if(mp != null) {
//			mp.setCoord(new Vector2(x, y));
//			target.put(mp);
//			created = mp;
//			mp = (Movable) mp.copy();			
//		} return created;
//	}
	
//	public IMapComponent generateAt(int x, int y) {
//		IMapComponent created = null;
//		if(mcp != null) {
//			mcp.setCoord(new Vector2(x,y));
//			target.put(mcp,x,y);
//			created = mcp;
//			mcp = (IMapComponent) mcp.copy();
//		} else if(mp != null) {
//			mp.setCoord(new Vector2(x, y));
//			target.put(mp);
//			created = mp;
//			mp = (Movable) mp.copy();			
//		} return created;
//	}
	

//	public void slide(List<IMapComponent> selection, Vector2 origin, Vector2 destination) {
//		Vector2 dv = destination.sub(origin);
//		for(IMapComponent imc : selection) {
//			imc.setCoord(imc.getCoord().cpy().add(dv));
//		}		
//	}
	
	

}
