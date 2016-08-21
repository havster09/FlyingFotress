package com.flyingfotress.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flyingfotress.game.camera.OrthoCamera;

public class GameOverScreen extends Screen {
    private OrthoCamera camera;

    public GameOverScreen(boolean won) {
        if(won) {
            System.out.println("won game");
        }
        else {
            System.out.println("lost game");
        }
    }

    @Override
    public void create() {
        camera = new OrthoCamera();
        camera.resize();
    }

    @Override
    public void update() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.isTouched()) {
                ScreenManager.setScreen(new GameScreen());
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        sb.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
