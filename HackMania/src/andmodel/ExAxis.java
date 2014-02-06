package andmodel;

public enum ExAxis {
	X,Y;
	
	public ExAxis reverse() {
		if (this.equals(X)) {
			return (Y);
		} else {
			return (X);
		}
	}
}
