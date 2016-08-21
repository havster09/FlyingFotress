package com.flyingfotress.game.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.flyingfotress.game.FlyingFotress;
import com.flyingfotress.game.TextureManager;
import com.flyingfotress.game.camera.OrthoCamera;
import com.flyingfotress.game.entity.EntityManager;
import com.flyingfotress.game.entity.Player;

public class GameScreen extends Screen {
    private OrthoCamera camera;
    private EntityManager entityManager;

    @Override
    public void create() {
        camera = new OrthoCamera();
        camera.resize();
        entityManager = new EntityManager(20, camera);
    }

    @Override
    public void update() {
        camera.update();
        entityManager.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        entityManager.render(sb);
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
