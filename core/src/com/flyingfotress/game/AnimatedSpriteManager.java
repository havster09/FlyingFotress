package com.flyingfotress.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

public class AnimatedSpriteManager {
    public static TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("sprite.txt"));
    public static Array<TextureAtlas.AtlasRegion> region = textureAtlas.findRegions("run");
    public static Animation animation = new Animation(1 / 15f, region, Animation.PlayMode.LOOP);
    public static AnimatedSprite PLAYER_ANIMATED_SPRITE = new AnimatedSprite(animation);
}
