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
import com.flyingfotress.game.SpriteManager;
import com.flyingfotress.game.TextureManager;
import com.flyingfotress.game.screen.GameOverScreen;
import com.flyingfotress.game.screen.ScreenManager;

import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

public class EntityManager {
    private final Array<EntitySprite> entities_sprites = new Array<EntitySprite>();
    private final Array<Flak> entities_animated_sprites_flak = new Array<Flak>();
    private final Array<Bomb> entities_animated_sprites_bomb = new Array<Bomb>();
    private final String[] flak_collection = {
            "flak_a",
            "flak_b",
            "flak_c",
            "flak_d",
            "flak_e",
            "flak_f"
    };

    private final String[] bomb_collection = {
            "bomb_a",
            "bomb_b",
            "bomb_c",
            "bomb_d"
    };
    public final Player player;

    public EntityManager(int amount, OrthographicCamera camera) {
        player = new Player(new Vector2(Gdx.graphics.getWidth() / 2 - SpriteManager.PLAYER_SPRITE.getWidth() / 2, Gdx.graphics.getHeight() / 2 - SpriteManager.PLAYER_SPRITE.getHeight() / 2), new Vector2(0, 0), this, camera);
        for (int i = 0; i < amount; i++) {
            float x = MathUtils.random(0, FlyingFotress.WIDTH - SpriteManager.ENEMY_SPRITE.getWidth());
            float y = MathUtils.random(FlyingFotress.HEIGHT, FlyingFotress.HEIGHT * 2);
            float speed = MathUtils.random(10, 20);
            addEntitySprite(new Enemy(new Vector2(x, y), new Vector2(0, -speed)));
        }
        spawnFlak(5);
        initBombs(10);
    }

    public void spawnFlak(int amount) {
        for (int i = 0; i < amount; i++) {
            int itemNum = MathUtils.random(0, flak_collection.length - 1);
            TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal(flak_collection[itemNum] + ".pack"));
            Array<TextureAtlas.AtlasRegion> region = textureAtlas.findRegions(flak_collection[itemNum]);
            Animation animation = new Animation(1 / 30f, region, Animation.PlayMode.LOOP);
            float x = MathUtils.random(0, FlyingFotress.WIDTH);
            float y = MathUtils.random(0, FlyingFotress.HEIGHT);
            float speed = 3;
            float a = MathUtils.random(1f, 2f);
            float b = MathUtils.random(0, 360);
            final Flak flak = new Flak(animation, new Vector2(x, y), new Vector2(0, -speed), x, y, a, b);
            new Timer().schedule(new Timer.Task() {
                @Override
                public void run() {
                    addEntityAnimatedSpriteFlak(flak);
                }
            }, MathUtils.random(0, 5));

        }
    }

    public Animation getAnimation() {
        int itemNum = MathUtils.random(0, bomb_collection.length - 1);
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal(bomb_collection[itemNum] + ".pack"));
        Array<TextureAtlas.AtlasRegion> region = textureAtlas.findRegions(bomb_collection[itemNum]);
        return new Animation(1 / 12f, region, Animation.PlayMode.NORMAL);
    }

    public void initBombs(int amount) {
        for (int i = 0; i < amount; i++) {
            Animation animation = getAnimation();
            float speed = 2;
            float a = MathUtils.random(2f, 3f);
            float b = MathUtils.random(0, 360);
            final Bomb bomb = new Bomb(animation, new Vector2(0, 0), new Vector2(0, -speed), 0, 0, a, b, false);
            addEntityAnimatedSpriteBomb(bomb);
        }
    }


    public void reSpawnBomb(final EntityAnimatedSprite animatedSprite, final float x, final float y) {
        final float a = MathUtils.random(2f, 3f);
        float b = MathUtils.random(0, 360);
        final float rotation = MathUtils.random(0, 360);

        final Animation animation = getAnimation();

        new Timer().schedule(new Timer.Task() {
            @Override
            public void run() {
                animatedSprite.setTime(0);
                animatedSprite.setScale(a,a);
                animatedSprite.setAnimation(animation);
                animatedSprite.setRotation(rotation);
                animatedSprite.setNewPosition(x, y);
                animatedSprite.isAlive = true;
            }
        }, MathUtils.random(1, 2));

    }

    public void spawnBomb(int amount, float x, float y) {
        for (int i = 0; i < amount; i++) {
            boolean recycleBomb = false;
            for (EntityAnimatedSprite easb : entities_animated_sprites_bomb) {
                if(!easb.checkIsAlive()){
                    reSpawnBomb(easb,x,y);
                    recycleBomb = true;
                    break;
                }
            }

            if(!recycleBomb) {
                Animation animation = getAnimation();
                float speed = 2;
                float a = MathUtils.random(2f, 3f);
                float b = MathUtils.random(0, 360);
                final Bomb bomb = new Bomb(animation, new Vector2(x, y), new Vector2(0, -speed), x, y, a, b, true);
                new Timer().schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        addEntityAnimatedSpriteBomb(bomb);
                    }
                }, MathUtils.random(1, 2));
            }
        }
    }

    public void update() {
        for (EntitySprite es : entities_sprites) {
            es.update();
        }
        for (EntityAnimatedSprite easf : entities_animated_sprites_flak) {
            easf.update();
        }

        // System.out.println(entities_animated_sprites_bomb.size);

        for (EntityAnimatedSprite easb : entities_animated_sprites_bomb) {
            if(easb.checkAnimationFinished() || easb.checkEnd()) {
                easb.isAlive = false;
            }
            else {
                easb.update();
            }
        }

        player.update();
        checkCollisions();
        removeBulletOffScreen();
        // removeBombs();
    }

    public void render(SpriteBatch sb) {
        for (EntitySprite es : entities_sprites) {
            es.render(sb);
        }

        for (EntityAnimatedSprite eas : entities_animated_sprites_flak) {
            eas.render(sb);
            if (eas.checkAnimationFinished()) {
                float x = MathUtils.random(0, FlyingFotress.WIDTH);
                float y = MathUtils.random(0, FlyingFotress.HEIGHT);
                eas.setNewPosition(x, y);
            }
        }

        for (EntityAnimatedSprite eas : entities_animated_sprites_bomb) {
            if (eas.checkIsAlive()) {
                eas.render(sb);
            } else {
                // System.out.println("is dead");
            }

        }

        player.render(sb);
        // player.font.draw(sb, player.message, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
    }

    public void addEntitySprite(EntitySprite entitySprite) {
        entities_sprites.add(entitySprite);
    }

    public void addEntityAnimatedSpriteFlak(Flak entityAnimatedSprite) {
        entities_animated_sprites_flak.add(entityAnimatedSprite);
    }

    public void addEntityAnimatedSpriteBomb(Bomb entityAnimatedSprite) {
        entities_animated_sprites_bomb.add(entityAnimatedSprite);
    }

    private Array<Enemy> getEnemies() {
        Array<Enemy> ret = new Array<Enemy>();
        for (EntitySprite e : entities_sprites) {
            if (e instanceof Enemy) {
                ret.add((Enemy) e);
            }
        }
        return ret;
    }

    private Array<Bullet> getBullets() {
        Array<Bullet> ret = new Array<Bullet>();
        for (EntitySprite e : entities_sprites) {
            if (e instanceof Bullet) {
                ret.add((Bullet) e);
            }
        }
        return ret;
    }

    private void removeBulletOffScreen() {
        for (Bullet m : getBullets()) {
            if (m.checkEnd()) {
                entities_sprites.removeValue(m, false);
            }
        }
    }

    private Array<Bomb> getBombs() {
        Array<Bomb> ret = new Array<Bomb>();
        for (EntityAnimatedSprite e : entities_animated_sprites_bomb) {
            if (e instanceof Bomb) {
                ret.add((Bomb) e);
            }
        }
        return ret;
    }

    private void removeBombs() {
        for (Bomb m : getBombs()) {
            entities_animated_sprites_bomb.removeValue(m, false);
        }
    }

    private void checkCollisions() {
        for (Enemy e : getEnemies()) {
            for (Bullet m : getBullets()) {
                if (e.getBounds().overlaps(m.getBounds())) {
                    entities_sprites.removeValue(e, false);
                    entities_sprites.removeValue(m, false);
                    if (gameOver()) {
                        // ScreenManager.setScreen(new GameOverScreen(true));
                    }
                }
            }
            if (e.getBounds().overlaps(player.getBounds())) {
                // ScreenManager.setScreen(new GameOverScreen(false));
            }
        }
    }

    public boolean gameOver() {
        return getEnemies().size < 1;
    }

}
