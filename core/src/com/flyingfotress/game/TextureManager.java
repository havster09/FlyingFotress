package com.flyingfotress.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureManager {
    public static TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("textures.pack"));

    public static TextureRegion PLAYER_TEXTURE_REGION = new TextureRegion(textureAtlas.findRegion("b17_top_tp"));
    public static TextureRegion BULLET_TEXTURE_REGION = new TextureRegion(textureAtlas.findRegion("bullet"));
    public static TextureRegion ENEMY_TEXTURE_REGION = new TextureRegion(textureAtlas.findRegion("fw190D_tp"));

    /*public static Texture PLAYER = PLAYER_TEXTURE_REGION.getTexture();
    public static Texture BULLET = BULLET_TEXTURE_REGION.getTexture();
    public static Texture ENEMY = ENEMY_TEXTURE_REGION.getTexture();*/

    public static Texture PLAYER = new Texture(Gdx.files.internal("b17_top_tp.png"));
    public static Texture BULLET = new Texture(Gdx.files.internal("bullet.png"));
    public static Texture ENEMY = new Texture(Gdx.files.internal("fw190D_tp.png"));

    public static Sprite PLAYER_SPRITE = new Sprite(PLAYER);
    public static Sprite BULLET_SPRITE = new Sprite(BULLET);
    public static Sprite ENEMY_SPRITE = new Sprite(ENEMY);
}
