package com.flyingfotress.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

public class AnimatedSpriteManager {
    public static TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("ff08102016.atlas"));
    public static Array<TextureAtlas.AtlasRegion> region = textureAtlas.findRegions("flak_e");
    public static Animation animation = new Animation(1 / 30f, region, Animation.PlayMode.LOOP);
    public static AnimatedSprite FLAK = new AnimatedSprite(animation);
}
