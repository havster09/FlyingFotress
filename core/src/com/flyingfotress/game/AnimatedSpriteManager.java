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

    public static TextureAtlas textureAtlasBombOne = new TextureAtlas(Gdx.files.internal("bomb_a.pack"));
    public static Array<TextureAtlas.AtlasRegion> regionBombOne = textureAtlasBombOne.findRegions("bomb_a");
    public static Animation animationBombOne = new Animation(1 / 30f, regionBombOne, Animation.PlayMode.LOOP);
    public static AnimatedSprite BOMB_1 = new AnimatedSprite(animationBombOne);

    public static TextureAtlas textureAtlasBombTwo = new TextureAtlas(Gdx.files.internal("bomb_b.pack"));
    public static Array<TextureAtlas.AtlasRegion> regionBombTwo = textureAtlasBombTwo.findRegions("bomb_b");
    public static Animation animationBombTwo = new Animation(1 / 30f, regionBombTwo, Animation.PlayMode.LOOP);
    public static AnimatedSprite BOMB_2 = new AnimatedSprite(animationBombTwo);

    public static TextureAtlas textureAtlasBombThree = new TextureAtlas(Gdx.files.internal("bomb_c.pack"));
    public static Array<TextureAtlas.AtlasRegion> regionBombThree = textureAtlasBombThree.findRegions("bomb_c");
    public static Animation animationBombThree = new Animation(1 / 30f, regionBombThree, Animation.PlayMode.LOOP);
    public static AnimatedSprite BOMB_3 = new AnimatedSprite(animationBombThree);

    public static TextureAtlas textureAtlasBombFour = new TextureAtlas(Gdx.files.internal("bomb_d.pack"));
    public static Array<TextureAtlas.AtlasRegion> regionBombFour = textureAtlasBombFour.findRegions("bomb_d");
    public static Animation animationBombFour = new Animation(1 / 30f, regionBombFour, Animation.PlayMode.LOOP);
    public static AnimatedSprite BOMB_4 = new AnimatedSprite(animationBombFour);
}
