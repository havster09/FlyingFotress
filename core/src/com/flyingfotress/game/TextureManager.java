package com.flyingfotress.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureManager {
    public static Texture PLAYER = new Texture(Gdx.files.internal("b17_top_tp.png"));
    public static Texture BULLET = new Texture(Gdx.files.internal("bullet.png"));
    public static Texture ENEMY = new Texture(Gdx.files.internal("fw190D_tp.png"));
}
