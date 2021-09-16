package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.DanceFans;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new DanceFans(), config);

		config.title = "Dance Fans";
		config.width = 1600;
		config.height = 900;
		new LwjglApplication(new DanceFans(), config);
	}
}
