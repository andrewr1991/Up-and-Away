package com.andrewvanpeter.upandaway;

public class Balloon {
    private float balloonX;
    private float balloonY;
    private double balloonSpeed = 10;
    private Boolean balloonLive = false;
    private Boolean visible = true;
    private Boolean balloonStart = true;

    Balloon(float balloonX, float balloonY) {
        this.balloonX = balloonX;
        this.balloonY = balloonY;
    }

    public float getBalloonX() {
        return balloonX;
    }

    public float getBalloonY() {
        return balloonY;
    }

    public void setBalloonX(float balloonX) {
        this.balloonX = balloonX;
    }

    public void setBalloonY(float balloonY) {
        this.balloonY = balloonY;
    }

    public void addBalloonY(double additionalY) {
        balloonY += additionalY;
    }

    public double getBalloonSpeed() {
        return balloonSpeed;
    }

    public void setBalloonSpeed(double balloonSpeed) {
        this.balloonSpeed = balloonSpeed;
    }

    public Boolean getBalloonLive() {
        return balloonLive;
    }

    public void setBalloonLive(Boolean balloonLive) {
        this.balloonLive = balloonLive;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getBalloonStart() {
        return balloonStart;
    }

    public void setBalloonStart(Boolean balloonStart) {
        this.balloonStart = balloonStart;
    }
}
