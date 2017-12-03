//Base code taken from Learning Java by Building Android Games by John Horton

package com.andrewvanpeter.upandaway;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends Activity {
    Canvas canvas;
    BunnyView bunnyView;

    //Initialize game objects
    SoundPlayer sound;
    Bunny bunny = new Bunny (320, 865);

    int startingStarY = -250;
    Random random = new Random();

    int randX1 = random.nextInt(720 - 25) + 1;
    Star star1 = new Star(randX1, startingStarY);

    int randX2 = random.nextInt(720 - 25) + 1;
    Star star2 = new Star(randX2, startingStarY);

    int randX3 = random.nextInt(720 - 25) + 1;
    Star star3 = new Star(randX3, startingStarY);

    int randX4 = random.nextInt(720 - 25) + 1;
    Star star4 = new Star(randX4, startingStarY);

    int randX5 = random.nextInt(720 - 25) + 1;
    Star star5 = new Star(randX5, startingStarY);

    Bitmap bunnyBitMap;
    Bitmap starBitmap;
    Bitmap backgroundBitMap;
    Bitmap carrotBitmap;
    Bitmap balloonBitmap;
    Bitmap blackholeBitmap;

    int screenWidth;
    int screenHeight;
    int topGap;

    //sound settings
    Boolean soundEffectsOn = true;

    //stats
    long lastFrameTime;
    int fps;

    //The size in pixels of a place on the game board
    int blockSize;
    int numBlocksWide;
    int numBlocksHigh;

    //Timer variable
    int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configureDisplay();
        sound = new SoundPlayer(this);
        bunnyView = new BunnyView(this);
        setContentView(bunnyView);

        int delay = 0; // do not delay
        int period = 1000; // repeat every 1 second
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run() {
                time++;
            }
        }, delay, period);

        Intent settingsData = getIntent();
        final int difficulty = settingsData.getIntExtra("Difficulty", 0);
        soundEffectsOn = settingsData.getBooleanExtra("soundFX", true);

        //Set difficulty other than easy
        if (difficulty == 2) {
            setStarSpeed(15);
        }
        else if (difficulty == 3) {
            setStarSpeed(20);
        }
    }

    class BunnyView extends SurfaceView implements Runnable {
        Thread ourThread = null;
        SurfaceHolder ourHolder;
        volatile boolean playingBunny;
        Paint paint;

        public BunnyView(Context context) {
            super(context);
            ourHolder = getHolder();
            paint = new Paint();
        }

        @Override
        public void run() {
            while (playingBunny) {
                updateGame();
                drawGame();
                controlFPS();
            }
        }

        public void updateGame() {
            if (time >= 4) {
                star1.setStarLive(true);
            }

            if (time >= 8) {
                star2.setStarLive(true);
            }

            if (time >= 12) {
                star3.setStarLive(true);
            }

            if (time >= 16) {
                star4.setStarLive(true);
            }

            if (time >= 20) {
                star5.setStarLive(true);
            }

            float bunnyXOrigin = bunny.getBunnyX() + 41;
            float bunnyYOrigin = bunny.getBunnyY() + 129;

            //If the player has not collided with a carrot
            if (!bunny.getInvincible()) {
                if ((bunnyXOrigin >= (star1.getStarX() + 40) && bunnyXOrigin <= star1.getStarX() + 170) && ((bunnyYOrigin + 50) >= star1.getStarY() + 75 && bunnyYOrigin <= (star1.getStarY() + 75) + 180) && star1.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.setBunnyLives(-1);
                    star1.setVisible(false);
                }

                if ((bunnyXOrigin >= (star2.getStarX() + 40) && bunnyXOrigin <= star2.getStarX() + 170) && ((bunnyYOrigin + 50) >= star2.getStarY() + 75 && bunnyYOrigin <= (star2.getStarY() + 75) + 180) && star2.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.setBunnyLives(-1);
                    star2.setVisible(false);
                }

                if ((bunnyXOrigin >= (star3.getStarX() + 40) && bunnyXOrigin <= star3.getStarX() + 170) && ((bunnyYOrigin + 50) >= star3.getStarY() + 75 && bunnyYOrigin <= (star3.getStarY() + 75) + 180) && star3.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.setBunnyLives(-1);
                    star3.setVisible(false);
                }

                if ((bunnyXOrigin >= (star4.getStarX() + 40) && bunnyXOrigin <= star4.getStarX() + 170) && ((bunnyYOrigin + 50) >= star4.getStarY() + 75 && bunnyYOrigin <= (star4.getStarY() + 75) + 180) && star4.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.setBunnyLives(-1);
                    star4.setVisible(false);
                }

                if ((bunnyXOrigin >= (star5.getStarX() + 40) && bunnyXOrigin <= star5.getStarX() + 170) && ((bunnyYOrigin + 50) >= star5.getStarY() + 75 && bunnyYOrigin <= (star5.getStarY() + 75) + 180) && star5.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.setBunnyLives(-1);
                    star5.setVisible(false);
                }
            }

            if (star1.getStarLive()) {
                star1.addStarY(star1.getStarSpeed());
            }

            if (star2.getStarLive()) {
                star2.addStarY(star2.getStarSpeed());
            }

            if (star3.getStarLive()) {
                star3.addStarY(star3.getStarSpeed());
            }

            if (star4.getStarLive()) {
                star4.addStarY(star4.getStarSpeed());
            }

            if (star5.getStarLive()) {
                star5.addStarY(star5.getStarSpeed());
            }

            if (star1.getStarY() >= 1700) {
                setStarPosition(star1);
                star1.setVisible(true);
            }

            if (star2.getStarY() >= 1700) {
                setStarPosition(star2);
                star2.setVisible(true);
            }

            if (star3.getStarY() >= 1700) {
                setStarPosition(star3);
                star3.setVisible(true);
            }

            if (star4.getStarY() >= 1700) {
                setStarPosition(star4);
                star4.setVisible(true);
            }

            if (star5.getStarY() >= 1700) {
                setStarPosition(star5);
                star5.setVisible(true);
            }

            //Bunny movement updates
            if (bunny.getBunnyIsMovingRight()) {
                bunny.setBunnyX(15);
            }

            if (bunny.getBunnyIsMovingLeft()) {
                bunny.setBunnyX(-15);
            }

            if (bunnyXOrigin <= 50) {
                bunny.setBunnyIsMovingRight(false);
            }

            if (bunnyXOrigin + 100 >= 775) {
                bunny.setBunnyIsMovingLeft(false);
            }
        }

        public void drawGame() {
            if (ourHolder.getSurface().isValid()) {
                canvas = ourHolder.lockCanvas();

                //Draw background
                canvas.drawBitmap(backgroundBitMap, 0, 0, paint);

                //Paint paint = new Paint();
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(topGap/2);
                canvas.drawText("Score:" + time, 10, topGap-6, paint);
                canvas.drawText("Lives:" + bunny.getBunnyLives(), 10, 30, paint);

                //Draw the bunny
                canvas.drawBitmap(bunnyBitMap, bunny.getBunnyX(), bunny.getBunnyY(), paint);

                //draw the stars if they are set to visible
                if (star1.getVisible()) {
                    canvas.drawBitmap(starBitmap, star1.getStarX(), star1.getStarY(), paint);
                }

                if (star2.getVisible()) {
                    canvas.drawBitmap(starBitmap, star2.getStarX(), star2.getStarY(), paint);
                }

                if (star3.getVisible()) {
                    canvas.drawBitmap(starBitmap, star3.getStarX(), star3.getStarY(), paint);
                }

                if (star4.getVisible()) {
                    canvas.drawBitmap(starBitmap, star4.getStarX(), star4.getStarY(), paint);
                }

                if (star5.getVisible()) {
                    canvas.drawBitmap(starBitmap, star5.getStarX(), star5.getStarY(), paint);
                }

                ourHolder.unlockCanvasAndPost(canvas);
            }
        }

        public void controlFPS() {
            long timeThisFrame = (System.currentTimeMillis() - lastFrameTime);
            long timeToSleep = 100 - timeThisFrame;
            if (timeThisFrame > 0) {
                fps = (int) (1000 / timeThisFrame);
            }
            if (timeToSleep > 0) {

                try {
                    ourThread.sleep(timeToSleep);
                } catch (InterruptedException e) {
                    //Print an error message to the console
                    Log.e("error", "failed to load sound files");
                }
            }
            lastFrameTime = System.currentTimeMillis();
        }

        public void pause() {
            playingBunny = false;
            try {
                ourThread.join();
            } catch (InterruptedException e) {
            }
        }

        public void resume() {
            playingBunny = true;
            ourThread = new Thread(this);
            ourThread.start();
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    if (motionEvent.getX() >= screenWidth / 2) {
                        bunny.setBunnyIsMovingRight(true);
                        bunny.setBunnyIsMovingLeft(false);
                    } else {
                        bunny.setBunnyIsMovingRight(false);
                        bunny.setBunnyIsMovingLeft(true);
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    bunny.setBunnyIsMovingRight(false);
                    bunny.setBunnyIsMovingLeft(false);
            }
            return true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        while (true) {
            bunnyView.pause();
            break;
        }
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        bunnyView.resume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        bunnyView.pause();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            bunnyView.pause();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        return false;
    }

    public void configureDisplay(){
        //find out the width and height of the screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        topGap = screenHeight/14;

        //Determine the size of each block/place on the game board
        blockSize = screenWidth/40;

        //Determine how many game blocks will fit into the height and width
        //Leave one block for the score at the top
        numBlocksWide = 40;
        numBlocksHigh = ((screenHeight - topGap ))/blockSize;

        //Load and scale bitmaps
        backgroundBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.background_flat_720x1280);
        bunnyBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.bunny_balloon_83x258);
        starBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.star_226x224);
        carrotBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.carrots);
        balloonBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.balloon);


        //scale the bitmaps to match the block size
        backgroundBitMap = Bitmap.createScaledBitmap(backgroundBitMap, 720, 1280, false);
        bunnyBitMap = Bitmap.createScaledBitmap(bunnyBitMap, 80, 200, false);
        starBitmap = Bitmap.createScaledBitmap(starBitmap, 200, 200, false);

    }

    public void setStarPosition(Star star) {
        Random random = new Random();
        int starX = random.nextInt(720 - 25) + 1;
        int starY = -250;

        star.setStarX(starX);
        star.setStarY(starY);
    }

    public void setStarSpeed(float speed) {
        star1.setStarSpeed(speed);
        star2.setStarSpeed(speed);
        star3.setStarSpeed(speed);
        star4.setStarSpeed(speed);
        star5.setStarSpeed(speed);
    }
}