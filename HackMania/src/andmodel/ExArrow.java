package andmodel;

public enum ExArrow{
	UP (ExAxis.Y,new Integer[] {0,1},1),
	DOWN (ExAxis.Y, new Integer[] {0,-1},-1),
	LEFT (ExAxis.X, new Integer[] {-1,0},-1),
	RIGHT (ExAxis.X, new Integer[] {1,0},1);
	
	private ExAxis axis;
	private Integer[] shifting;
	private int dir;

	private ExArrow(ExAxis axis, Integer[] coord, int dir) {
		this.axis = axis;
		this.shifting = coord;
		this.dir = dir;
	}
	
	public ExAxis getAxis(){
		return this.axis;
	}

	public Integer[] getShifting() {
		return shifting;
	}
	
	public int getDir() {
		return dir;
	}

}
