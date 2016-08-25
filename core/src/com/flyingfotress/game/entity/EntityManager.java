package com.flyingfotress.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.flyingfotress.game.FlyingFotress;
import com.flyingfotress.game.TextureManager;
import com.flyingfotress.game.camera.OrthoCamera;
import com.flyingfotress.game.screen.GameOverScreen;
import com.flyingfotress.game.screen.ScreenManager;

public class EntityManager {
    private final Array<Entity> entities = new Array<Entity>();
    public final Player player;

    public EntityManager(int amount, OrthoCamera camera) {
        player = new Player(new Vector2(Gdx.graphics.getWidth()/2 - TextureManager.PLAYER.getWidth()/2, Gdx.graphics.getHeight()/2 - TextureManager.PLAYER.getHeight()), new Vector2(0, 0), this, camera);
        for(int i = 0; i < amount; i++) {
            float x = MathUtils.random(0, FlyingFotress.WIDTH - TextureManager.ENEMY.getWidth());
            float y = MathUtils.random(FlyingFotress.HEIGHT, FlyingFotress.HEIGHT * 2);
            float speed = MathUtils.random(10, 20);
            addEntity(new Enemy(new Vector2(x, y), new Vector2(0, -speed)));
        }
    }
    public void update() {
        for(Entity e: entities) {
            e.update();
        }
        player.update();
        checkCollisions();
        removeBulletOffScreen();
    }

    public void render(SpriteBatch sb) {
        for(Entity e: entities) {
            e.render(sb);
        }
        player.render(sb);
        // player.font.draw(sb, player.message, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    private Array<Enemy> getEnemies() {
        Array<Enemy> ret = new Array<Enemy>();
        for(Entity e : entities) {
            if (e instanceof Enemy) {
                ret.add((Enemy) e);
            }
        }
        return ret;
    }

    private Array<Bullet> getBullets() {
        Array<Bullet> ret = new Array<Bullet>();
        for(Entity e : entities) {
            if (e instanceof Bullet) {
                ret.add((Bullet) e);
            }
        }
        return ret;
    }

    private void removeBulletOffScreen() {
        for(Bullet m: getBullets()) {
            if(m.checkEnd()) {
                entities.removeValue(m, false);
            }
        }
    }

    private void checkCollisions() {
        for(Enemy e: getEnemies()) {
            for(Bullet m: getBullets()) {
                if(e.getBounds().overlaps(m.getBounds())) {
                    entities.removeValue(e, false);
                    entities.removeValue(m, false);
                    if(gameOver()) {
                        ScreenManager.setScreen(new GameOverScreen(true));
                    }
                }
            }
            if(e.getBounds().overlaps(player.getBounds())) {
                // ScreenManager.setScreen(new GameOverScreen(false));
            }
        }
    }

    public boolean gameOver() {
        return getEnemies().size < 1;
    }

}
