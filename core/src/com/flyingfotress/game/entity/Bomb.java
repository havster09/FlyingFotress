package com.flyingfotress.game.entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.flyingfotress.game.AnimatedSpriteManager;

public class Bomb extends EntityAnimatedSprite{
    public Bomb(Animation animation, Vector2 pos, Vector2 direction, float x, float y) {
        super(animation, pos, direction, x, y);
    }

    @Override
    public void update() {
        pos.add(direction);
    }

    public boolean checkEnd() {
        return (pos.y >= Gdx.graphics.getHeight() || pos.y < 0 || pos.x >= Gdx.graphics.getWidth() || pos.x < 0);
    }
}


