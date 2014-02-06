package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import mapcomp.Appearance;
import mapcomp.IMapComponent;
import mapcomp.MapComponent;
import items.Throwable;

import standards.Axis;
import standards.LivingState;
import substrate.Platform;
import utilities.ImageFetcher;
import utilities.TextureGroup;

import active.Gamer;
import active.Movable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class ObjectRenderer {
	private static final float FRAMEDURATION = 0.15f;
	private Map<String,TextureRegion[]> images = new HashMap<String, TextureRegion[]>();
	private Map<String,Animation[]> animations = new HashMap<String, Animation[]>();
	private Map<String,Appearance> appearances = new HashMap<String, Appearance>();
	private SpriteBatch batch;

	public ObjectRenderer(SpriteBatch batch) {
		this.batch = batch;	
	}

//	private void loadTextures(String path,String name) {
//		TextureAtlas atlas = ImageFetcher.getAtlas(path);
//		TextureGroup tg = new TextureGroup();
//		Appearance app = new Appearance();
//		for(AtlasRegion region : atlas.getRegions()) {
//			TextureRegion texture = new TextureRegion(region);
//			String s = region.name;
//			if(s.charAt(s.length()-1)=='a') {
//				String id = s.split("_")[1];
//				
//				tg.addRegion(texture, id);				
//			} else {
//				TextureRegion[] tr = new TextureRegion[2];
//				tr[0] = new TextureRegion(texture);
//				tr[0].flip(true, false);
//				tr[1] = texture;
//				images.put(name, tr);
//			}
//		} animations.put(name, ImageFetcher.animate(tg, FRAMEDURATION));
//		
//	}
	
	public void getTextures(IMapComponent imc,Deque<TextureRegion> deposit) {
		if(imc instanceof Movable) {
			deposit.add(getTexture((Movable)imc));
			return;
		} else if(imc instanceof Throwable) {
			deposit.add(getTexture((Throwable) imc));
			return;
		} else if(imc instanceof Platform) {
			deposit.addAll(getTextures((Platform) imc));
			return;
		}
		TextureRegion[] textures = images.get(imc.getID());
		TextureRegion texture;
		if(textures == null) {
			texture = ImageFetcher.getTexture(imc.getRootFolder()+"/"+imc.getID()+".png");
			textures = new TextureRegion[1];
			textures[0] = texture;
			images.put(imc.getID(), textures);
		} deposit.addLast(textures[0]);
	}
	
	private Collection<TextureRegion> getTextures(Platform m) {
		TextureRegion[] tr = images.get(m.getID());
		if(tr==null) {
			tr = new TextureRegion[3];
			tr[0] = ImageFetcher.getTexture("mapcomp/"+m.getID()+"_left.png");
			tr[1] = ImageFetcher.getTexture("mapcomp/"+m.getID()+"_mid.png");
			tr[2] = ImageFetcher.getTexture("mapcomp/"+m.getID()+"_right.png");
			this.images.put(m.getID(), tr);
		} ArrayList<TextureRegion> res = new ArrayList<TextureRegion>();
		res.add(tr[0]);
		for(int i=1;i<=m.getLength()-2;i++) {
			res.add(tr[1]);
		} res.add(tr[2]);
		return res;
	}
	
	private TextureRegion getTexture(Throwable thrown) {
		String imname = thrown.getID();
		TextureRegion[] textures; 
		TextureRegion texture;
		textures = images.get(imname);
		if(textures==null) {
			textures = ImageFetcher.getTextures(thrown.getRootFolder()+"/"+imname+".png");
			images.put(imname, textures);
		} texture = textures[thrown.getArrow().getOrder()];
		return texture;
	}

	public TextureRegion getTexture(Movable anim) {
		String imname = anim.getID();
		TextureRegion[] textures; 
		TextureRegion texture = null;
		if(anim.getState()==LivingState.IDLE) {
			textures = images.get(imname);
			if(textures==null) {
				textures = ImageFetcher.getTextures(anim.getRootFolder()+"/"+imname,imname+"_idle");
				images.put(imname, textures);
			} texture = textures[anim.getArrow().getOrder()];
		} else if(anim.getState()!=LivingState.WALK) {
			textures = images.get(imname);
			if(textures==null) {
				textures = ImageFetcher.getTextures(anim.getRootFolder()+"/"+imname,imname+"_idle");
				images.put(imname, textures);
			} texture = textures[anim.getArrow().getOrder()];
		} else if(anim.getState()==LivingState.WALK) {
			Animation[] movie = animations.get(imname);
			if(movie==null) {
				if(anim instanceof Gamer) {
					movie = ImageFetcher.animate(anim.getRootFolder()+"/"+imname,imname+"_walk", FRAMEDURATION);
				} else {
					movie = ImageFetcher.animate(imname,FRAMEDURATION);
				}
				animations.put(imname, movie);
			} texture = movie[anim.getArrow().getOrder()].getKeyFrame(anim.getStateTime(),true);
		} return texture;
	}

}
