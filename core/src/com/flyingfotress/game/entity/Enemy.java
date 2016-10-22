package com.flyingfotress.game.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.flyingfotress.game.FlyingFotress;
import com.flyingfotress.game.TextureManager;

public class Enemy extends EntitySprite {
    public Enemy(Vector2 pos, Vector2 direction) {
        super(TextureManager.ENEMY_SPRITE, pos, direction);

    }
    @Override
    public void update() {
        pos.add(direction);
        if(pos.y <= -TextureManager.ENEMY_SPRITE.getHeight()) {
            float x = MathUtils.random(0, FlyingFotress.WIDTH - TextureManager.ENEMY_SPRITE.getWidth());
            pos.set(new Vector2(x, FlyingFotress.HEIGHT));
        }
    }
}
