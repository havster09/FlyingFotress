package com.flyingfotress.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

public abstract class EntityAnimatedSprite {
    private final AnimatedSprite animatedSprite;
    protected Vector2 pos, direction;

    public EntityAnimatedSprite(AnimatedSprite animatedSprite, Vector2 pos, Vector2 direction) {
        this.pos = pos;
        this.direction = direction;
        this.animatedSprite = animatedSprite;
    }

    public abstract void update();

    public void render(SpriteBatch sb) {
        animatedSprite.draw(sb);
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

}
