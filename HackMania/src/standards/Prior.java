package standards;

public enum Prior {
	ABSOLUTE(0),
	HIGHER(1),
	HIGH(2),
	AVERAGE(3),
	LOW(4),
	LOWER(5),
	NONE(6); 
	
	private final int order;

	private Prior(int value) {
		this.order = value;
	}
	
	public int above(Prior p) {
		return (p.order - this.order); 
	}
}
