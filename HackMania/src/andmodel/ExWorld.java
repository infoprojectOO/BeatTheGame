package andmodel;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class ExWorld {

	/** Our player controlled hero **/
	ExGamer bob;
	/** A world has a level through which Bob needs to go through **/
	ExBoard level;

	/** The collision boxes **/
	Array<Rectangle> collisionRects = new Array<Rectangle>();

	// Getters -----------

	public Array<Rectangle> getCollisionRects() {
		return collisionRects;
	}
	public ExGamer getGamer() {
		return bob;
	}
	public ExBoard getLevel() {
		return level;
	}
	/** Return only the blocks that need to be drawn **/
	public List<ExBlock> getDrawableBlocks(int width, int height) {
		int x = (int)bob.getPosition().x - width;
		int y = (int)bob.getPosition().y - height;
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		int x2 = x + 2 * width;
		int y2 = y + 2 * height;
		if (x2 > level.getWidth()) {
			x2 = level.getWidth() - 1;
		}
		if (y2 > level.getHeight()) {
			y2 = level.getHeight() - 1;
		}

		List<ExBlock> blocks = new ArrayList<ExBlock>();
		ExBlock block;
		for (int col = x; col <= x2; col++) {
			for (int row = y; row <= y2; row++) {
				block = level.getBlocks()[col][row];
				if (block != null) {
					blocks.add(block);
				}
			}
		}
		return blocks;
	}

	// --------------------
	public ExWorld() {
		createDemoWorld();
	}

	private void createDemoWorld() {
		bob = new ExGamer(new Vector2(7, 2));
		level = new ExBoard();
	}

}
