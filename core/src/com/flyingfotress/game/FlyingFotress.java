package com.flyingfotress.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flyingfotress.game.screen.GameScreen;
import com.flyingfotress.game.screen.ScreenManager;

public class FlyingFotress extends ApplicationAdapter implements ApplicationListener {
	public static int WIDTH, HEIGHT;
	SpriteBatch batch;
	
	@Override
	public void create () {
		WIDTH  = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		ScreenManager.setScreen(new GameScreen());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(ScreenManager.getCurrentScreen() != null) {
			ScreenManager.getCurrentScreen().update();
		}

		if(ScreenManager.getCurrentScreen() != null) {
			ScreenManager.getCurrentScreen().render(batch);
		}
	}
	
	@Override
	public void dispose () {
		if(ScreenManager.getCurrentScreen() != null) {
			ScreenManager.getCurrentScreen().dispose();
		}
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		if(ScreenManager.getCurrentScreen() != null) {
			ScreenManager.getCurrentScreen().resize(width, height);
		}
	}

	@Override
	public void pause () {
		if(ScreenManager.getCurrentScreen() != null) {
			ScreenManager.getCurrentScreen().pause();
		}
	}

	@Override
	public void resume () {
		if(ScreenManager.getCurrentScreen() != null) {
			ScreenManager.getCurrentScreen().resume();
		}
	}
}
