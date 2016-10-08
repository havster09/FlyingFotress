package com.flyingfotress.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.flyingfotress.game.AnimatedSpriteManager;
import com.flyingfotress.game.FlyingFotress;
import com.flyingfotress.game.TextureManager;
import com.flyingfotress.game.screen.GameOverScreen;
import com.flyingfotress.game.screen.ScreenManager;

import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

public class EntityManager {
    private final Array<EntityTexture> entities_textures = new Array<EntityTexture>();
    private final Array<Flak> entities_animated_sprites = new Array<Flak>();
    public final Player player;

    public EntityManager(int amount, OrthographicCamera camera) {
        player = new Player(new Vector2(Gdx.graphics.getWidth()/2 - TextureManager.PLAYER.getWidth()/2, Gdx.graphics.getHeight()/2 - TextureManager.PLAYER.getHeight()), new Vector2(0, 0), this, camera);
        for(int i = 0; i < amount; i++) {
            float x = MathUtils.random(0, FlyingFotress.WIDTH - TextureManager.ENEMY.getWidth());
            float y = MathUtils.random(FlyingFotress.HEIGHT, FlyingFotress.HEIGHT * 2);
            float speed = MathUtils.random(10, 20);
            addEntityTexture(new Enemy(new Vector2(x, y), new Vector2(0, -speed)));
        }
        spawnFlak(10);
    }

    public void spawnFlak(int amount) {
        for(int i = 0; i < amount*2; i++) {
            TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("ff08102016.atlas"));
            Array<TextureAtlas.AtlasRegion> region = textureAtlas.findRegions("flak_e");
            Animation animation = new Animation(1 / 30f, region, Animation.PlayMode.LOOP);
            AnimatedSprite animatedSprite = new AnimatedSprite(animation);
            float x = MathUtils.random(0, FlyingFotress.WIDTH - AnimatedSpriteManager.FLAK.getWidth());
            float y = MathUtils.random(0, FlyingFotress.HEIGHT);
            float speed = 3;
            Flak flak =  new Flak();
            addEntityAnimatedSprite(flak);
        }
    }

    public void update() {
        for(EntityTexture et: entities_textures) {
            et.update();
        }
        for(Flak eas: entities_animated_sprites) {
            eas.update();
        }
        player.update();
        checkCollisions();
        removeBulletOffScreen();
    }

    public void render(SpriteBatch sb) {
        for(EntityTexture et: entities_textures) {
            et.render(sb);
        }

        for(Flak eas: entities_animated_sprites) {
            eas.render(sb);
            if(eas.checkAnimationFinished()) {
                float x = MathUtils.random(0, FlyingFotress.WIDTH - AnimatedSpriteManager.FLAK.getWidth());
                float y = MathUtils.random(0, FlyingFotress.HEIGHT);
                eas.setNewPosition(x, y);
            }
        }

        player.render(sb);
        // player.font.draw(sb, player.message, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
    }

    public void addEntityTexture(EntityTexture entityTexture) {
        entities_textures.add(entityTexture);
    }

    public void addEntityAnimatedSprite(Flak entityAnimatedSprite) {
        entities_animated_sprites.add(entityAnimatedSprite);
    }

    private Array<Enemy> getEnemies() {
        Array<Enemy> ret = new Array<Enemy>();
        for(EntityTexture e : entities_textures) {
            if (e instanceof Enemy) {
                ret.add((Enemy) e);
            }
        }
        return ret;
    }

    private Array<Bullet> getBullets() {
        Array<Bullet> ret = new Array<Bullet>();
        for(EntityTexture e : entities_textures) {
            if (e instanceof Bullet) {
                ret.add((Bullet) e);
            }
        }
        return ret;
    }

    private void removeBulletOffScreen() {
        for(Bullet m: getBullets()) {
            if(m.checkEnd()) {
                entities_textures.removeValue(m, false);
            }
        }
    }

    private void checkCollisions() {
        for(Enemy e: getEnemies()) {
            for(Bullet m: getBullets()) {
                if(e.getBounds().overlaps(m.getBounds())) {
                    entities_textures.removeValue(e, false);
                    entities_textures.removeValue(m, false);
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
