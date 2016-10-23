package com.flyingfotress.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

public class AnimatedSpriteManager {
    public static TextureAtlas textureAtlasFlak = new TextureAtlas(Gdx.files.internal("flak_f.atlas"));
    public static Array<TextureAtlas.AtlasRegion> regionFlak = textureAtlasFlak.findRegions("flak_f");
    public static Animation animationFlak = new Animation(1 / 30f, regionFlak, Animation.PlayMode.LOOP);
    public static AnimatedSprite FLAK = new AnimatedSprite(animationFlak);

    public static TextureAtlas textureAtlasBombOne = new TextureAtlas(Gdx.files.internal("flak_f.atlas"));
    public static Array<TextureAtlas.AtlasRegion> regionBombOne = textureAtlasBombOne.findRegions("flak_f");
    public static Animation animationBombOne = new Animation(1 / 30f, regionBombOne, Animation.PlayMode.LOOP);
    public static AnimatedSprite BOMB_1 = new AnimatedSprite(animationBombOne);
}
