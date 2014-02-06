package help;

import java.util.Arrays;
import java.util.List;

import geometry.Geometry;
import utilities.ArrayUtils;
import utilities.ImageFetcher;
import view.SceneFrame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class QuickTestGraphics implements ApplicationListener {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new LwjglApplication(new QuickTestGraphics(),"QuickTest", 400,500,true);

	}
	
	private Stage stage;
	private ShapeRenderer shaperenderer;
	private SpriteBatch spritebatch;
	private Array<SpriteDrawable> sprites = new Array<SpriteDrawable>();
	private Array<Texture> textures = new Array<Texture>();
	private float scaleX,scaleY;
	
	public QuickTestGraphics() {
		
	}
	
	@Override
	public void create () {
		stage = new Stage();
		shaperenderer = new ShapeRenderer();
		spritebatch = new SpriteBatch();
		sprites.add(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("gadgets/up_on.png")))));
		sprites.add(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("gadgets/down_on.png")))));
		textures.add(new Texture(Gdx.files.internal("gadgets/up_on.png")));
		
	       
	}

	@Override
	public void resize (int width, int height) {
	        stage.setViewport(width, height, true);
	        scaleX = width/10;
	        scaleY = height/10;
	}

	@Override
	public void render () {
	        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
//	        stage.act(Gdx.graphics.getDeltaTime());
//	        stage.draw();
//	        Polygon p1 = new Polygon();
//	        Rectangle rect3 = new Rectangle(0.5f,2,1f,1f);
//			Rectangle rect1 = new Rectangle(0f,1f,1f,1f);
//			Rectangle rect2 = new Rectangle(0.5f,1.5f,1f,1f);
//			float[] vert = {rect1.x,rect1.y,rect1.x+rect1.width,rect1.y,rect1.x,rect1.y+rect1.height};
//			Geometry.joinBasis(p1, rect1);
//			Geometry.joinBasis(p1, rect2);
//			Geometry.joinBasis(p1, rect3);
//			float[] ve  = ArrayUtils.addAll(Geometry.getRectVertices(rect1),Geometry.getRectVertices(rect2));
//			shaperenderer.begin(ShapeType.Line);
//			shaperenderer.setColor(new Color(0, 1, 0, 1));
//			float[] v = p1.getVertices();
//			scaleup(v);
//			shaperenderer.polygon(v);
////			shaperenderer.rect(rect1.x*scaleX, rect1.y*scaleY, rect1.width*scaleX, rect1.height*scaleY);
//			rect1.merge(rect2);
//			shaperenderer.setColor(new Color(0, 0, 1, 1));
////			shaperenderer.rect(rect2.x*scaleX, rect2.y*scaleY, rect2.width*scaleX, rect2.height*scaleY);
//			shaperenderer.setColor(new Color(1, 0, 0, 1));
////			shaperenderer.rect(rect1.x*scaleX, rect1.y*scaleY, rect1.width*scaleX, rect1.height*scaleY);
//			shaperenderer.end();
			spritebatch.begin();
			for(SpriteDrawable sd : sprites) {
//				sd.draw(spritebatch, sd.getSprite().getX(), sd.getSprite().getY(), sd.getSprite().getWidth(), sd.getSprite().getHeight());
				sd.getSprite().draw(spritebatch);
			}
			spritebatch.end();

//	        Table.drawDebug(stage); // This is optional, but enables debug lines for tables.
	}
	
	private void scaleup(float[] v) {
		for(int i=0;i<v.length/2;i++) {
			v[2*i] *=scaleX;
			v[2*i+1] *= scaleY;
		}
	}

	@Override
	public void dispose() {
	        stage.dispose();
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
