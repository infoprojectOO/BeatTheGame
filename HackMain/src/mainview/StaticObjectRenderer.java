package mainview;

import java.awt.Image;
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
import modules.GraphicImage;
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

public class StaticObjectRenderer {
	private Map<String,Image[]> images = new HashMap<String, Image[]>();
//	private Map<String,Appearance> appearances = new HashMap<String, Appearance>();

	public StaticObjectRenderer() {
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
	
	public void getTextures(IMapComponent imc,Deque<Image> texturebuffer) {
		if(imc instanceof Movable) {
			texturebuffer.add(getTexture((Movable)imc));
			return;
		} else if(imc instanceof Throwable) {
			texturebuffer.add(getTexture((Throwable) imc));
			return;
		} else if(imc instanceof Platform) {
			texturebuffer.addAll(getTextures((Platform) imc));
			return;
		}
		Image[] textures = images.get(imc.getID());
		Image texture;
		if(textures == null) {
			texture = GraphicImage.getImage(imc.getRootFolder()+"/"+imc.getID()+".png");
			textures = new Image[1];
			textures[0] = texture;
			images.put(imc.getID(), textures);
		} texturebuffer.addLast(textures[0]);
	}
	
	private Collection<? extends Image> getTextures(Platform m) {
		Image[] tr = images.get(m.getID());
		if(tr==null) {
			tr = new Image[3];
			tr[0] = GraphicImage.getImage("mapcomp/"+m.getID()+"_left.png");
			tr[1] = GraphicImage.getImage("mapcomp/"+m.getID()+"_mid.png");
			tr[2] = GraphicImage.getImage("mapcomp/"+m.getID()+"_right.png");
			this.images.put(m.getID(), tr);
		} Collection<Image> res = new ArrayList<Image>();
		res.add(tr[0]);
		for(int i=1;i<=m.getLength()-2;i++) {
			res.add(tr[1]);
		} res.add(tr[2]);
		return res;
	}
	
	private Image getTexture(Throwable thrown) {
		String imname = thrown.getID();
		Image[] textures; 
		Image texture;
		textures = images.get(imname);
		if(textures==null) {
			textures = GraphicImage.getTextures(thrown.getRootFolder()+"/"+imname+".png");
			images.put(imname, textures);
		} texture = textures[thrown.getArrow().getOrder()];
		return texture;
	}

	public Image getTexture(Movable anim) {
		String imname = anim.getID();
		Image[] textures; 
		Image texture = null;
		textures = images.get(imname);
		if(textures==null) {
			textures = GraphicImage.getTextures(anim.getRootFolder()+"/"+imname+"_idle.png");
			images.put(imname, textures);
		} texture = textures[anim.getArrow().getOrder()];
	return texture;
	}

}
