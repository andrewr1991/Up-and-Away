package com.andrewvanpeter.upandaway;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static int hitSound;
    private static  int music;

    public SoundPlayer(Context context) {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        hitSound = soundPool.load(context, R.raw.hitstar, 1);
        music = soundPool.load(context, R.raw.lullaby, 1);
    }

    public void playHitSound() {
        soundPool.play(hitSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playMusic() {
        soundPool.play(music, 1.0f, 1.0f, 2, 0, 1.0f);
    }
}