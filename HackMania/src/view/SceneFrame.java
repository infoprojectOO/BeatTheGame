package view;

import items.Attribute;
import items.Circuitry;
import items.Electricity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import standards.PlayerAction;
import substrate.Virtua;
import utilities.ImageFetcher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;

import control.ActionController;
import control.ButtonController;
import control.Observer;

public class SceneFrame extends Stage implements Observer {
	public static final String JUMP ="jump", FIRE ="fire",LEFT="left",RIGHT="right";
	private Button movebutton, jumpbutton, firebutton;
	private ActionController actioncontrol;
	private Renderer backstage;
	private ClickListener buttoncontroller;
	private Array<Label> labels = new Array<Label>();
	private Set<Sprite> top = new HashSet<Sprite>();
	private Map<String,Sprite> layers = new HashMap<String, Sprite>();
	private Group information;
	private Attribute[] attributes;
	
	public SceneFrame(Virtua virtua, SpriteBatch batch) {
		super(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),false,batch);
		this.actioncontrol = virtua.getAction();
		this.attributes = virtua.getPlayer().getAttributes();
		this.information = new Group();
		this.buttoncontroller = new ButtonController(actioncontrol,this);
		createButtons();
		unpackLayers();
		unpackInfo();
	}

	private void createButtons() {
		this.movebutton = new Button(ImageFetcher.getDrawable("gadgets/gadgets","movebutton"));
		movebutton.setName(ButtonController.names.get(PlayerAction.MOVE));
		movebutton.setColor(1, 1, 1, 0.65f);
		movebutton.setBounds(this.getWidth()/10, this.getHeight()/10, this.getWidth()/8, this.getWidth()/8);
		movebutton.setTouchable(Touchable.enabled);
		movebutton.setOrigin(movebutton.getWidth()/2, movebutton.getHeight()/2);
		movebutton.addListener(buttoncontroller);
		this.jumpbutton = new Button(ImageFetcher.getDrawable("gadgets/gadgets","jumpbutton"));
		jumpbutton.setColor(1, 1, 1, 0.65f);
		jumpbutton.setName(ButtonController.names.get(PlayerAction.JUMP));
		jumpbutton.setBounds(this.getWidth()*7/10, this.getHeight()/10, this.getWidth()/10, this.getWidth()/10);
		jumpbutton.addListener(buttoncontroller);
		this.firebutton = new Button(ImageFetcher.getDrawable("gadgets/gadgets","firebutton"));
		firebutton.setColor(1, 1, 1, 0.65f);
		firebutton.setName(ButtonController.names.get(PlayerAction.FIRE));
		firebutton.setBounds(this.getWidth()*4/5, this.getHeight()/10, this.getWidth()/10, this.getWidth()/10);
		firebutton.addListener(buttoncontroller);
		this.addActor(movebutton);
		this.addActor(jumpbutton);
		this.addActor(firebutton);
	}
	
	private void unpackLayers() {
		String[] names = {"up","down","left","right","jump","fire"};
		int i=0;
		for(String name : names){
			Sprite sprite = ImageFetcher.getSprite("gadgets/gadgets",name+"_on");
			switch (i) {
			case 4: sprite.setBounds(jumpbutton.getX(), jumpbutton.getY(),jumpbutton.getWidth(),jumpbutton.getHeight()); break;
			case 5: sprite.setBounds(firebutton.getX(), firebutton.getY(),firebutton.getWidth(),firebutton.getHeight()); break;
			default: sprite.setBounds(movebutton.getX(), movebutton.getY(),movebutton.getWidth(), movebutton.getHeight()); break;
			} i++;
			layers.put(name, sprite);
		}
	}
	
	private void unpackInfo() {
		float factor = 7/10f;
		for(Attribute attribute : attributes) {
			attribute.addObserver(this);
			Image im = ImageFetcher.getImage("gadgets/gadgets",attribute.name());
			im.setBounds(this.getWidth()*factor, this.getHeight()*9/10f, this.getWidth()/20, this.getHeight()/20);
			Label label = new Label(attribute.text(), new LabelStyle(new BitmapFont(), new Color(1, 1, 1, 1)));
			label.setPosition(this.getWidth()*(factor+1/20f), this.getHeight()*9/10f);
			labels.add(label);
			factor += 1/7f; 
			information.addActor(im);
			information.addActor(label);
		}
		
	}

	public SceneFrame(float width, float height, boolean keepAspectRatio) {
		super(width, height, keepAspectRatio);
		// TODO Auto-generated constructor stub
	}

	public SceneFrame(float width, float height, boolean keepAspectRatio, SpriteBatch batch) {
		super(width, height, keepAspectRatio, batch);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.LEFT : actioncontrol.left(true); top.add(layers.get("left")); break;
		case Keys.RIGHT : actioncontrol.right(true);top.add(layers.get("right")); break;
		case Keys.Z : actioncontrol.jump(true);top.add(layers.get("jump")); break;
		case Keys.X : actioncontrol.fire(true);top.add(layers.get("fire")); break;
		} return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.LEFT : actioncontrol.left(false);top.remove(layers.get("left")); break;
		case Keys.RIGHT : actioncontrol.right(false);top.remove(layers.get("right")); break;
		case Keys.Z : actioncontrol.jump(false);top.remove(layers.get("jump")); break;
		case Keys.X : actioncontrol.fire(false);top.remove(layers.get("fire")); break;
		} return true;
	}

	@Override
	public boolean keyTyped(char character) {
		if (character == 'd')
			backstage.toogleDebug();
		else if (character == 'p') {
			Gdx.app.getApplicationListener().pause();
		} else if (character == 'r') {
			Gdx.app.getApplicationListener().resume();
		}
		return true;
	}

//	@Override
//	public boolean touchDown(int x, int y, int pointer, int button) {
//		InputEvent evt = new InputEvent();
//		evt.setButton(button);
//		evt.setType(InputEvent.Type.touchDown);
//		evt.setTarget(hit(x,y,false));
//		evt.getTarget().fire(evt);
//		System.out.println(String.format("%d , %d",x,y));
//		System.out.println(Buttons.LEFT);
//		Vector2 vtouch = new Vector2(x,y);
//		this.screenToStageCoordinates(vtouch);
//		System.out.println(this.hit(vtouch.x, vtouch.y, false));
//		Array<EventListener> listeners = this.hit(vtouch.x, vtouch.y, false).getListeners();
		
//		if (!Gdx.app.getType().equals(ApplicationType.Android))
//			return false;
//		if (x < width / 2 && y > height / 2) {
//			actioncontrol.leftPressed();
//		}
//		if (x > width / 2 && y > height / 2) {
//			actioncontrol.rightPressed();
//		}
//		return true;
//	}

//	@Override
//	public boolean touchUp(int x, int y, int pointer, int button) {
//		if (!Gdx.app.getType().equals(ApplicationType.Android))
//			return false;
//		if (x < width / 2 && y > height / 2) {
//			actioncontrol.leftReleased();
//		}
//		if (x > width / 2 && y > height / 2) {
//			actioncontrol.rightReleased();
//		}
//		return false;
//	}

//	@Override
//	public boolean touchDragged(int x, int y, int pointer) {
//		// TODO Auto-generated method stub
//		return false;
//	}

	@Override
	public boolean mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void layer(String layer,boolean state) {
		if(state) {
			this.top.add(layers.get(layer));
		} else {
			this.top.remove(layers.get(layer));
		}
	}
	
	@Override
	public void draw() {
		super.draw();
		SpriteBatch sb = backstage.getBatch();
		sb.begin();
		for(Sprite image : top) {
			image.draw(sb);
		} this.information.draw(sb, 1);
		sb.end();
	}

	public void setBackStage(Renderer r) {
		this.backstage = r;
	}

	@Override
	public void update() {
		int index = 0;
		for(Attribute a : attributes) {
			Label label = this.labels.get(index);
			label.setText(a.text());
			if(label.needsLayout()) {
				label.pack();
			}
			index++;
		}
		
	}

}
