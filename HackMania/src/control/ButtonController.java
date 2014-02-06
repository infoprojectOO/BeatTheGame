package control;

import java.util.Map;
import java.util.HashMap;

import standards.PlayerAction;
import view.SceneFrame;

import android.net.LocalSocketAddress.Namespace;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ButtonController extends ClickListener {
	public static final Map<PlayerAction,String> names = new HashMap<PlayerAction,String>() {
		{
			put(PlayerAction.MOVE,"move");
			put(PlayerAction.JUMP,"jump");
			put(PlayerAction.FIRE,"fire");
		}
	};
	private ActionController master;
	private SceneFrame stage;
	private boolean gone;

	public ButtonController(ActionController master, SceneFrame stage) {
		super();
		this.master = master;
		this.stage = stage;
	}

	public ButtonController(int button) {
		super(button);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		boolean update = super.touchDown(event, x, y, pointer, button);
		System.out.println("touch");
		if(update) {
			gone = false;
			Actor source = event.getTarget();
			if(source.getName()=="move") {
				move(x-source.getOriginX(),y-source.getOriginY(),true); 
			} else if(source.getName()=="fire") {
				fire(true);
			} else if(source.getName()=="jump"){
				jump(true);
			}
		} return update;
			
	}
	
	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer) {
		super.touchDragged(event, x, y, pointer);
		Actor source = event.getTarget();
		Vector2 stagecoord = source.localToStageCoordinates(new Vector2(x,y));
		Actor target = event.getStage().hit(stagecoord.x,stagecoord.y, true);
//		System.out.println(String.format("x : %f y : %f", x,y));
		String name = source.getName();
		if(!isOver(source, x, y)) gone = true;
		if(name=="move") move(x-source.getOriginX(),y-source.getOriginY(),isOver(source, x, y));
		else if(name=="jump") jump(isOver(source, x, y));
		else if(name=="fire"&&gone) fire(isOver(source, x, y));
//		if(target!=null) {
//			name = target.getName();
//			if(name=="move") move(x-source.getOriginX(),y-source.getOriginY(),true);
//			else if(name=="jump") jump(true);
//			else if(name=="fire"&&gone) fire(true);
//		} else {
//			move(x-source.getOriginX(),y-source.getOriginY(),false);
//			jump(false);
//			fire(false);
//		}
		if(gone&&isOver(source,x,y)) gone = false;
	}
	
	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		Actor source = event.getTarget();
		if(source.getName()=="move") {
			move(x-source.getOriginX(),y-source.getOriginY(),false); 
		} else if(source.getName()=="fire") {
			fire(false); stage.layer(SceneFrame.FIRE, false);
		} else if(source.getName()=="jump"){
			jump(false); stage.layer(SceneFrame.JUMP, false);
		} super.touchDown(event, x, y, pointer, button);
		super.touchUp(event, x, y, pointer, button);
	}
	
	@Override
	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
		super.enter(event, x, y, pointer, fromActor);
		System.out.println("entered");
	}

	private void jump(boolean j) {
		master.jump(j);
		stage.layer(SceneFrame.JUMP, j);
	}

	private void fire(boolean f) {
		master.fire(f);
		stage.layer(SceneFrame.FIRE, f);
	}

	private void move(float x, float y, boolean move) {
		if(x<0) {
			master.left(move);
			master.right(false);
			stage.layer(SceneFrame.LEFT, move);
			stage.layer(SceneFrame.RIGHT, false);
		} else if(x>0) {
			master.right(move);
			master.left(false);
			stage.layer(SceneFrame.LEFT, false);
			stage.layer(SceneFrame.RIGHT, move);
		} else {
			master.left(false);
			master.right(false);
			stage.layer(SceneFrame.LEFT, false);
			stage.layer(SceneFrame.RIGHT, false);
		}
	}
	

}
