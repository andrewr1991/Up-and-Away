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

    //Initialize sound class
    SoundPlayer sound;

    Bitmap bunnyBitMap;
    Bitmap starBitmap;
    Bitmap backgroundBitMap;
    Bitmap carrotBitmap;
    Bitmap balloonBitmap;
    Bitmap blackholeBitmap;

    //to start or stop bunny movement
    Boolean bunnyIsMovingRight = false;
    Boolean bunnyIsMovingLeft = false;


    int screenWidth;
    int screenHeight;
    int topGap;

    //sound settings
    Boolean soundEffectsOn = true;

    //stats
    long lastFrameTime;
    int fps;

    //Game objects
    float bunnyX = 320;
    float bunnyY = 865;

    //Carrot Boolean
    Boolean invincible = false;

    int lives = 3;

    //Star speed
    int starSpeed = 10;

    int star1X;
    int star1Y;
    int star2X;
    int star2Y;
    int star3X;
    int star3Y;
    int star4X;
    int star4Y;
    int star5X;
    int star5Y;

    //Star bools
    Boolean star1Live = false;
    Boolean star2Live = false;
    Boolean star3Live = false;
    Boolean star4Live = false;
    Boolean star5Live = false;

    //The size in pixels of a place on the game board
    int blockSize;
    int numBlocksWide;
    int numBlocksHigh;

    //Timer variables
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

        Random random1 = new Random();
        star1X = random1.nextInt(720 - 25) + 1;
        star1Y = -250;

        Random random2 = new Random();
        star2X = random2.nextInt(720 - 25) + 1;
        star2Y = -250;

        Random random3 = new Random();
        star3X = random3.nextInt(720 - 25) + 1;
        star3Y = -250;

        Random random4 = new Random();
        star4X = random4.nextInt(720 - 25) + 1;
        star4Y = -250;

        Random random5 = new Random();
        star5X = random4.nextInt(720 - 25) + 1;
        star5Y = -250;

        Intent settingsData = getIntent();
        final int difficulty = settingsData.getIntExtra("Difficulty", 0);
        soundEffectsOn = settingsData.getBooleanExtra("soundFX", true);

        //Set difficulty other than easy
        if (difficulty == 2) {
            starSpeed = 15;
        }
        else if (difficulty == 3) {
            starSpeed = 20;
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
            float bunnyXOrigin = bunnyX + 41;
            float bunnyYOrigin = bunnyY + 129;

            //If the player has not collided with a carrot
            if (!invincible) {
                if ((bunnyXOrigin >= (star1X + 40) && bunnyXOrigin <= star1X + 170) && ((bunnyYOrigin + 50) >= star1Y + 75 && bunnyYOrigin <= (star1Y + 75) + 180)) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    star1Y = 1400;
                    lives--;
                }

                if ((bunnyXOrigin >= (star2X + 40) && bunnyXOrigin <= star2X + 170) && ((bunnyYOrigin + 50) >= star2Y + 75 && bunnyYOrigin <= (star2Y + 75) + 180)) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    star2Y = 1400;
                    lives--;
                }

                if ((bunnyXOrigin >= (star3X + 40) && bunnyXOrigin <= star3X + 170) && ((bunnyYOrigin + 50) >= star3Y + 75 && bunnyYOrigin <= (star3Y + 75) + 180)) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    star3Y = 1400;
                    lives--;
                }

                if ((bunnyXOrigin >= (star4X + 40) && bunnyXOrigin <= star4X + 170) && ((bunnyYOrigin + 50) >= star4Y + 75 && bunnyYOrigin <= (star4Y + 75) + 180)) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    star4Y = 1400;
                    lives--;
                }

                if ((bunnyXOrigin >= (star5X + 40) && bunnyXOrigin <= star5X + 170) && ((bunnyYOrigin + 50) >= star5Y + 75 && bunnyYOrigin <= (star5Y + 75) + 180)) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    star5Y = 1400;
                    lives--;
                }
            }

            if (time >= 4) {
                star1Live = true;
            }

            if (time >= 8) {
                star2Live = true;
            }

            if (time >= 12) {
                star3Live = true;
            }

            if (time >= 16) {
                star4Live = true;
            }

            if (time >= 20) {
                star5Live = true;
            }

            if (star1Live) {
                star1Y += starSpeed;
            }

            if (star2Live) {
                star2Y += starSpeed;
            }

            if (star3Live) {
                star3Y += starSpeed;
            }

            if (star4Live) {
                star4Y += starSpeed;
            }

            if (star5Live) {
                star5Y += starSpeed;
            }

            if (star1Y >= 1300) {
                Random random1 = new Random();
                star1X = random1.nextInt(720 - 25) + 1;
                star1Y = -250;
            }

            if (star2Y >= 1300) {
                Random random2 = new Random();
                star2X = random2.nextInt(720 - 25) + 1;
                star2Y = -250;
            }

            if (star3Y >= 1300) {
                Random random3 = new Random();
                star3X = random3.nextInt(720 - 25) + 1;
                star3Y = -250;
            }

            if (star4Y >= 1300) {
                Random random4 = new Random();
                star4X = random4.nextInt(720 - 25) + 1;
                star4Y = -250;
            }

            if (star5Y >= 1300) {
                Random random4 = new Random();
                star5X = random4.nextInt(720 - 25) + 1;
                star5Y = -250;
            }

            //Bunny movement updates
            if (bunnyIsMovingRight) {
                bunnyX += 15;
            }

            if (bunnyIsMovingLeft) {
                bunnyX -= 15;
            }

            if (bunnyXOrigin <= 50) {
                bunnyIsMovingLeft = false;
            }

            if (bunnyXOrigin + 100 >= 775) {
                bunnyIsMovingRight = false;
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

                //Draw the bunny
                canvas.drawBitmap(bunnyBitMap, bunnyX, bunnyY, paint);

                //draw the star
                canvas.drawBitmap(starBitmap, star1X, star1Y, paint);
                canvas.drawBitmap(starBitmap, star2X, star2Y, paint);
                canvas.drawBitmap(starBitmap, star3X, star3Y, paint);
                canvas.drawBitmap(starBitmap, star4X, star4Y, paint);
                canvas.drawBitmap(starBitmap, star5X, star5Y, paint);

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
                        bunnyIsMovingRight = true;
                        bunnyIsMovingLeft = false;
                    } else {
                        bunnyIsMovingRight = false;
                        bunnyIsMovingLeft = true;
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    bunnyIsMovingRight = false;
                    bunnyIsMovingLeft = false;
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
}