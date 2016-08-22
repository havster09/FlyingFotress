package com.flyingfotress.game.entity;

public class GunInfo {
    public int pointer;
    public String name;
    public long ammoCount = 5000;
    public long lastFire = 0;
    public boolean isFiring = false;

    public GunInfo(String name) {
        this.name = name;
    }
}
