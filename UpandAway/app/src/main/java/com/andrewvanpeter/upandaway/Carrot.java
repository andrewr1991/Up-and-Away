package com.andrewvanpeter.upandaway;

public class Carrot {
    private float carrotX;
    private float carrotY;
    private double carrotSpeed = 10;
    private Boolean carrotLive = false;
    private Boolean visible = true;

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

    public void addCarrotY(double additionalY) {
        carrotY += additionalY;
    }

    public double getCarrotSpeed() {
        return carrotSpeed;
    }

    public void setCarrotSpeed(double carrotSpeed) {
        this.carrotSpeed = carrotSpeed;
    }

    public Boolean getCarrotLive() {
        return carrotLive;
    }

    public void setCarrotLive(Boolean change) {
        carrotLive = change;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
