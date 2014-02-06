package items;

import com.badlogic.gdx.math.Vector2;

import control.Observer;

public class Circuitry implements Attribute {
	private int level;
	private Observer observer;
	public static int CRITICAL = 50;
	public static int SEVERE = 25;
	public static int HEAVY = 15;
	public static int MEDIUM = 10;
	public static int LIGHT = 5;
	public static int MINIMAL = 1;
	
	public Circuitry() {
		this.level = 100;
	}
	
	public static class Holder implements Item {
		private static final long serialVersionUID = -2426731414886667397L;
		private int level;

		public Holder(int amount) {
			this.level = amount;
		}
		
		@Override
		public int getAmount() {
			return level;
		}

		@Override
		public String getID() {
			return "chip";
		}

		@Override
		public void merge(Item item) {
						
		}
	}

	@Override
	public void consume(int amount) {
		this.level += amount;
		if(level<0) {
			level = 0;
		} else if(level>100) {
			level = 100;
		} observer.update();
	}

	@Override
	public String text() {
		return (String.format(": %3d" , level)+"%");
	}

	@Override
	public String name() {
		return "computer_chip";
	}

	@Override
	public void addObserver(Observer o) {
		this.observer = o;		
	}
	

}
