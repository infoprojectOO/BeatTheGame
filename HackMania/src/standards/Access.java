package standards;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Array;

public class Access implements Serializable {
	private static final long serialVersionUID = 7778883846807529819L;
	private List<Integer> subscriptions = new ArrayList<Integer>();
	private int personal;
	
	public Access(Access.Allowance key) {
		this.personal = key.getKey();				
	}
	
	public static enum Allowance {
		CHARACTER(1),
		SCENERY(2),
		ITEM(3),
		OBSTACLE(4);
		
		private int key;

		private Allowance(int key) {
			this.key = key;
		}

		public int getKey() {
			return key;
		}
	}
	
	public void subscribe(Access.Allowance key) {
		subscriptions.add(key.getKey());
	}
	
	public boolean allow(Access access) {
		return access.getKeys().contains(personal);
	}

	private List<Integer> getKeys() {
		return subscriptions;
	}

}
