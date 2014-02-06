package substrate;

import active.Gamer;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import control.ActionController;
import control.Dynamics;

public class Virtua {
	private Array<Filmtub> levels = new Array<Filmtub>();
	private Gamer explorer;
	private ActionController action;
	private Dynamics motion;
	private Collision collision;
	private Zoom view;

	public Virtua() {
		this.explorer = new Gamer(new Vector2(2,1));
		this.action = new ActionController(explorer);
		this.motion = new Dynamics();
		this.motion.addMovable(explorer);
		this.levels.add(new Filmtub(50, 10));
		this.levels.get(0).put(explorer);
		this.view = new Zoom(this.levels.get(0),new GridPoint2(0,0));
		this.view.track(explorer);
		this.explorer.addObserver(view);
		Collision.setWorld(this.levels.get(0));
	}

	public Zoom getView() {
		return this.view;
	}
	
	public Gamer getPlayer() {
		return this.explorer;
	}
	
	public ActionController getAction() {
		return this.action;
	}
	
	public Dynamics getMotion() {
		return this.motion;
	}
	
	public void update(float delta) {
		this.action.processInput();
		this.motion.update(delta);
	}

}
