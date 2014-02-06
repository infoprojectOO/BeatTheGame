package utilities;

import standards.Axis;

import com.badlogic.gdx.math.Vector2;

public class Speed extends Vector2 {
	private Vector2 inflimit = new Vector2(-10,-10);
	private Vector2 suplimit = new Vector2(10, 10);
	private float[][] friction = {{0,0},{0,0}};

	public Speed() {
		super();
	}

	public Speed(Vector2 v) {
		super(v);
		// TODO Auto-generated constructor stub
	}

	public Speed(float x, float y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	public void setLimits(float inf, float sup) {
		this.inflimit.x = inf;
		this.suplimit.x = sup;
//		this.inflimity = inf;
//		this.suplimity = sup;	
	}
	
	@Override
	public Vector2 add(Vector2 vect) {
		super.add(vect);
		if(this.x < inflimit.x*(1-friction[0][0])) {
			this.x = inflimit.x*(1-friction[0][0]);
		} else if(this.x > suplimit.x*(1-friction[0][1])){
			this.x = suplimit.x*(1-friction[0][1]);
		} if(this.y < inflimit.y*(1-friction[1][0])) {
			this.y = inflimit.y*(1-friction[1][0]);
		} else if (this.y > suplimit.y*(1-friction[1][1])) {
			this.y = suplimit.y*(1-friction[1][1]);
		}
		return this;
	}

	public void friction(Axis axis, float[] friction) {
		if(axis==Axis.X) {
			if(friction[0]>this.friction[0][0]) {
				this.friction[0][0] = friction[0];
			} if(friction[1]>this.friction[0][1]) {
				this.friction[0][1] = friction[1];
			}
		} else {
			if(friction[0]>this.friction[0][0]) {
				this.friction[1][0] = friction[0];
			} if(friction[1]>this.friction[0][1]) {
				this.friction[1][1] = friction[1];
			}
		}
	}

	public void friction(Axis axis, boolean b) {
		if(axis==Axis.X) {
			this.friction[0][0] = 0;
			this.friction[0][1] = 0;
		} else {
			this.friction[1][0] = 0;
			this.friction[1][1] = 0;
		}
		
	}

}
