package com.andrewvanpeter.upandaway;

public class Bunny {
    private float bunnyX;
    private float bunnyY;
    private int lives = 3;
    Boolean bunnyIsMovingRight = false;
    Boolean bunnyIsMovingLeft = false;

    public Bunny(float bunnyX, float bunnyY){
        this.bunnyX = bunnyX;
        this.bunnyY = bunnyY;
    }

    public float getBunnyX() {
        return bunnyX;
    }

    public float getBunnyY() {
        return bunnyY;
    }

    public void setBunnyX(float newX) {
        bunnyX += newX;
    }

    public int getBunnyLives() {
        return lives;
    }

    public void setBunnyLives(int additionalLife) {
        lives += additionalLife;
    }

    public Boolean getBunnyIsMovingRight() {
        return bunnyIsMovingRight;
    }

    public Boolean getBunnyIsMovingLeft() {
        return bunnyIsMovingLeft;
    }

    public void setBunnyIsMovingRight(Boolean change) {
        bunnyIsMovingRight = change;
    }

    public void setBunnyIsMovingLeft(Boolean change) {
        bunnyIsMovingLeft = change;
    }
}