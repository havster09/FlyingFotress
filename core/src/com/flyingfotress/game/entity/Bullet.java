package com.flyingfotress.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.flyingfotress.game.SpriteManager;
import com.flyingfotress.game.TextureManager;

public class Bullet extends EntitySprite {

    public Bullet(Vector2 pos, Vector2 direction) {
        super(SpriteManager.BULLET_SPRITE, pos, direction);
    }

    @Override
    public void update() {
        pos.add(direction);
    }

    public boolean checkEnd() {
        return (pos.y >= Gdx.graphics.getHeight() || pos.y < 0 || pos.x >= Gdx.graphics.getWidth() || pos.x < 0);
    }
}
