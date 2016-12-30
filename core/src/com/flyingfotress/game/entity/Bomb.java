package com.flyingfotress.game.entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.flyingfotress.game.AnimatedSpriteManager;

public class Bomb extends EntityAnimatedSprite{
    public Bomb(Animation animation, Vector2 pos, Vector2 direction, float x, float y, float a, float b, boolean isAlive) {
        super(animation, pos, direction, x, y, a, b, isAlive);
    }

    @Override
    public void update() {
        pos.add(direction);
    }
}


