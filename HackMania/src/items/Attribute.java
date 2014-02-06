package items;

import control.Observer;

public interface Attribute {
	void addObserver(Observer o);
	void consume(int amount);
	String text();
	String name();
}
