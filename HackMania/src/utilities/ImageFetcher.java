package utilities;

import items.Bullet;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

import mapcomp.Block;
import mapcomp.IMapComponent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;


import active.Gamer;
import active.Movable;
import active.Opponent;

public class ImageFetcher {
	private final static Map<String,TextureAtlas> imagebook = new HashMap<String,TextureAtlas>();
	
	public static TextureAtlas getAtlas(String name) {
		TextureAtlas ta = imagebook.get(name);
		if(ta==null) {
			ta = new TextureAtlas(Gdx.files.internal(name)+".pack");
			imagebook.put(name,ta);
		} return ta;
	}
	
	public static Animation[] animate(String imname, float duration) {
		Animation[] anims = new Animation[2];
		TextureRegion[] leftframes = new TextureRegion[8];
		TextureRegion[] rightframes = new TextureRegion[8];
		for(int i=0;i<8;i++) {
			rightframes[i] = new TextureRegion(new Texture(Gdx.files.internal("animations/"+(i+1)+"a_"+imname+".png")));
			leftframes[i] = new TextureRegion(rightframes[i]);
			leftframes[i].flip(true, false);
		} anims[0] = new Animation(duration,leftframes);
		anims[1] = new Animation(duration, rightframes);
		return anims;
	}
	
	public static Animation[] animate(String imname, float duration, int[] indexes) {
		Animation[] anims = new Animation[2];
		TextureRegion[] leftframes = new TextureRegion[indexes.length];
		TextureRegion[] rightframes = new TextureRegion[indexes.length];
		for(int i=0;i<4;i++) {
			rightframes[i] = new TextureRegion(new Texture(Gdx.files.internal("animations/"+imname+"_"+(i+1)+"a"+".png")));
			leftframes[i] = new TextureRegion(rightframes[i]);
			leftframes[i].flip(true, false);
		} anims[0] = new Animation(duration,leftframes);
		anims[1] = new Animation(duration, rightframes);
		return anims;
	}
	
	public static TextureRegion getTexture(String name) {
		return new TextureRegion(new Texture(Gdx.files.internal(name)));
	}

	public static TextureRegion[] getTextures(String name) {
		TextureRegion right = new TextureRegion(new Texture(Gdx.files.internal(name)));
		TextureRegion left = new TextureRegion(right);
		left.flip(true, false);
		return new TextureRegion[] {left,right};
	}
	
	public static Drawable getDrawable(String name) {
		return new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal(name))));	
	}
	
	public static Sprite getSprite(String name) {
		return new Sprite(new Texture(Gdx.files.internal(name)));
	}
	
	public static Image getImage(String name) {
		return new Image(new Texture(Gdx.files.internal(name)));
	}

	public static Animation[] animate(TextureGroup tg, float frameduration) {
		Animation[] anims = new Animation[2];
		TextureRegion[] leftframes = new TextureRegion[tg.getGroups().get("walk").size];
		TextureRegion[] rightframes = new TextureRegion[tg.getGroups().get("walk").size];
		Array<TextureRegion> arr = tg.getGroups().get("walk");
		int i = 0;
		for(TextureRegion tr : arr) {
			rightframes[i] = new TextureRegion(tr);
			leftframes[i] = new TextureRegion(tr);
			leftframes[i].flip(true, false);
			i++;
		} anims[0] = new Animation(frameduration, leftframes);
		anims[1] = new Animation(frameduration, rightframes);
//		System.out.println(anims[0].getKeyFrame(0.5f,true));
		return anims;
	}
	
	public static TextureRegion getTexture(String pack, String name) {
		return getAtlas(pack).findRegion(name);
	}
	
	public static TextureRegion[] getTextures(String pack, String name) {
		TextureRegion right = getAtlas(pack).findRegion(name);
		TextureRegion left = new TextureRegion(right);
		left.flip(true, false);
		return new TextureRegion[] {left,right};		
	}
	
	public static Drawable getDrawable(String pack, String name) {
		return new SpriteDrawable(new Sprite(getAtlas(pack).findRegion(name)));
	}
	
	public static Sprite getSprite(String pack, String name) {
		return new Sprite(getAtlas(pack).findRegion(name));
	}
	
	public static Image getImage(String pack, String name) {
		return new Image(getAtlas(pack).findRegion(name));
	}
	
	public static Animation[] animate(String pack, String root, float duration) {
		Animation[] anims = new Animation[2];
		ArrayDeque<TextureRegion> frames = new ArrayDeque<TextureRegion>();
		TextureAtlas atlas = getAtlas(pack);
		int i = 1;
		boolean done = false;
		while(done==false){
			try {
				frames.add(atlas.findRegion(root+"_"+i+"a"));
				i++;
			} catch(NullPointerException npe) {
				done = true;
			}			
		} TextureRegion[] leftframes = new TextureRegion[frames.size()];
		TextureRegion[] rightframes = new TextureRegion[frames.size()];
		for(i=0;i<leftframes.length;i++) {
			rightframes[i] = frames.pop();
			leftframes[i] = new TextureRegion(rightframes[i]);
			leftframes[i].flip(true, false);
		} anims[0] = new Animation(duration, leftframes);
		anims[1] = new Animation(duration, rightframes);
		return anims;
	}
	

}
