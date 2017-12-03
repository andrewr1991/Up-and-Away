package com.andrewvanpeter.upandaway;

public class Star {
    private float starX;
    private float starY;
    private Boolean starLive = false;
    private float starSpeed = 10;
    private Boolean visible = true;

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

    public void setStarX(float starX) {
        this.starX = starX;
    }

    public void setStarY(float starY) {
        this.starY = starY;
    }

    public void addStarY(float additionalY) {
        starY += additionalY;
    }

    public float getStarSpeed() {
        return starSpeed;
    }

    public void setStarSpeed(float StarSpeed) {
        this.starSpeed = StarSpeed;
    }

    public void addStarSpeed(float additionalSpeed) {
        starSpeed += additionalSpeed;
    }

    public Boolean getStarLive() {
        return starLive;
    }

    public void setStarLive(Boolean change) {
        starLive = change;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean change) {
        visible = change;
    }
}
