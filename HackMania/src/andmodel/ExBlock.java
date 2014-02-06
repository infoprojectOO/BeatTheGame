package andmodel;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class ExBlock{
	private float width,height;
	private Rectangle box = new Rectangle();
	private Vector2 pos;

	public ExBlock(Vector2 pos) {
		this.pos = pos;
		this.box.setX(pos.x);
		this.box.setY(pos.y);
		this.width = 1f;
		this.height = 1f;
		box.width = 1f;
		box.height = 1f;
	
	}


	public float getBreadth(ExAxis axis) {
		if(axis==ExAxis.X) {
			return width;
		} else {
			return height;
		}
	}


	public Rectangle getBounds() {
		return this.box;
	}


	public Vector2 getPosition() {
		return pos;
	}


}
