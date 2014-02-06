package game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import control.VirtuaControl;

public class VirtuaLauncher {

	public static void main(String[] args) {
		new LwjglApplication(new VirtuaControl(), "Virtua", 480, 320, true);
	}

}
