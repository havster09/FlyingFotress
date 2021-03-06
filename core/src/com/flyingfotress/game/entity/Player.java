package com.flyingfotress.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.flyingfotress.game.AnimatedSpriteManager;
import com.flyingfotress.game.SpriteManager;
import com.flyingfotress.game.SpriteManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.currentTimeMillis;

public class Player extends EntitySprite implements InputProcessor {
    public static final int ACCELERATOR_MULTIPLIER = 50;
    public static final int GUNNER_TOUCH_BUFFER = 200;
    private static long lastBombDrop = 0;
    private final OrthographicCamera camera;
    public String message;

    private ArrayList<GunInfo> gunners = new ArrayList<GunInfo>();
    private Map<Integer, TouchInfo> touches = new HashMap<Integer, TouchInfo>();
    public BitmapFont font;

    public Player(Vector2 pos, Vector2 direction, EntityManager entityManager, OrthographicCamera camera) {
        super(SpriteManager.PLAYER_SPRITE, pos, direction);
        this.entityManager = entityManager;
        this.camera = camera;

        font = new BitmapFont();
        font.setColor(Color.RED);
        Gdx.input.setInputProcessor(this);

        for(int i = 0; i < 5; i++) {
            touches.put(i, new TouchInfo());
        }

        ArrayList<String> gunPositions = new ArrayList<String>();

        gunPositions.add("headGunner");
        gunPositions.add("tailGunner");
        gunPositions.add("leftWaistGunner");
        gunPositions.add("rightWaistGunner");

        for(String g: gunPositions) {
            gunners.add(new GunInfo(g));
        }
    }

    @Override
    public void update() {
        this.setCenter(SpriteManager.PLAYER_SPRITE.getWidth()/2, SpriteManager.PLAYER_SPRITE.getHeight()/2);
        float accelX = Gdx.input.getAccelerometerX();

        float highestRightX = -8f;
        float highestLeftX = 8f;
        float directionValue = 0f;

        if(accelX < 0) {
            if (accelX < highestRightX) {
                directionValue = highestRightX;
            }
            else {
                directionValue = accelX;
            }
        }
        else if(accelX > 0) {
            if (accelX > highestLeftX) {
                directionValue = highestLeftX;
            }
            else {
                directionValue = accelX;
            }
        }
        else {
            directionValue = 0f;
        }

        setDirection(directionValue * -ACCELERATOR_MULTIPLIER, 0);

        pos.add(direction);

        int dir = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.A) || dir == 1) {
            setDirection(-100, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || dir == 2) {
            setDirection(100, 0);
        } else {
            setDirection(0, 0);
        }

        // debug
        message = "";
        for(int i = 0; i < 5; i++) {
            if(touches.get(i).touched) {
                message += "Finger: " + Integer.toString(i) + "touch at:" +
                        Float.toString(touches.get(i).touchX) + "," +
                        Float.toString(touches.get(i).touchY) + "\n";

                message += "Player position X at: " + Float.toString(this.getPosition().x) + "\n";
                message += "Player position Y at: " + Float.toString(this.getPosition().y) + "\n";
            }
        }

        for(int i = 0; i < 4; i++) {
            if(gunners.get(i).isFiring) {
                String gunName = gunners.get(i).name;
                switch(gunName) {
                    case "headGunner":
                        headGunnerFire(i);
                        break;
                    case "tailGunner":
                        tailGunnerFire(i);
                        break;
                    case "leftWaistGunner":
                        leftWaistGunnerFire(i);
                        break;
                    case "rightWaistGunner":
                        rightWaistGunnerFire(i);
                        break;
                    default:
                        Gdx.app.log("INFO",gunName + "fire");
                }
            }
        }
    }

    private void headGunnerFire(int i) {
        if (currentTimeMillis() - gunners.get(i).lastFire >= MathUtils.random(100, 350)) {
            entityManager.addEntitySprite(new Bullet(pos.cpy().add(SpriteManager.BULLET_SPRITE.getWidth() / 2, SpriteManager.BULLET_SPRITE.getHeight()),
                    new Vector2(MathUtils.random(-1, 1), 20)));
            gunners.get(i).lastFire = currentTimeMillis();
        }
    }

    private void tailGunnerFire(int i) {
        if (currentTimeMillis() - gunners.get(i).lastFire >= MathUtils.random(50, 350)) {
            entityManager.addEntitySprite(new Bullet(pos.cpy().add(SpriteManager.BULLET_SPRITE.getWidth() / 2, 0),
                    new Vector2(MathUtils.random(-1, 1), -20)));
            gunners.get(i).lastFire = currentTimeMillis();
        }
    }

    private void leftWaistGunnerFire(int i) {
        if (currentTimeMillis() - gunners.get(i).lastFire >= MathUtils.random(50, 350)) {
            entityManager.addEntitySprite(new Bullet(pos.cpy().add(SpriteManager.BULLET_SPRITE.getWidth() / 2, SpriteManager.BULLET_SPRITE.getHeight()/2),
                    new Vector2(-20, MathUtils.random(-1, 1))));
            gunners.get(i).lastFire = currentTimeMillis();
        }
    }

    private void rightWaistGunnerFire(int i) {
        if (currentTimeMillis() - gunners.get(i).lastFire >= MathUtils.random(50, 350)) {
            entityManager.addEntitySprite(new Bullet(pos.cpy().add(SpriteManager.BULLET_SPRITE.getWidth() / 2, SpriteManager.BULLET_SPRITE.getHeight()/2),
                    new Vector2(20, MathUtils.random(-1, 1))));
            gunners.get(i).lastFire = currentTimeMillis();
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pointer < 5) {
            touches.get(pointer).touchX = screenX;
            touches.get(pointer).touchY = screenY;
            touches.get(pointer).touched = true;

            System.out.println(screenX + ", " + screenY + ", " + this.pos.x + ", " + this.pos.y + ", " + SpriteManager.PLAYER_SPRITE.getHeight());
            Vector3 touch = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            Rectangle textureBounds = new Rectangle(this.getPosition().x,this.getPosition().y + SpriteManager.PLAYER_SPRITE.getHeight(),SpriteManager.PLAYER_SPRITE.getWidth(),SpriteManager.PLAYER_SPRITE.getHeight());

            if(textureBounds.contains(touch.x,touch.y))
            {
                if (currentTimeMillis() - Player.lastBombDrop >= MathUtils.random(300, 650)) {
                    System.out.println("bomb drop");
                    entityManager.spawnBomb(1, this.pos.x + MathUtils.random(-50, 50), this.pos.y - SpriteManager.PLAYER_SPRITE.getHeight()/2);
                    Player.lastBombDrop = currentTimeMillis();
                }

            }
            else {
                if((screenX >  Gdx.graphics.getWidth()/2 - GUNNER_TOUCH_BUFFER && screenX < Gdx.graphics.getWidth()/2 + GUNNER_TOUCH_BUFFER)
                        || (screenX > this.getPosition().x - GUNNER_TOUCH_BUFFER && screenX < this.getPosition().x + GUNNER_TOUCH_BUFFER)) {
                    if(screenY < Gdx.graphics.getHeight()/2) {
                        gunners.get(0).isFiring = !gunners.get(0).isFiring;
                    }
                    else {
                        gunners.get(1).isFiring = !gunners.get(1).isFiring;
                    }
                }
                else if(screenY >  Gdx.graphics.getHeight()/2 - GUNNER_TOUCH_BUFFER && screenY < Gdx.graphics.getHeight()/2 + GUNNER_TOUCH_BUFFER){
                    if(screenX < this.getPosition().x + SpriteManager.PLAYER_SPRITE.getWidth()/2) {
                        gunners.get(2).isFiring = !gunners.get(2).isFiring;
                    }
                    else {
                        gunners.get(3).isFiring = !gunners.get(3).isFiring;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
