package com.andrewvanpeter.upandaway;

public class Balloon {
    private double balloonX;
    private double balloonY;
    private double balloonSpeed;
    private Boolean balloonLive = false;

    Balloon(double balloonX, double balloonY) {
        this.balloonX = balloonX;
        this.balloonY = balloonY;
    }

    public double getBalloonX() {
        return balloonX;
    }

    public double getCarrotY() {
        return balloonY;
    }

    public void setCarrotX(double balloonX) {
        this.balloonX = balloonX;
    }

    public void setCarrotY(double balloonY) {
        this.balloonY = balloonY;
    }

    public void setCarrotSpeed(double balloonSpeed) {
        this.balloonSpeed = balloonSpeed;
    }

    public Boolean getBalloonLive() {
        return balloonLive;
    }

    public void setBalloonLive(Boolean balloonLive) {
        this.balloonLive = balloonLive;
    }
}
