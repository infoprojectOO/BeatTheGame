package geometry;

import standards.Arrow;
import standards.Axis;
import android.graphics.Point;

public class FrontLine {
	private final Point org;
	private final int length;
	private Axis axis;
	private int sign;

	public FrontLine(Arrow dir, Point org, int length) {
		this.axis = dir.getAxis();
		this.sign = dir.getDir();
		this.org = org;
		this.length = length;		
	}

	public int getLength() {
		return length;
	}

	public int getOrgX() {
		return org.x;
	}
	
	public int getOrgY() {
		return org.y;
	}
	
	public int getDir() {
		return sign;
	}

	public Axis getAxis() {
		return axis;
	}

}
