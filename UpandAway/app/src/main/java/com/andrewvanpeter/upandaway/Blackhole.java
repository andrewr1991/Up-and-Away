package com.andrewvanpeter.upandaway;

public class Blackhole {
    private float blackholeX;
    private float blackholeY;
    private double blackholeSpeed = 10;
    private Boolean blackholeLive = false;
    private Boolean visible = true;
    private Boolean blackholeStart = true;

    Blackhole(float blackholeX, float blackholeY) {
        this.blackholeX = blackholeX;
        this.blackholeY = blackholeY;
    }

    public float getBlackholeX() {
        return blackholeX;
    }

    public float getBlackholeY() {
        return blackholeY;
    }

    public void setBlackholeX(float blackholeX) {
        this.blackholeX = blackholeX;
    }

    public void setBlackholeY(float blackholeY) {
        this.blackholeY = blackholeY;
    }

    public void addBlackholeY(double additionalY) {
        blackholeY += additionalY;
    }

    public double getBlackholeSpeed() {
        return blackholeSpeed;
    }

    public void setBlackholeSpeed(double blackholeSpeed) {
        this.blackholeSpeed = blackholeSpeed;
    }

    public Boolean getBlackholeLive() {
        return blackholeLive;
    }

    public void setBlackholeLive(Boolean change) {
        blackholeLive = change;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getBlackholeStart() {
        return blackholeStart;
    }

    public void setBlackholeStart(Boolean blackholeStart) {
        this.blackholeStart = blackholeStart;
    }
}
