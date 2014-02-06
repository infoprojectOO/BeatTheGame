package view;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import andmodel.*;

public class ExPlayerController {

	private ExWorld  world;
	private Array<ExBlock> collidable = new Array<ExBlock>();
	private ExGamer     bob;

	private static final float WIDTH = 10f;

	private static final long LONG_JUMP_PRESS   = 150l;
	private static final float ACCELERATION     = 20f;
	private static final float GRAVITY          = -20f;
	private static final float MAX_JUMP_SPEED   = 7f;
	private static final float DAMP             = 0.90f;
	private static final float MAX_VEL          = 4f;

	private long    jumpPressedTime;
	private boolean jumpingPressed;
	private boolean grounded = false;

	public static Map<String, Boolean> keys = new HashMap<String, Boolean>();
	static {
		keys.put("left", false);
		keys.put("right", false);
		keys.put("jump", false);
		keys.put("fire", false);
	};

	public ExPlayerController(ExWorld world) {
		this.world = world;
		this.bob = world.getGamer();
	}

	// ** Key presses and touches **************** //

	public void leftPressed() {
		keys.get(keys.put("left", true));
	}

	public void rightPressed() {
		keys.get(keys.put("right", true));
	}

	public void jumpPressed() {
		keys.get(keys.put("jump", true));
	}

	public void firePressed() {
		keys.get(keys.put("fire", true));
	}

	public void leftReleased() {
		keys.get(keys.put("left", false));
	}

	public void rightReleased() {
		keys.get(keys.put("right", false));
	}

	public void jumpReleased() {
		keys.get(keys.put("jump", false));
		jumpingPressed = false;
	}

	public void fireReleased() {
		keys.get(keys.put("fire", false));
	}
	
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};

	public void update(float delta) {
		processInput();
		if (grounded && bob.getState().equals(ExLivingState.AIR)) {
			bob.setState(ExLivingState.IDLE);
		}
		bob.getAcceleration().y = GRAVITY;
		bob.getAcceleration().scl(delta);
		bob.getVelocity().add(bob.getAcceleration().x, bob.getAcceleration().y);
		checkCollisionWithBlocks(delta);
		bob.getVelocity().x *= DAMP;
		if (bob.getVelocity().x > MAX_VEL) {
			bob.getVelocity().x = MAX_VEL;
		}
		if (bob.getVelocity().x < -MAX_VEL) {
			bob.getVelocity().x = -MAX_VEL;
		}
		bob.update(delta);
	}

	private void checkCollisionWithBlocks(float delta) {
		bob.getVelocity().scl(delta);
		Rectangle bobRect = rectPool.obtain();
		bobRect.set(bob.getBounds().x, bob.getBounds().y, bob.getBounds().width, bob.getBounds().height);
		int startX, endX;
		int startY = (int) bob.getBounds().y;
		int endY = (int) (bob.getBounds().y + bob.getBounds().height);
		if (bob.getVelocity().x < 0) {
			startX = endX = (int) Math.floor(bob.getBounds().x + bob.getVelocity().x);
		} else {
			startX = endX = (int) Math.floor(bob.getBounds().x + bob.getBounds().width + bob.getVelocity().x);
		}
		populateCollidableBlocks(startX, startY, endX, endY);
		bobRect.x += bob.getVelocity().x;
		world.getCollisionRects().clear();
		for (ExBlock block : collidable) {
			if (block == null) continue;
			if (bobRect.overlaps(block.getBounds())) {
				bob.getVelocity().x = 0;
				world.getCollisionRects().add(block.getBounds());
				break;
			}
		}
		bobRect.x = bob.getPosition().x;
		startX = (int) bob.getBounds().x;
		endX = (int) (bob.getBounds().x + bob.getBounds().width);
		if (bob.getVelocity().y < 0) {
			startY = endY = (int) Math.floor(bob.getBounds().y + bob.getVelocity().y);
		} else {
			startY = endY = (int) Math.floor(bob.getBounds().y + bob.getBounds().height + bob.getVelocity().y);
		}
		populateCollidableBlocks(startX, startY, endX, endY);
		bobRect.y += bob.getVelocity().y;
		for (ExBlock block : collidable) {
			if (block == null) continue;
			if (bobRect.overlaps(block.getBounds())) {
				if (bob.getVelocity().y < 0) {
					grounded = true;
				}
				bob.getVelocity().y = 0;
				world.getCollisionRects().add(block.getBounds());
				break;
			}
		}
		bobRect.y = bob.getPosition().y;
		bob.getPosition().add(bob.getVelocity());
		bob.getBounds().x = bob.getPosition().x;
		bob.getBounds().y = bob.getPosition().y;
		bob.getVelocity().scl(1 / delta);
	}

	private void populateCollidableBlocks(int startX, int startY, int endX, int endY) {
		collidable.clear();
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (x >= 0 && x < world.getLevel().getWidth() && y >=0 && y < world.getLevel().getHeight()) {
					collidable.add(world.getLevel().get(x, y));
				}
			}
		}
	}

	/** 
	public void update(float delta) {
		processInput();

		bob.getAcceleration().y = GRAVITY;
		bob.getAcceleration().scl(delta);
		bob.getVelocity().add(bob.getAcceleration().x, bob.getAcceleration().y);
		if (bob.getAcceleration().x == 0) bob.getVelocity().x *= DAMP;
		if (bob.getVelocity().x > MAX_VEL) {
			bob.getVelocity().x = MAX_VEL;
		}
		if (bob.getVelocity().x < -MAX_VEL) {
			bob.getVelocity().x = -MAX_VEL;
		}

		bob.update(delta);
		if (bob.getPosition().y < 0) {
			bob.getPosition().y = 0f;
			bob.setPosition(bob.getPosition());
			if (bob.getState().equals(ExLivingState.AIR)) {
				bob.setState(ExLivingState.IDLE);
			}
		}
		if (bob.getPosition().x < 0) {
			bob.getPosition().x = 0;
			bob.setPosition(bob.getPosition());
			if (!bob.getState().equals(ExLivingState.AIR)) {
				bob.setState(ExLivingState.IDLE);
			}
		}
		if (bob.getPosition().x > WIDTH - bob.getBounds().width ) {
			bob.getPosition().x = WIDTH - bob.getBounds().width;
			bob.setPosition(bob.getPosition());
			if (!bob.getState().equals(ExLivingState.AIR)) {
				bob.setState(ExLivingState.IDLE);
			}
		}
	}The main update method **/

	/** Change Bob's state and parameters based on input controls 
	 * @return **/
	private boolean processInput() {
		if (keys.get("jump")) {
			if (!bob.getState().equals(ExLivingState.AIR)) {
				jumpingPressed = true;
				jumpPressedTime = System.currentTimeMillis();
				bob.setState(ExLivingState.AIR);
				bob.getVelocity().y = MAX_JUMP_SPEED;
				grounded = false;
			} else {
				if (jumpingPressed && ((System.currentTimeMillis() - jumpPressedTime) >= LONG_JUMP_PRESS)) {
					jumpingPressed = false;
				} else {
					if (jumpingPressed) {
						bob.getVelocity().y = MAX_JUMP_SPEED;
					}
				}
			}
		}
		if (keys.get("left")) {
			// left is pressed
			bob.setArrow(ExArrow.LEFT);
			if (!bob.getState().equals(ExLivingState.AIR)) {
				bob.setState(ExLivingState.WALK);
			}
			bob.getAcceleration().x = -ACCELERATION;
		}
		else if (keys.get("right")) {
			// left is pressed
			bob.setArrow(ExArrow.RIGHT);
			if (!bob.getState().equals(ExLivingState.AIR)) {
				bob.setState(ExLivingState.WALK);
			}
			bob.getAcceleration().x = ACCELERATION;
		}
		else {
			if (!bob.getState().equals(ExLivingState.AIR)) {
				bob.setState(ExLivingState.IDLE);
			}
			bob.getAcceleration().x = 0;

		}
		return false;
	}



}
