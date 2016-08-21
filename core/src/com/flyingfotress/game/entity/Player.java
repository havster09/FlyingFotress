package com.flyingfotress.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.flyingfotress.game.FlyingFotress;
import com.flyingfotress.game.TextureManager;
import com.flyingfotress.game.camera.OrthoCamera;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.currentTimeMillis;

public class Player extends Entity implements InputProcessor {
    public static final int ACCELERATOR_MULTIPLIER = 100;
    private long lastFire;
    private final OrthoCamera camera;
    public String message;

    private Map<Integer, GunInfo> gunners = new HashMap<Integer, GunInfo>();
    private Map<Integer, TouchInfo> touches = new HashMap<Integer, TouchInfo>();
    public BitmapFont font;

    public Player(Vector2 pos, Vector2 direction, EntityManager entityManager, OrthoCamera camera) {
        super(TextureManager.PLAYER, pos, direction);
        this.entityManager = entityManager;
        this.camera = camera;

        font = new BitmapFont();
        font.setColor(Color.RED);
        Gdx.input.setInputProcessor(this);

        for(int i = 0; i < 5; i++) {
            touches.put(i, new TouchInfo());
        }

        for(int i = 0; i < 4; i++) {
            gunners.put(i, new GunInfo());
        }
    }

    @Override
    public void update() {
        float accelX = Gdx.input.getAccelerometerX();

        float highestRightX = 1f;
        float highestLeftX = -1f;
        float directionValue;
        if (accelX > highestRightX) {
            directionValue = highestLeftX;
        } else if (accelX < highestLeftX) {
            directionValue = highestRightX;
        } else {
            directionValue = 0f;
        }

        setDirection(directionValue * ACCELERATOR_MULTIPLIER, 0);

        pos.add(direction);

        int dir = 0;

        if (Gdx.input.isTouched()) {
            Vector2 touch = camera.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY());
            if (touch.x < FlyingFotress.WIDTH / 2) {
                dir = 1;
            } else {
                dir = 2;
            }

        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) || dir == 1) {
            setDirection(-100, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || dir == 2) {
            setDirection(100, 0);
        } else {
            setDirection(0, 0);
        }

        /*if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(currentTimeMillis() - lastFire >= MathUtils.random(10, 150)) {
                entityManager.addEntity(new Bullet(pos.cpy().add(TextureManager.PLAYER.getWidth()/2 - TextureManager.BULLET.getWidth()/2, TextureManager.PLAYER.getHeight())));
                lastFire = currentTimeMillis();
            }
        }*/

        // autofire

        /*if (currentTimeMillis() - lastFire >= MathUtils.random(100, 350)) {
            if(touches.size() > 0) {
                entityManager.addEntity(new Bullet(pos.cpy().add(TextureManager.PLAYER.getWidth() / 2 - TextureManager.BULLET.getWidth() / 2, TextureManager.PLAYER.getHeight())));
                lastFire = currentTimeMillis();
            }
        }*/

        // touch fire

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
            entityManager.addEntity(new Bullet(pos.cpy().add(TextureManager.PLAYER.getWidth() / 2, TextureManager.PLAYER.getHeight()),
                    new Vector2(MathUtils.random(-1, 1), 20)));
            gunners.get(i).lastFire = currentTimeMillis();
        }
    }

    private void tailGunnerFire(int i) {
        if (currentTimeMillis() - gunners.get(i).lastFire >= MathUtils.random(50, 350)) {
            entityManager.addEntity(new Bullet(pos.cpy().add(TextureManager.PLAYER.getWidth() / 2, 0),
                    new Vector2(MathUtils.random(-1, 1), -20)));
            gunners.get(i).lastFire = currentTimeMillis();
        }
    }

    private void leftWaistGunnerFire(int i) {
        if (currentTimeMillis() - gunners.get(i).lastFire >= MathUtils.random(50, 350)) {
            entityManager.addEntity(new Bullet(pos.cpy().add(TextureManager.PLAYER.getWidth() / 2, TextureManager.PLAYER.getHeight()/2),
                    new Vector2(-20, MathUtils.random(-1, 1))));
            gunners.get(i).lastFire = currentTimeMillis();
        }
    }

    private void rightWaistGunnerFire(int i) {
        if (currentTimeMillis() - gunners.get(i).lastFire >= MathUtils.random(50, 350)) {
            entityManager.addEntity(new Bullet(pos.cpy().add(TextureManager.PLAYER.getWidth() / 2, TextureManager.PLAYER.getHeight()/2),
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

            if(screenX <  this.getPosition().x + 300 && screenX > this.getPosition().x + TextureManager.PLAYER.getWidth() - 300) {
                if(screenY < Gdx.graphics.getHeight()/2) {
                    gunners.get(pointer).name = "headGunner";
                    gunners.get(pointer).isFiring = true;
                }
                else {
                    gunners.get(pointer).name = "tailGunner";
                    gunners.get(pointer).isFiring = true;
                }
            }
            else if(screenY >  this.getPosition().y - 300 && screenY < this.getPosition().y + TextureManager.PLAYER.getHeight() + 300){
                if(screenX < Gdx.graphics.getWidth()/2) {
                    gunners.get(pointer).name = "leftWaistGunner";
                    gunners.get(pointer).isFiring = true;
                }
                else {
                    gunners.get(pointer).name = "rightWaistGunner";
                    gunners.get(pointer).isFiring = true;
                }
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer < 5) {
            touches.get(pointer).touchX = 0;
            touches.get(pointer).touchY = 0;
            touches.get(pointer).touched = false;
            gunners.get(pointer).isFiring = false;
        }
        return true;
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
