package com.flyingfotress.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

public abstract class EntityAnimatedSprite {
    private final AnimatedSprite animatedSprite;
    private Animation animation;
    protected Vector2 pos, direction;
    protected float x, y;

    public EntityAnimatedSprite(AnimatedSprite animatedSprite, Vector2 pos, Vector2 direction, float x, float y) {
        this.pos = pos;
        this.direction = direction;
        this.animatedSprite = animatedSprite;
        this.x = x;
        this.y = y;
    }

    public EntityAnimatedSprite(Animation animation, Vector2 pos, Vector2 direction, float x, float y) {
        this.pos = pos;
        this.direction = direction;
        this.animation = animation;
        this.x = x;
        this.y = y;
        this.animatedSprite = new AnimatedSprite(animation);
    }

    public abstract void update();

    public void render(SpriteBatch sb) {
        animatedSprite.setPosition(pos.x, pos.y);
        animatedSprite.draw(sb);
        animatedSprite.setScale(1f,1f);
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
