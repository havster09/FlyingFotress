package com.flyingfotress.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.flyingfotress.game.FlyingFotress;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Flying Fotress";
		config.width = FlyingFotress.WIDTH;
		config.height = FlyingFotress.HEIGHT;
		new LwjglApplication(new FlyingFotress(), config);
	}
}
