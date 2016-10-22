package com.flyingfotress.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.flyingfotress.game.AnimatedSpriteManager;
import com.flyingfotress.game.FlyingFotress;
import com.flyingfotress.game.TextureManager;
import com.flyingfotress.game.screen.GameOverScreen;
import com.flyingfotress.game.screen.ScreenManager;

import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

public class EntityManager {
    private final Array<EntitySprite> entities_sprites = new Array<EntitySprite>();
    private final Array<Flak> entities_animated_sprites = new Array<Flak>();
    private final String[] flak_collection = {
            "flak_a",
            "flak_b",
            "flak_c",
            "flak_d",
            "flak_e",
            "flak_f"
    };
    public final Player player;

    public EntityManager(int amount, OrthographicCamera camera) {
        player = new Player(new Vector2(Gdx.graphics.getWidth()/2 - TextureManager.PLAYER.getWidth()/2, Gdx.graphics.getHeight()/2 - TextureManager.PLAYER.getHeight()), new Vector2(0, 0), this, camera);
        for(int i = 0; i < amount; i++) {
            float x = MathUtils.random(0, FlyingFotress.WIDTH - TextureManager.ENEMY.getWidth());
            float y = MathUtils.random(FlyingFotress.HEIGHT, FlyingFotress.HEIGHT * 2);
            float speed = MathUtils.random(10, 20);
            addEntitySprite(new Enemy(new Vector2(x, y), new Vector2(0, -speed)));
        }
        spawnFlak(10);
    }

    public void spawnFlak(int amount) {
        for(int i = 0; i < amount*2; i++) {
            int itemNum = MathUtils.random(0,flak_collection.length-1);
            TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal(flak_collection[itemNum]+ ".pack"));
            Array<TextureAtlas.AtlasRegion> region = textureAtlas.findRegions(flak_collection[itemNum]);
            Animation animation = new Animation(1 / 30f, region, Animation.PlayMode.LOOP);
            float x = MathUtils.random(0, FlyingFotress.WIDTH);
            float y = MathUtils.random(0, FlyingFotress.HEIGHT);
            float speed = 3;
            final Flak flak =  new Flak(animation, new Vector2(x, y), new Vector2(0, -speed), x, y);
            new Timer().schedule(new Timer.Task() {
                @Override
                public void run() {
                    addEntityAnimatedSprite(flak);
                }
            }, MathUtils.random(0, 5));

        }
    }

    public void update() {
        for(EntitySprite es: entities_sprites) {
            es.update();
        }
        for(EntityAnimatedSprite eas: entities_animated_sprites) {
            eas.update();
        }

        player.update();
        checkCollisions();
        removeBulletOffScreen();
    }

    public void render(SpriteBatch sb) {
        for(EntitySprite es: entities_sprites) {
            es.render(sb);
        }

        for(EntityAnimatedSprite eas: entities_animated_sprites) {
            eas.render(sb);
            if(eas.checkAnimationFinished()) {
                float x = MathUtils.random(0, FlyingFotress.WIDTH);
                float y = MathUtils.random(0, FlyingFotress.HEIGHT);
                eas.setNewPosition(x, y);
            }
        }

        player.render(sb);
        // player.font.draw(sb, player.message, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
    }

    public void addEntitySprite(EntitySprite entitySprite) {
        entities_sprites.add(entitySprite);
    }

    public void addEntityAnimatedSprite(Flak entityAnimatedSprite) {
        entities_animated_sprites.add(entityAnimatedSprite);
    }

    private Array<Enemy> getEnemies() {
        Array<Enemy> ret = new Array<Enemy>();
        for(EntitySprite e : entities_sprites) {
            if (e instanceof Enemy) {
                ret.add((Enemy) e);
            }
        }
        return ret;
    }

    private Array<Bullet> getBullets() {
        Array<Bullet> ret = new Array<Bullet>();
        for(EntitySprite e : entities_sprites) {
            if (e instanceof Bullet) {
                ret.add((Bullet) e);
            }
        }
        return ret;
    }

    private void removeBulletOffScreen() {
        for(Bullet m: getBullets()) {
            if(m.checkEnd()) {
                entities_sprites.removeValue(m, false);
            }
        }
    }

    private void checkCollisions() {
        for(Enemy e: getEnemies()) {
            for(Bullet m: getBullets()) {
                if(e.getBounds().overlaps(m.getBounds())) {
                    entities_sprites.removeValue(e, false);
                    entities_sprites.removeValue(m, false);
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
