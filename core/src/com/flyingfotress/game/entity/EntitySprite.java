package com.flyingfotress.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class EntitySprite {
    private final Sprite sprite;
    protected Vector2 pos, direction;
    public EntityManager entityManager;

    public EntitySprite(Sprite sprite, Vector2 pos, Vector2 direction) {
        this.sprite = sprite;
        this.pos = pos;
        this.direction = direction;
    }

    public abstract void update();

    public void render(SpriteBatch sb) {
        sb.draw(sprite, pos.x - sprite.getWidth()/2, pos.y - sprite.getHeight()/2);
    }

    public Vector2 getPosition() {
        return pos;
}

    public void setDirection(float x, float y) {
        direction.set(x, y);
        direction.scl(Gdx.graphics.getDeltaTime());
    }

    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y - sprite.getHeight(), sprite.getWidth(), sprite.getHeight());
    }

    public void setCenter(float x, float y) {
        sprite.setCenter(x, y);
    }

    public void setRotation(float degrees) {
        sprite.setRotation(degrees);
    }

}
