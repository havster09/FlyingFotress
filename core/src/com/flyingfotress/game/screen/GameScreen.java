package com.flyingfotress.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.flyingfotress.game.FlyingFotress;
import com.flyingfotress.game.TextureManager;
import com.flyingfotress.game.camera.OrthoCamera;
import com.flyingfotress.game.entity.EntityManager;
import com.flyingfotress.game.entity.Player;
import com.sun.org.apache.bcel.internal.Constants;

public class GameScreen extends Screen {
    private TextureRegion textureRegion;
    private Rectangle textureRegionBounds1;
    private Rectangle textureRegionBounds2;
    private EntityManager entityManager;
    private TiledMap tiledMap;
    private MapRenderer tiledMapRenderer;
    private Texture textureBg;
    private float speed = 3f;
    private float delta = 2f;
    private OrthographicCamera camera;

    @Override
    public void create() {
        camera = new OrthographicCamera(FlyingFotress.WIDTH,FlyingFotress.HEIGHT);
        camera.setToOrtho(false, FlyingFotress.WIDTH, FlyingFotress.HEIGHT);

        entityManager = new EntityManager(20, camera);
        tiledMap = new TmxMapLoader().load("test_bg.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        textureBg = new Texture(Gdx.files.internal("test_bg.jpg"));

         textureRegion = new TextureRegion(textureBg);
        textureRegionBounds1 = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        textureRegionBounds2 = new Rectangle(0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void update() {
        camera.update();

        // tiledMapRenderer.setView(camera);
        // tiledMapRenderer.render();

        if (bottomBoundsReached(delta)) {
            resetBounds();
        }
        else {
            updateYBounds(-delta);
        }

        entityManager.update();

        camera.update();

    }

    private boolean bottomBoundsReached(float delta) {
        return (textureRegionBounds2.y - (delta * speed)) <= 0;
    }

    private void resetBounds() {
        textureRegionBounds1 = textureRegionBounds2;
        textureRegionBounds2 = new Rectangle(0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void updateYBounds(float delta) {
         textureRegionBounds1.y += delta * speed;
         textureRegionBounds2.y += delta * speed;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        sb.draw(textureRegion, textureRegionBounds1.x, textureRegionBounds1.y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.draw(textureRegion, textureRegionBounds2.x, textureRegionBounds2.y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        entityManager.render(sb);
        sb.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.update();
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
