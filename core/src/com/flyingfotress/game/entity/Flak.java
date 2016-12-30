package com.flyingfotress.game.entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

public class Flak extends EntityAnimatedSprite{
    public Flak(Animation animation, Vector2 pos, Vector2 direction, float x, float y, float a, float b) {
        super(animation, pos, direction, x, y, a, b, true);
    }

    @Override
    public void update() {

    }
}


