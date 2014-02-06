package view;


import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;

import mapcomp.Block;
import mapcomp.IMapComponent;
import mapcomp.MapComponent;
import mapcomp.Scenery;
import items.Bullet;
import active.Gamer;
import active.Opponent;
import andmodel.ExArrow;
import andmodel.ExLivingState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

import standards.Arrow;
import standards.Axis;
import standards.LivingState;
import substrate.Collision;
import substrate.Filmtub;
import substrate.Platform;
import substrate.Zoom;
import utilities.ImageFetcher;

public class VirtuaRenderer implements Renderer {
	private Zoom view;
	private int width,height;
	private float scaleX,scaleY;
	private float FRAMEDURATION = 0.05f;
	
	private TextureRegion imblock;
	private TextureRegion imgamerleft;
	private TextureRegion imgamerright;
	
	private ShapeRenderer boundRenderer;

	private SpriteBatch batch;
	private Deque<TextureRegion> texturebuffer = new ArrayDeque<TextureRegion>();

	private OrthographicCamera cam;
	private boolean debug = false;
	private float viewwidth = 10f,viewheight = 8f;
	private ObjectRenderer objectcare;
	
	
	public VirtuaRenderer(Zoom zoom, SpriteBatch batch) {
		this.view = zoom;
		this.viewwidth = zoom.intwidth;
		this.viewheight = zoom.intheight;
		this.objectcare = new ObjectRenderer(batch);
		this.cam = new OrthographicCamera(viewwidth,viewheight);
		this.cam.translate(viewwidth/2f, viewheight/2f);
		this.boundRenderer = new ShapeRenderer();
		this.cam.update();
		this.batch = batch;
		loadTextures();
	}

	public void render() {
		batch.begin();
		drawComponents();
		batch.end();
		if(debug) {
			drawBounds();
		}
	}

	private void drawBounds() {
		Vector2 offset = this.view.getOrigin();
		cam.position.set(offset.x+viewwidth/2f, offset.y+viewheight/2f, 0);
		cam.update();
		boundRenderer.setProjectionMatrix(cam.combined);
		boundRenderer.begin(ShapeType.Line);
		boundRenderer.setColor(new Color(0, 1, 0, 1));
		for(IMapComponent mc : this.view.getRoots()) {
			boundRenderer.rect(mc.getBox().x /** scaleX*/, mc.getBox().y /** scaleY*/, mc.getBox().width/**scaleX*/, mc.getBox().height/**scaleY*/);
		}
		boundRenderer.end();
		
	}

	private void drawComponents() {
		Vector2 offset = this.view.getOrigin();
		Queue<IMapComponent> list = new PriorityQueue<IMapComponent>(this.view.getRoots());
		while(list.size()>0) {
			IMapComponent mc = list.poll();
			objectcare.getTextures(mc,texturebuffer);
			int l = texturebuffer.size();
			int i = 0;
			while(texturebuffer.size()!=0) {
				batch.draw(texturebuffer.pollFirst(), (mc.getCoord().x-offset.x+i) * scaleX, (mc.getCoord().y-offset.y) * scaleY, mc.getBreadth(Axis.X)*scaleX/l, mc.getBreadth(Axis.Y)*scaleY);
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

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
		this.scaleX = (float) w/viewwidth;
		this.scaleY = (float) h/viewheight;
		
	}

	@Override
	public void toogleDebug() {
		this.debug  = !this.debug;		
	}

	@Override
	public SpriteBatch getBatch() {
		return batch;
	}

}
