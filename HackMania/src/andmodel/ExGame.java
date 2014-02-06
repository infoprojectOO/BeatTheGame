package andmodel;

import view.ExScreen;

import com.badlogic.gdx.Game;

public class ExGame extends Game {

	@Override
	public void create() {
		setScreen(new ExScreen());

	}

}
