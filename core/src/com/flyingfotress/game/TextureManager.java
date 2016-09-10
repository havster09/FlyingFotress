package com.flyingfotress.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class TextureManager {
    public static Texture PLAYER = new Texture(Gdx.files.internal("b17_top_tp.png"));
    public static Texture BULLET = new Texture(Gdx.files.internal("bullet.png"));
    public static Texture ENEMY = new Texture(Gdx.files.internal("fw190D_tp.png"));

    public static Sprite PLAYER_SPRITE = new Sprite(PLAYER);
    public static Sprite BULLET_SPRITE = new Sprite(BULLET);
    public static Sprite ENEMY_SPRITE = new Sprite(ENEMY);
}
