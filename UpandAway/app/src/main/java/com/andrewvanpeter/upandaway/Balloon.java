package com.andrewvanpeter.upandaway;

public class Balloon {
    private float balloonX;
    private float balloonY;
    private float balloonSpeed;
    Boolean balloonLive = false;

    Balloon(float balloonX, float balloonY) {
        this.balloonX = balloonX;
        this.balloonY = balloonY;
    }

    public float getBalloonX() {
        return balloonX;
    }

    public float getCarrotY() {
        return balloonY;
    }

    public void setCarrotX(float balloonX) {
        this.balloonX = balloonX;
    }

    public void setCarrotY(float balloonY) {
        this.balloonY = balloonY;
    }

    public void setCarrotSpeed(float balloonSpeed) {
        this.balloonSpeed = balloonSpeed;
    }

    public Boolean getBalloonLive() {
        return balloonLive;
    }

    public void setBalloonLive(Boolean change) {
        balloonLive = change;
    }
}
