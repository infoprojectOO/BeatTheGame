package geometry;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import standards.Arrow;

public final class Direction {
	public Map<Arrow,Integer> dir = new HashMap<Arrow,Integer>();
	
	public Direction(Arrow a1, int l1) {
		dir.put(a1, l1);
	}

	public Direction(Arrow a1, int l1, Arrow a2, int l2) {
		dir.put(a2, l2);
		dir.put(a1, l1);
	}
	
	public Integer[] getShifting() {
		Integer[] shifting = {0,0};
		for(Entry<Arrow,Integer> e : dir.entrySet()) {
			shifting[0] += e.getKey().getShifting()[0]*e.getValue();
			shifting[1] += e.getKey().getShifting()[1]*e.getValue();
		} return shifting;
	}

}
