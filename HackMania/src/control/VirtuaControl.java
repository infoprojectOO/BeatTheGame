package control;

import substrate.Virtua;
import view.VirtuaScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class VirtuaControl extends Game {
	private Screen gamescreen, pausescreen, menuscreen;
	private Virtua world;
	private boolean paused;

	@Override
	public void create() {
		this.world = new Virtua();
		this.gamescreen = new VirtuaScreen(this.world);
		setScreen(this.gamescreen);
//		this.gamescreen.render(0);
//		paused = true;
	}

//	@Override
//	public void resize(int width, int height) {
//		// TODO Auto-generated method stub
//
//	}
//
	@Override
	public void render() {
		if(!paused) {
			super.render();
		}
	}
//
	@Override
	public void pause() {
		paused = true;
		super.pause();

	}
//
	@Override
	public void resume() {
		paused = false;
		super.resume();
	}
//
//	@Override
//	public void dispose() {
//		// TODO Auto-generated method stub
//
//	}

}
