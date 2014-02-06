package items;

import java.io.Serializable;

public interface Item extends Serializable {
	String getID();
	void merge(Item item);
	int getAmount();

}
