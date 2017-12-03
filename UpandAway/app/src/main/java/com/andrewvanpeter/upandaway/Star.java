package com.andrewvanpeter.upandaway;

public class Star {
    private float starX;
    private float starY;
    private Boolean starLive = false;
    private float starSpeed = 10;

    Star(float starX, float starY) {
        this.starX = starX;
        this.starY = starY;
    }

    public float getStarX() {
        return starX;
    }

    public float getStarY() {
        return starY;
    }

    public void setStarX(float newX) {
        starX = newX;
    }

    public void setStarY(float newY) {
        starY = newY;
    }

    public void addStarY(float additionalY) {
        starY += additionalY;
    }

    public float getStarSpeed() {
        return starSpeed;
    }

    public void setStarSpeed(float newSpeed) {
        starSpeed += newSpeed;
    }

    public Boolean getStarLive() {
        return starLive;
    }

    public void setStarLive(Boolean change) {
        starLive = change;
    }
}
