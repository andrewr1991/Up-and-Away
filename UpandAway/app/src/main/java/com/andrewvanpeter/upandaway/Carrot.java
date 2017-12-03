package com.andrewvanpeter.upandaway;

public class Carrot {
    private float carrotX;
    private float carrotY;
    private float carrotSpeed;
    private Boolean carrotLive = false;

    Carrot(float carrotX, float carrotY) {
        this.carrotX = carrotX;
        this.carrotY = carrotY;
    }

    public float getCarrotX() {
        return carrotX;
    }

    public float getCarrotY() {
        return carrotY;
    }

    public void setCarrotX(float carrotX) {
        this.carrotX = carrotX;
    }

    public void setCarrotY(float carrotY) {
        this.carrotY = carrotY;
    }

    public void setCarrotSpeed(float carrotSpeed) {
        this.carrotSpeed = carrotSpeed;
    }

    public Boolean getCarrotLive() {
        return carrotLive;
    }

    public void setCarrotLive(Boolean change) {
        carrotLive = change;
    }
}
