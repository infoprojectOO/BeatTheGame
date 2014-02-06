package view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Renderer {
	void render();

	void toogleDebug();

	SpriteBatch getBatch();
}
