package mainview;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;

import mainbuilder.MapAssistant;
import mapcomp.IMapComponent;
import mapcomp.Scenery;

import standards.Axis;
import substrate.Filmtub;
import substrate.Zoom;
import utilities.Priorator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

import control.Observer;

public class StaticVirtuaRenderer implements StaticRenderer, Observer {
	private Filmtub view;
	private int width,height;
	private int scaleX=10,scaleY=10;

	private Deque<Image> texturebuffer = new ArrayDeque<Image>();

	private boolean bounds = false;
	private StaticObjectRenderer objectcare;
	private boolean grid = false;
	private Rectangle highlight;
	private MapAssistant help;

	
	
	public StaticVirtuaRenderer(Filmtub map) {
		this.view = map;
		this.objectcare = new StaticObjectRenderer();
		loadTextures();
	}

	public void render(Graphics g) {
		drawComponents(g);
		if(bounds) {
			drawBounds(g);
		} if(grid) {
			drawGrid(g);
		} drawHighliht(g);
	}

	private void drawHighliht(Graphics g) {
		g.setColor(Color.GREEN);
		if(highlight != null) {
			g.drawRect(highlight.x, highlight.y, highlight.width, highlight.height);
		}
		
	}

	private void drawGrid(Graphics g) {
		g.setColor(Color.RED.brighter());
		for(int x = 1;x<view.width;x++) {
			for(int y = 1; y<view.height;y++) {
				g.drawLine(x*scaleX, 0, x*scaleX, height);
				g.drawLine(0, y*scaleY, width, y*scaleY);
			}
		}
		
	}

	private void drawBounds(Graphics g) {
		g.setColor(Color.CYAN.darker());
		Queue<IMapComponent> list = new PriorityQueue<IMapComponent>(100,new Priorator());
		list.addAll(this.view.getRoots().keySet());
		list.addAll(this.view.getMobiles());
		for(IMapComponent imc : list) {
			com.badlogic.gdx.math.Rectangle rect = imc.getBox();
			g.drawRect((int) (rect.x*scaleX), height - (int) ((rect.y+rect.height)*scaleY), (int) (rect.width*scaleX), (int) (rect.height*scaleY));
		}
		
	}

	private void drawComponents(Graphics g) {
		Vector2 offset = Vector2.Zero;
		Queue<IMapComponent> list = new PriorityQueue<IMapComponent>(100,new Priorator());
		list.addAll(this.view.getRoots().keySet());
		list.addAll(this.view.getMobiles());
		while(list.size()>0) {
			IMapComponent mc = list.poll();
			objectcare.getTextures(mc,texturebuffer);
			int l = texturebuffer.size();
			int i = 0;
			while(texturebuffer.size()!=0) {
				g.drawImage(texturebuffer.pollFirst(), (int)((mc.getCoord().x-offset.x+i) * scaleX), height-(int)((mc.getCoord().y-offset.y+mc.getBreadth(Axis.Y)) * scaleY), (int)(mc.getBreadth(Axis.X)*scaleX/l), (int)(mc.getBreadth(Axis.Y)*scaleY),null);
				i++;
			}
//			System.out.println(mc);
		}
		
	}

	private void loadTextures() {
//		TextureRegion[] leftframes = new TextureRegion[8];
//		TextureRegion[] rightframes = new TextureRegion[8];
//		TextureRegion[] leftframesevil = new TextureRegion[8];
//		TextureRegion[] rightframesevil = new TextureRegion[8];
//		for(int i=0;i<8;i++) {
//			rightframes[i] = new TextureRegion(new Texture(Gdx.files.internal("character/"+(i+1)+"a.png")));
//			rightframesevil[i] = new TextureRegion(new Texture(Gdx.files.internal("character/"+(i+1)+"a_evil.png")));
//			leftframes[i] = new TextureRegion(rightframes[i]);
//			leftframes[i].flip(true, false);
//			leftframesevil[i] = new TextureRegion(rightframesevil[i]);
//			leftframesevil[i].flip(true, false);
//		}
//		tree = ImageFetcher.getTexture("background/tree_ground.png");
//		grass = ImageFetcher.getTexture("background/grass.png");
//		rock = ImageFetcher.getTexture("background/rock_ground.png");
//		bullet =  ImageFetcher.getTexture("images/bullet.png");
//		imblock =  ImageFetcher.getTexture(ImageFetcher.BLOCK);
//		imevilright =  ImageFetcher.getTexture(ImageFetcher.EVIL);
//		imevilleft = new TextureRegion(imevilright);
//		imevilleft.flip(true, false);
//		imgamerright =  ImageFetcher.getTexture(ImageFetcher.GAMER);
//		imgamerleft = new TextureRegion(imgamerright);
//		imgamerleft.flip(true, false);
		
	}

	public void bounds(boolean state) {
		this.bounds = state;		
	}

	public void setScale(int resX, int resY) {
		this.scaleX = resX;
		this.scaleY = resY;
		this.width = view.width * scaleX;
		this.height = view.height * scaleY;
	}

	public void grid(boolean state) {
		this.grid  = state;
		
	}

	public void highlight(IMapComponent imc) {
		if(imc == null) {
			highlight = null;
		} else {
			com.badlogic.gdx.math.Rectangle rect = imc.getBox();
			this.highlight = new Rectangle((int)(rect.x*scaleX),(int)((view.height-rect.y-rect.height)*scaleY),(int)(rect.width*scaleX),(int)(rect.height*scaleY));
		}		
	}

	public void moveselection(Point dp) {
		System.out.println(dp);
		Point org = this.highlight.getLocation();
		this.highlight.setLocation(org.x+dp.x,org.y+dp.y);
		
	}

	public void help(MapAssistant mapaid) {
		this.help = mapaid;
	}

	@Override
	public void update() {
		String code = this.help.getChangeCode();
		if(code == MapAssistant.HIGHLIGHT) {
			com.badlogic.gdx.math.Rectangle rect = help.getSelection();
			this.highlight = new Rectangle((int)(rect.x*scaleX),(int)((view.height-rect.y-rect.height)*scaleY),(int)(rect.width*scaleX),(int)(rect.height*scaleY));
		} else if(code == MapAssistant.SIZE) {
			this.setScale(scaleX, scaleY);
		}
		
	}

	

}
