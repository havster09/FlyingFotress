package com.flyingfotress.game.entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.flyingfotress.game.AnimatedSpriteManager;
import com.flyingfotress.game.FlyingFotress;

import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

public class Flak {
    private final AnimatedSprite animatedSprite;
    protected Vector2 pos, direction;
    protected float x, y, speed;

    public Flak() {
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("ff08102016.atlas"));
        Array<TextureAtlas.AtlasRegion> region = textureAtlas.findRegions("flak_e");
        Animation animation = new Animation(1 / 30f, region, Animation.PlayMode.LOOP);

        this.animatedSprite =  new AnimatedSprite(animation);
        this.speed = 3f;
        this.x = MathUtils.random(0, FlyingFotress.WIDTH - AnimatedSpriteManager.FLAK.getWidth());
        this.y = MathUtils.random(0, FlyingFotress.HEIGHT);
        this.pos = new Vector2(this.x, this.y);
        this.direction = new Vector2(0, -speed);
    }

    public void update() {
        pos.add(direction);
        if(pos.y <= -AnimatedSpriteManager.FLAK.getHeight()) {
            float x = MathUtils.random(0, FlyingFotress.WIDTH - AnimatedSpriteManager.FLAK.getWidth());
            pos.set(new Vector2(x, FlyingFotress.HEIGHT));
        }
    }

    public void render(SpriteBatch sb) {
        animatedSprite.setPosition(pos.x, pos.y);
        animatedSprite.draw(sb);
        animatedSprite.setScale(.5f,.5f);
    }

    public Vector2 getPosition() {
        return pos;
    }

    public void setDirection(float x, float y) {
        direction.set(x, y);
        direction.scl(Gdx.graphics.getDeltaTime());
    }

    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y - animatedSprite.getHeight(), animatedSprite.getWidth(), animatedSprite.getHeight());
    }

    public boolean checkAnimationFinished() {
        Animation animation = animatedSprite.getAnimation();
        return animatedSprite.getTime() > animation.getAnimationDuration();
    }

    public void setNewPosition(float x, float y) {
        pos.x = x;
        pos.y = y;
        animatedSprite.setTime(0f);
    }
}
