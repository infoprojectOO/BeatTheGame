package standards;

public enum Arrow {
	UP (Axis.Y,new Integer[] {0,1},1,2),
	DOWN (Axis.Y, new Integer[] {0,-1},-1,3),
	LEFT (Axis.X, new Integer[] {-1,0},-1,0),
	RIGHT (Axis.X, new Integer[] {1,0},1,1);
	
	private Axis axis;
	private Integer[] shifting;
	private int dir;
	private int order;

	private Arrow(Axis axis, Integer[] coord, int dir, int order) {
		this.axis = axis;
		this.shifting = coord;
		this.dir = dir;
		this.order = order;
	}
	
	public Axis getAxis(){
		return this.axis;
	}

	public Integer[] getShifting() {
		return shifting;
	}
	
	public int getDir() {
		return dir;
	}
	
	public int getOrder() {
		return order;
	}

}
