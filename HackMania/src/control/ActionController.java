package control;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import standards.Arrow;
import standards.LivingState;

import active.Gamer;

public class ActionController {
	private static final long IMPULSE_TIME = 250l;
	private Map<String,Boolean> keys = new HashMap<String,Boolean>(); {
		{
			keys.put("left", false);
			keys.put("right", false);
			keys.put("jump", false);
			keys.put("fire", false);
		}
	};
	private Gamer actionchar;
	private boolean updates;
	private long impulseStart = 0l;
	private boolean fired; 

	public ActionController(Gamer player) {
		this.actionchar = player;
	}

	public void left(boolean b) {
		keys.put("left", b);
		updates = true;
	}

	public void right(boolean b) {
		keys.put("right", b);
		updates = true;
	}

	public void jump(boolean b) {
		keys.put("jump", b);
		updates = true;
	}

	public void fire(boolean b) {
		keys.put("fire", b);
		updates = true;
		fired = false;
	}
	
	public void processInput() {
		if(updates) {
			if(keys.get("left")) {
				actionchar.pull(true);
				if(actionchar.isGrounded()) {
					actionchar.setState(LivingState.WALK);
				}
				actionchar.setArrow(Arrow.LEFT);
				//			actionchar.getVelocity().x = -actionchar.speed;
				actionchar.getAcceleration().x = -Gamer.speedinc;
			} else if(keys.get("right")) {
				actionchar.pull(true);
				actionchar.setArrow(Arrow.RIGHT);
				if(actionchar.isGrounded()) {
					actionchar.setState(LivingState.WALK);
				}
				//			actionchar.getVelocity().x = actionchar.speed;
				actionchar.getAcceleration().x = Gamer.speedinc;
			} else if(actionchar.getVelocity().x!=0) {
				//			actionchar.getVelocity().x = 0;
				Vector2 v = actionchar.getVelocity();
				actionchar.getAcceleration().x = -v.x/Math.abs(v.x)*Gamer.speeddec;
				actionchar.pull(false);
			} else {
				actionchar.setState(LivingState.IDLE);
				actionchar.getAcceleration().x = 0;
			} if(keys.get("jump")) {
				if(actionchar.isGrounded()) {
					actionchar.setState(LivingState.AIR);
					actionchar.getVelocity().y = Gamer.jump_impulse;
					impulseStart = System.currentTimeMillis();
				} else if(System.currentTimeMillis()-impulseStart<IMPULSE_TIME) {
					actionchar.getVelocity().y = Gamer.jump_impulse;			
				} 	
			} if(keys.get("fire")&&!fired) {
				actionchar.shoot();
				fired = true;
			}
//			updates = false;
		}
	}

}
