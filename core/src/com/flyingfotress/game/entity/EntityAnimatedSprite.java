package com.flyingfotress.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

public abstract class EntityAnimatedSprite {
    private final AnimatedSprite animatedSprite;
    public Boolean isAlive;
    private float a;
    private float b;
    private Animation animation;
    protected Vector2 pos, direction;
    protected float x, y;

    public EntityAnimatedSprite(AnimatedSprite animatedSprite, Vector2 pos, Vector2 direction, float x, float y) {
        this.isAlive = true;
        this.pos = pos;
        this.direction = direction;
        this.animatedSprite = animatedSprite;
        this.x = x;
        this.y = y;
    }

    public EntityAnimatedSprite(Animation animation, Vector2 pos, Vector2 direction, float x, float y, float a, float b, boolean isAlive) {
        this.isAlive = isAlive;
        this.pos = pos;
        this.direction = direction;
        this.animation = animation;
        this.x = x;
        this.y = y;
        this.a = a;
        this.b = b;
        this.animatedSprite = new AnimatedSprite(animation);
    }

    public abstract void update();

    public void render(SpriteBatch sb) {
        animatedSprite.setPosition(pos.x - animatedSprite.getWidth()/2, pos.y - animatedSprite.getHeight()/2);
        animatedSprite.draw(sb);
        animatedSprite.setScale(this.a,this.a);
        animatedSprite.setRotation(this.b);
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

    public boolean checkIsAlive() {
        return this.isAlive;
    }

    public boolean checkEnd() {
        return (pos.y >= Gdx.graphics.getHeight() || pos.y < 0 || pos.x >= Gdx.graphics.getWidth() || pos.x < 0);
    }

    public void setTime(float time) {
        animatedSprite.setTime(0);
    }

    public void setScale(float a, float b) {
        this.a = a;
        this.b = b;
    }

    public void setRotation(float rotation) {
        this.b = rotation;
    }

    public void setAnimation(Animation animation) {
        animatedSprite.setAnimation(animation);
    }
}
