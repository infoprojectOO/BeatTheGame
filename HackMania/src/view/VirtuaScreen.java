package view;

import substrate.Virtua;
import substrate.Zoom;

import android.drm.DrmStore.Action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import control.ActionController;
import control.Dynamics;

public class VirtuaScreen implements Screen {
	private SpriteBatch batch;
	private SceneFrame stage;
	private Texture background;
	private int width, height;
	private VirtuaRenderer renderer;
	private Button button;
	private Virtua world;

	public VirtuaScreen(Virtua virtua) {
		batch = new SpriteBatch();
		this.renderer = new VirtuaRenderer(virtua.getView(),batch);
		createstage(virtua);
		this.world = virtua;
		background = new Texture(Gdx.files.internal("background/hackbacks.jpg"));
	}

	private void createstage(Virtua virtua) {
		this.stage = new SceneFrame(virtua,batch);
		this.stage.setBackStage(renderer);
	}

	@Override
	public void render(float delta) {
		BitmapFont font = new BitmapFont();
		CharSequence seq = String.valueOf(delta);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0, width, height);
		font.draw(batch, seq, 10, height*9/10);
		batch.end();
		world.update(delta);
		renderer.render();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		this.renderer.setSize(width,height);
		this.stage.setViewport(width, height, true);
		this.width = width;
		this.height = height;

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);

	}

}
