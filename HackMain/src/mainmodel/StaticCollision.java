package mainmodel;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import mapcomp.IMapComponent;
import substrate.Filmtub;

public class StaticCollision {
	private static Filmtub physworld;
	private static boolean masterlap;

	public StaticCollision() {
		// TODO Auto-generated constructor stub
	}
	
	public static void setOverlap(boolean state) {
		StaticCollision.masterlap = state;
	}
	
	public boolean authorize(IMapComponent newcomp) {
		if(this.physworld.fitsin(newcomp)) {
			Rectangle rect = newcomp.getBox();
			Array<IMapComponent> candidates = physworld.getObjects(rect,newcomp);
			for(IMapComponent candidate : candidates){
				if(candidate.getBox().overlaps(rect)) {
					if(!masterlap) {
						System.out.println("Overlapping");
						return false;
					}
				}
			} return true;
		} else {
			System.out.println("Out of Bounds");
			return false;
		}
	}
	
	public static void setMap(Filmtub map) {
		StaticCollision.physworld = map;
	}

}
