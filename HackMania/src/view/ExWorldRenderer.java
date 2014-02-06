package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

import andmodel.*;

public class ExWorldRenderer {
	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;
	private static final float RUNNING_FRAME_DURATION = 0.06f;

	private ExWorld world;
	private OrthographicCamera cam;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();

	/** Textures **/
	private TextureRegion bobIdleLeft;
	private TextureRegion bobIdleRight;
	private TextureRegion bobJumpLeft;
	private TextureRegion bobFallLeft;
	private TextureRegion bobJumpRight;
	private TextureRegion bobFallRight;
	private TextureRegion blockTexture;
	private TextureRegion bobFrame;

	/** Animations **/
	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;

	private SpriteBatch spriteBatch;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
	}

	public ExWorldRenderer(ExWorld world2) {
		this.world = world2;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		spriteBatch = new SpriteBatch();
		loadTextures();
	}

	private void loadTextures() {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures/textures.pack"));
		bobIdleLeft = atlas.findRegion("bob-01");
		bobIdleRight = new TextureRegion(bobIdleLeft);
		bobIdleRight.flip(true, false);
		blockTexture = atlas.findRegion("block");
		bobJumpLeft = atlas.findRegion("bob-05");
		bobJumpRight = new TextureRegion(bobJumpLeft);
		bobJumpRight.flip(true, false);
		bobFallLeft = atlas.findRegion("bob-06");
		bobFallRight = new TextureRegion(bobFallLeft);
		bobFallRight.flip(true, false);
		TextureRegion[] walkLeftFrames = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			walkLeftFrames[i] = atlas.findRegion("bob-0" + (i+2));
		}
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);

		TextureRegion[] walkRightFrames = new TextureRegion[5];

		for (int i = 0; i < 5; i++) {
			walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
			walkRightFrames[i].flip(true, false);
		}
		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
	}
	
	public void setDebug() {
		debug = !debug;
	}



	public void render() {
		spriteBatch.begin();
		drawBlocks();
		drawBob();
		spriteBatch.end();
		drawCollisionBlocks();
		if (debug)
			drawDebug();
	}

	private void drawCollisionBlocks() {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Filled);
		debugRenderer.setColor(new Color(1, 1, 1, 1));
		for (Rectangle rect : world.getCollisionRects()) {
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		debugRenderer.end();

	}

	private void drawBlocks() {
		System.out.println(world.getDrawableBlocks(width, height));
		for (ExBlock block : world.getDrawableBlocks(width, height)) {

			spriteBatch.draw(blockTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, block.getBreadth(ExAxis.X) * ppuX, block.getBreadth(ExAxis.Y) * ppuY);
		}
	}

	private void drawBob() {
		ExGamer bob = world.getGamer();
		bobFrame = (bob.getArrow()==ExArrow.LEFT) ? bobIdleLeft : bobIdleRight;
		if(bob.getState().equals(ExLivingState.WALK)) {
			bobFrame = (bob.getArrow()==ExArrow.LEFT) ? walkLeftAnimation.getKeyFrame(bob.getStateTime(), true) : walkRightAnimation.getKeyFrame(bob.getStateTime(), true);
		} else if (bob.getState().equals(ExLivingState.AIR)) {
				            if (bob.getVelocity().y > 0) {
				                bobFrame = (bob.getArrow()==ExArrow.LEFT) ? bobJumpLeft : bobJumpRight;
					            } else {
				                bobFrame = (bob.getArrow()==ExArrow.LEFT) ? bobFallLeft : bobFallRight;
					            }
				        }
		spriteBatch.draw(bobFrame, bob.getPosition().x * ppuX, bob.getPosition().y * ppuY, bob.getBreadth(ExAxis.X) * ppuX, bob.getBreadth(ExAxis.Y) * ppuY);
	}

	private void drawDebug() {
		// render blocks
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Line);
		for (ExBlock block : world.getDrawableBlocks(width, height)) {
			Rectangle rect = block.getBounds();
			float x1 = block.getPosition().x /*+ rect.x*/;
			float y1 = block.getPosition().y /*+ rect.y*/;
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			debugRenderer.rect(x1, y1, rect.width, rect.height);
		}
		// render Bob
		ExGamer bob = world.getGamer();
		Rectangle rect = bob.getBounds();
		float x1 = bob.getPosition().x /*+ rect.x*/;
		float y1 = bob.getPosition().y /*+ rect.y*/;
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(x1, y1, rect.width, rect.height);
		debugRenderer.end();
	}


}
