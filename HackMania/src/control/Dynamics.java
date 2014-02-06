package control;

import java.util.HashSet;
import java.util.Set;

import items.Throwable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import active.Gamer;
import active.Movable;
import substrate.Virtua;

public class Dynamics {
	private static float gravity = -10f;
	private static Set<Movable> actors;
	private static Set<items.Throwable> thrown;

	public Dynamics() {
		Dynamics.actors = new HashSet<Movable>();	
		Dynamics.thrown = new HashSet<Throwable>();
	}
	
	public static void addMovable(Movable actor) {
		actors.add(actor);
	}

	public void update(float delta) {
		for(Movable actor : actors) {
			actor.getAcceleration().y = gravity;
			actor.move(delta);
		} 
		synchronized(thrown) {
			for(Throwable thr : new HashSet<Throwable>(thrown)) {
					thr.move(delta);
			}
		}
//		System.out.println(thrown.size());
//		System.out.println("after");
	}

	public static void addThrowable(items.Throwable bullet) {
		thrown.add(bullet);
	}

	public static void destroy(Throwable bullet) {
		thrown.remove(bullet);		
	}

	public static void destroy(Movable anim) {
		Dynamics.actors.remove(anim);		
	}

}
