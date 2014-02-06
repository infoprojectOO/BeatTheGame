package items;

import control.Observer;

public final class Electricity implements Attribute {
	private int level = 0;
	private Observer observer;

	public Electricity() {
		
	}
	
	public static class Holder implements Item {
		private static final long serialVersionUID = 526998448138235854L;
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
			return "thunder";
		}

		@Override
		public void merge(Item item) {
						
		}
	}

	@Override
	public void consume(int amount) {
		this.level += amount;
		observer.update();
	}

	@Override
	public String text() {
		return String.format(": %3d", level);
	}

	@Override
	public String name() {
		return "red_battery";
	}

	@Override
	public void addObserver(Observer o) {
		this.observer = o;
	}
	

}
