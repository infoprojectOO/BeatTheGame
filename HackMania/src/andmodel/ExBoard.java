package andmodel;



import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class ExBoard {
	
	private int width;
	private int height;
	private ExBlock[][] blocks;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ExBlock[][] getBlocks() {
		return blocks;
	}

	public void setBlocks(ExBlock[][] blocks) {
		this.blocks = blocks;
	}

	public ExBoard() {
		Level();
	}

	public ExBlock get(int x, int y) {
		return blocks[x][y];
	}

	private void Level() {
		width = 10;
		height = 7;
		blocks = new ExBlock[width][height];
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				blocks[col][row] = null;
			}
		}

		for (int col = 0; col < 10; col++) {                       
			blocks[col][0] = new ExBlock(new Vector2(col, 0));
			blocks[col][6] = new ExBlock(new Vector2(col, 6));
			if (col > 2) {
			blocks[col][1] = new ExBlock(new Vector2(col, 1));
			}
		}
		blocks[9][2] = new ExBlock(new Vector2(9, 2));
		blocks[9][3] = new ExBlock(new Vector2(9, 3));
		blocks[9][4] = new ExBlock(new Vector2(9, 4));
		blocks[9][5] = new ExBlock(new Vector2(9, 5));

		blocks[6][3] = new ExBlock(new Vector2(6, 3));
		blocks[6][4] = new ExBlock(new Vector2(6, 4));
		blocks[6][5] = new ExBlock(new Vector2(6, 5));
	}


}
