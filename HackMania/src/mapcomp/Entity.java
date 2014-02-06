package mapcomp;

import java.io.Serializable;

import standards.Axis;

public class Entity implements Serializable {
	private static final long serialVersionUID = 5732050394160130351L;
	private float[][] friction = {{0,0},{0,0}};
	private float[][] restitution = {{1,0},{0,1}};

	public Entity(float brake, float rebounce) {
		this.friction[0][0] = brake;
		this.friction[1][1] = brake;
		this.friction[0][1] = brake;
		this.friction[1][0] = brake;
		this.restitution[0][0] = rebounce;
		this.restitution[1][1] = rebounce;
		this.restitution[0][1] = rebounce;
		this.restitution[1][0] = rebounce;
	}
	
	public Entity(float[] asymbrake, float[] asymbounce) {
		this.friction[0] = asymbrake;
		this.restitution[1] = asymbounce;
	}
	
	/**convention : forces on X axis index 0
	 				forces on Y axis index 1
	 				forces counter positive increments index 1
	 				forces counter negative increments index 0**/
	public Entity(float[][] brakes, float[][] bounces) {
		this.friction = brakes;
		this.restitution = bounces;
	}
	
	public float getFriction() {
		return friction[0][0];
	}
	
	public float getFriction(Axis axis, float val) {
		int row = (axis==Axis.X) ? 0 : 1;
		int col = (val<0) ? 0 : 1;
		return friction[row][col];
	}
	
	public float getRestitution() {
		return restitution[0][0];
	}
	
	public float getRestitution(Axis axis, float val) {
		int row = (axis==Axis.X) ? 0 : 1;
		int col = (val<0) ? 0 : 1;
		return restitution[row][col];
	}

	public float[] getFriction(Axis axis) {
		int row = (axis==Axis.X) ? 0 : 1;
		return friction[row];
	}
	
	public float[] getRestitution(Axis axis) {
		int row = (axis==Axis.X) ? 0 : 1;
		return restitution[row];
	}

}
