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
import android.media.MediaPlayer;
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
    MediaPlayer music;

    Bunny bunny = new Bunny (320, 890);
    int bunnyInvincibleTime = 15;

    int startingAIY = -250;
    Random random = new Random();

    int randStarX1 = random.nextInt(600 - 10) + 1;
    Star star1 = new Star(randStarX1, startingAIY);

    int randStarX2 = random.nextInt(600 - 10) + 1;
    Star star2 = new Star(randStarX2, startingAIY);

    int randStarX3 = random.nextInt(600 - 10) + 1;
    Star star3 = new Star(randStarX3, startingAIY);

    int randStarX4 = random.nextInt(600 - 10) + 1;
    Star star4 = new Star(randStarX4, startingAIY);

    int randStarX5 = random.nextInt(600 - 10) + 1;
    Star star5 = new Star(randStarX5, startingAIY);

    int randStarX6 = random.nextInt(600 - 10) + 1;
    Star star6 = new Star(randStarX6, startingAIY);

    int randCarrotX = random.nextInt(600 - 10) + 1;
    Carrot carrot = new Carrot(randCarrotX, -70);

    int randBalloonX = random.nextInt(600 - 10) + 1;
    Balloon balloon = new Balloon(randBalloonX, -120);

    int randBlackholeX = random.nextInt(600 - 10) + 1;
    Blackhole blackhole = new Blackhole(randBlackholeX, -130);

    Bitmap bunnyBitMap1;
    Bitmap bunnyBitMap2;
    Bitmap bunnyBitMap3;
    Bitmap starBitmap;
    Bitmap backgroundBitMap;
    Bitmap carrotBitmap;
    Bitmap balloonBitmap;
    Bitmap blackholeBitmap;
    Bitmap menuBitmap;

    //Spawn time variables
    int carrotSpawnTime = 20;
    int balloonSpawnTime = 30;
    int blackholeSpawnTime = 60;

    int screenWidth;
    int screenHeight;
    int topGap;

    //sound settings
    Boolean soundEffectsOn;
    Boolean musicOn;

    //Difficulty settings
    int difficulty;

    //stats
    long lastFrameTime;
    int fps;

    //The size in pixels of a place on the game board
    int blockSize;
    int numBlocksWide;
    int numBlocksHigh;

    //Timer variable
    int time = 0;
    int timer = 0;
    int score = 0;
    Boolean timeLoop = true;
    Boolean carrotLoop = true;
    Boolean balloonLoop = true;
    Boolean blackholeLoop = true;

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
        difficulty = settingsData.getIntExtra("Difficulty", 1);
        soundEffectsOn = settingsData.getBooleanExtra("soundFX", true);
        musicOn = settingsData.getBooleanExtra("music", true);

        //Play music
        music = MediaPlayer.create(this, R.raw.lullaby);
        if (musicOn)
            music.start();

        //Set difficulty other than easy
        if (difficulty == 2) {
            setStarSpeed(12);
            carrotSpawnTime = 30;
            balloonSpawnTime = 45;
            blackholeSpawnTime = 40;
            bunnyInvincibleTime = 12;
        }
        else if (difficulty == 3) {
            setStarSpeed(14);
            carrotSpawnTime = 40;
            balloonSpawnTime = 60;
            blackholeSpawnTime = 20;
            bunnyInvincibleTime = 10;
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
            if (bunny.getBunnyLives() == 0) {
                music.stop();
            }

            if (bunny.getBunnyLives() != 0) {
                score = time;

                if (time >= 4)
                    star1.setStarLive(true);

                if (time >= 7)
                    star2.setStarLive(true);

                if (time >= 10)
                    star3.setStarLive(true);

                if (time >= 13)
                    star4.setStarLive(true);

                if (time >= 16)
                    star5.setStarLive(true);

                if (time >= 19)
                    star6.setStarLive(true);

                //Star movement code
                if (star1.getStarLive())
                    star1.addStarY(star1.getStarSpeed());

                if (star2.getStarLive())
                    star2.addStarY(star2.getStarSpeed());

                if (star3.getStarLive())
                    star3.addStarY(star3.getStarSpeed());

                if (star4.getStarLive())
                    star4.addStarY(star4.getStarSpeed());

                if (star5.getStarLive())
                    star5.addStarY(star5.getStarSpeed());

                if (star6.getStarLive())
                    star6.addStarY(star6.getStarSpeed());
            }

            if (carrot.getCarrotStart())
                if (time >= carrotSpawnTime) {
                    carrot.setCarrotLive(true);
                    carrot.setCarrotStart(false);
                }

            if (balloon.getBalloonStart())
                if (time >= balloonSpawnTime) {
                    balloon.setBalloonLive(true);
                    balloon.setBalloonStart(false);
                }

            if (blackhole.getBlackholeStart())
                if (time >= blackholeSpawnTime) {
                    blackhole.setBlackholeLive(true);
                    blackhole.setBlackholeStart(false);
                }

            double bunnyXOrigin = bunny.getBunnyX() + 41;
            double bunnyYOrigin = bunny.getBunnyY() + 129;

            //Collision code
            collision(bunnyXOrigin, bunnyYOrigin, star1, star2, star3, star4, star5, star6, carrot, balloon);

            //Bunny invincibility code
            if (bunny.getInvincible()) {
                while (timeLoop) {
                    timer = time;
                    timeLoop = false;
                }
                if ((time - timer) == bunnyInvincibleTime) {
                    bunny.setInvincible(false);
                    timeLoop = true;
                }
            }

            //Carrot respawn code
            if (carrot.getCarrotY() == (-80)) {
                while (carrotLoop) {
                    int timer = time;
                    carrotLoop = false;
                }
                if ((time - timer) == carrotSpawnTime) {
                    carrot.setCarrotLive(true);
                    carrotLoop = true;
                }
            }

            //Balloon respawn code
            if (balloon.getBalloonY() == (-120)) {
                while (balloonLoop) {
                    int timer = time;
                    balloonLoop = false;
                }
                if ((time - timer) == balloonSpawnTime) {
                    balloon.setBalloonLive(true);
                    balloonLoop = true;
                }
            }

            //Black hole respawn code
            if (blackhole.getBlackholeY() == (-135)) {
                while (blackholeLoop) {
                    int timer = time;
                    blackholeLoop = false;
                }
                if ((time - timer) == blackholeSpawnTime) {
                    blackhole.setBlackholeLive(true);
                    blackholeLoop = true;
                }
            }

            //If carrot, balloon, or black hole misses bunny
            if (carrot.getCarrotY() >= 1700) {
                carrot.setCarrotY(-80);
            }

            if (balloon.getBalloonY() >= 1700) {
                balloon.setBalloonY(-120);
            }

            if (blackhole.getBlackholeY() >= 1700) {
                blackhole.setBlackholeY(-135);
            }

            //Carrot movement code
            if (carrot.getCarrotLive()) {
                carrot.addCarrotY(carrot.getCarrotSpeed());
                carrot.setVisible(true);
            }

            //Balloon movement code
            if (balloon.getBalloonLive()) {
                balloon.addBalloonY(balloon.getBalloonSpeed());
                balloon.setVisible(true);
            }

            if (blackhole.getBlackholeLive()) {
                blackhole.addBlackholeY(blackhole.getBlackholeSpeed());
                blackhole.setVisible(true);
            }

            increaseStarSpeed(star1, time);
            increaseStarSpeed(star2, time);
            increaseStarSpeed(star3, time);
            increaseStarSpeed(star4, time);
            increaseStarSpeed(star5, time);
            increaseStarSpeed(star6, time);

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

            if (star6.getStarY() >= 1700) {
                setStarPosition(star6);
                star6.setVisible(true);
            }

            //Bunny movement updates
            if (bunny.getBunnyIsMovingRight()) {
                bunny.setBunnyX(15);
            }

            if (bunny.getBunnyIsMovingLeft()) {
                bunny.setBunnyX(-15);
            }

            if (bunnyXOrigin + 100 >= 775) {
                bunny.setBunnyIsMovingRight(false);
            }

            if (bunnyXOrigin <= 50) {
                bunny.setBunnyIsMovingLeft(false);
            }

            //End game code
            if (bunny.getBunnyLives() == 0) {
                star1.setStarLive(false);
                star1.setStarLive(false);
                star1.setStarLive(false);
                star1.setStarLive(false);
                star1.setStarLive(false);
                star1.setStarLive(false);
                carrot.setCarrotLive(false);
                balloon.setBalloonLive(false);
                blackhole.setBlackholeLive(false);
                musicOn = false;
            }
        }

        public void drawGame() {
            if (ourHolder.getSurface().isValid()) {
                canvas = ourHolder.lockCanvas();

                //Draw background
                canvas.drawBitmap(backgroundBitMap, 0, 0, paint);

                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(topGap / 2);

                canvas.drawText("Score:" + score, 10, topGap - 6, paint);
                canvas.drawText("Lives:" + bunny.getBunnyLives(), 10, 30, paint);

                if (bunny.getInvincible()) {
                    canvas.drawText("Invincible", 10, 125, paint);
                }


                //Start game caption
                if (time == 1) {
                    canvas.drawText("3", screenWidth / 2,
                            screenHeight / 2, paint);
                }

                if (time == 2) {
                    canvas.drawText("2", screenWidth / 2,
                            screenHeight / 2, paint);
                }

                if (time == 3) {
                    canvas.drawText("1", screenWidth / 2,
                            screenHeight / 2, paint);
                }

                if (time == 4) {
                    canvas.drawText("Start!", (screenWidth / 2) - 50,
                            screenHeight / 2, paint);
                }

                //Draw the bunny
                if (bunny.getBunnyLives() == 1)
                    canvas.drawBitmap(bunnyBitMap1, bunny.getBunnyX(),
                            bunny.getBunnyY(), paint);

                if (bunny.getBunnyLives() == 2)
                    canvas.drawBitmap(bunnyBitMap2, bunny.getBunnyX(),
                            bunny.getBunnyY(), paint);

                if (bunny.getBunnyLives() == 3) {
                    canvas.drawBitmap(bunnyBitMap3, bunny.getBunnyX(),
                            bunny.getBunnyY(), paint);
                }

                //draw the stars if they are set to visible
                if (star1.getVisible())
                    canvas.drawBitmap(starBitmap, star1.getStarX(),
                            star1.getStarY(), paint);

                if (star2.getVisible())
                    canvas.drawBitmap(starBitmap, star2.getStarX(),
                            star2.getStarY(), paint);

                if (star3.getVisible())
                    canvas.drawBitmap(starBitmap, star3.getStarX(),
                            star3.getStarY(), paint);

                if (star4.getVisible())
                    canvas.drawBitmap(starBitmap, star4.getStarX(),
                            star4.getStarY(), paint);

                if (star5.getVisible())
                    canvas.drawBitmap(starBitmap, star5.getStarX(),
                            star5.getStarY(), paint);

                if (star6.getVisible())
                    canvas.drawBitmap(starBitmap, star6.getStarX(),
                            star6.getStarY(), paint);

                //draw the carrot if it is set to visible
                if (carrot.getVisible())
                    canvas.drawBitmap(carrotBitmap, carrot.getCarrotX(),
                            carrot.getCarrotY(), paint);

                //Draw the balloon if it is set to visible
                if (balloon.getVisible())
                    canvas.drawBitmap(balloonBitmap, balloon.getBalloonX(),
                            balloon.getBalloonY(), paint);

                if (blackhole.getVisible()) {
                    canvas.drawBitmap(blackholeBitmap, blackhole.getBlackholeX(),
                            blackhole.getBlackholeY(), paint);
                }

                //End game drawing code
                if (bunny.getBunnyLives() == 0) {
                    canvas.drawText("Game Over!", (screenWidth / 2) - 120,
                            screenHeight / 2, paint);
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
        bunnyBitMap1 = BitmapFactory.decodeResource(getResources(), R.drawable.bunny_balloon_83x258);
        bunnyBitMap2 = BitmapFactory.decodeResource(getResources(), R.drawable.bunny_balloons_2);
        bunnyBitMap3 = BitmapFactory.decodeResource(getResources(), R.drawable.bunny_balloons_3);
        starBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.star_226x224);
        carrotBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.carrots);
        balloonBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.balloon);
        blackholeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blackhole);
        menuBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pausemenu_menuup);

        //scale the bitmaps to match the block size
        backgroundBitMap = Bitmap.createScaledBitmap(backgroundBitMap, 720, 1280, false);
        bunnyBitMap1 = Bitmap.createScaledBitmap(bunnyBitMap1, 80, 200, false);
        bunnyBitMap2 = Bitmap.createScaledBitmap(bunnyBitMap2, 80, 200, false);
        bunnyBitMap3 = Bitmap.createScaledBitmap(bunnyBitMap3, 80, 200, false);
        starBitmap = Bitmap.createScaledBitmap(starBitmap, 220, 220, false);
        carrotBitmap = Bitmap.createScaledBitmap(carrotBitmap, 65, 65, false);
        balloonBitmap = Bitmap.createScaledBitmap(balloonBitmap, 50, 120, false);
        blackholeBitmap = Bitmap.createScaledBitmap(blackholeBitmap, 70, 70, false);
        menuBitmap = Bitmap.createScaledBitmap(menuBitmap, 100, 50, false);
    }

    public void setStarPosition(Star star) {
        Random random = new Random();
        int starX = random.nextInt(600 - 10) + 1;
        int starY = -250;

        star.setStarX(starX);
        star.setStarY(starY);
    }

    public void setStarSpeed(double speed) {
        star1.setStarSpeed(speed);
        star2.setStarSpeed(speed);
        star3.setStarSpeed(speed);
        star4.setStarSpeed(speed);
        star5.setStarSpeed(speed);
        star6.setStarSpeed(speed);
    }

    public void increaseStarSpeed(Star star, int time) {
        if (time % 10 == 0 && star.getStarSpeed() <= 15)
            star.addStarSpeed(0.0125);
    }

    public void collision(double bunnyXOrigin, double bunnyYOrigin, Star star1, Star star2, Star star3, Star star4, Star star5, Star star6, Carrot carrot, Balloon balloon) {
        if (bunny.getBunnyLives() == 1) {
            if (!bunny.getInvincible()) {
                if ((bunnyXOrigin >= (star1.getStarX() + 40) && bunnyXOrigin <= star1.getStarX() + 170) && ((bunnyYOrigin + 50) >= star1.getStarY() + 75 && bunnyYOrigin <= (star1.getStarY() + 75) + 180) && star1.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star1.setVisible(false);
                }

                if ((bunnyXOrigin >= (star2.getStarX() + 40) && bunnyXOrigin <= star2.getStarX() + 170) && ((bunnyYOrigin + 50) >= star2.getStarY() + 75 && bunnyYOrigin <= (star2.getStarY() + 75) + 180) && star2.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star2.setVisible(false);
                }

                if ((bunnyXOrigin >= (star3.getStarX() + 40) && bunnyXOrigin <= star3.getStarX() + 170) && ((bunnyYOrigin + 50) >= star3.getStarY() + 75 && bunnyYOrigin <= (star3.getStarY() + 75) + 180) && star3.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star3.setVisible(false);
                }

                if ((bunnyXOrigin >= (star4.getStarX() + 40) && bunnyXOrigin <= star4.getStarX() + 170) && ((bunnyYOrigin + 50) >= star4.getStarY() + 75 && bunnyYOrigin <= (star4.getStarY() + 75) + 180) && star4.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star4.setVisible(false);
                }

                if ((bunnyXOrigin >= (star5.getStarX() + 40) && bunnyXOrigin <= star5.getStarX() + 170) && ((bunnyYOrigin + 50) >= star5.getStarY() + 75 && bunnyYOrigin <= (star5.getStarY() + 75) + 180) && star5.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star5.setVisible(false);
                }

                if ((bunnyXOrigin >= (star6.getStarX() + 40) && bunnyXOrigin <= star6.getStarX() + 170) && ((bunnyYOrigin + 50) >= star6.getStarY() + 75 && bunnyYOrigin <= (star6.getStarY() + 75) + 180) && star6.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star6.setVisible(false);
                }
            }

            //Carrot collision code
            if ((bunnyXOrigin >= (carrot.getCarrotX() - 30)) && (bunnyXOrigin <= (carrot.getCarrotX() + 100)) && ((bunnyYOrigin + 50) >= (carrot.getCarrotY())) && (bunnyYOrigin <= (carrot.getCarrotY() + 190)) && carrot.getVisible()) {
                bunny.setInvincible(true);
                carrot.setVisible(false);
                carrot.setCarrotLive(false);

                int newRandCarrotX = random.nextInt(600 - 10) + 1;
                carrot.setCarrotX(newRandCarrotX);
                carrot.setCarrotY(-80);
            }

            //Balloon collision code
            if ((bunnyXOrigin >= (balloon.getBalloonX() - 30)) && (bunnyXOrigin <= (balloon.getBalloonX() + 80)) && ((bunnyYOrigin + 50) >= (balloon.getBalloonY())) && (bunnyYOrigin <= (balloon.getBalloonY() + 245)) && balloon.getVisible()) {
                if (bunny.getBunnyLives() < 3)
                    bunny.addBunnyLife(1);
                balloon.setVisible(false);
                balloon.setBalloonLive(false);

                int newRandBalloonX = random.nextInt(600 - 10) + 1;
                balloon.setBalloonX(newRandBalloonX);
                balloon.setBalloonY(-120);
            }

            if ((bunnyXOrigin >= (blackhole.getBlackholeX() - 30)) && (bunnyXOrigin <= (blackhole.getBlackholeX() + 100)) && ((bunnyYOrigin + 50) >= (blackhole.getBlackholeY())) && (bunnyYOrigin <= (blackhole.getBlackholeY() + 190)) && blackhole.getVisible()) {
                blackhole.setVisible(false);
                blackhole.setBlackholeLive(false);

                int newRandBlackholeX = random.nextInt(600 - 10) + 1;
                blackhole.setBlackholeX(newRandBlackholeX);
                blackhole.setBlackholeY(-135);

                bunny.death();
            }
        }

        if (bunny.getBunnyLives() == 2) {
            if (!bunny.getInvincible()) {
                if ((bunnyXOrigin >= (star1.getStarX() + 50) && bunnyXOrigin <= star1.getStarX() + 180) && ((bunnyYOrigin + 50) >= star1.getStarY() + 75 && bunnyYOrigin <= (star1.getStarY() + 75) + 180) && star1.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star1.setVisible(false);
                }

                if ((bunnyXOrigin >= (star2.getStarX() + 50) && bunnyXOrigin <= star2.getStarX() + 180) && ((bunnyYOrigin + 50) >= star2.getStarY() + 75 && bunnyYOrigin <= (star2.getStarY() + 75) + 180) && star2.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star2.setVisible(false);
                }

                if ((bunnyXOrigin >= (star3.getStarX() + 50) && bunnyXOrigin <= star3.getStarX() + 180) && ((bunnyYOrigin + 50) >= star3.getStarY() + 75 && bunnyYOrigin <= (star3.getStarY() + 75) + 180) && star3.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star3.setVisible(false);
                }

                if ((bunnyXOrigin >= (star4.getStarX() + 50) && bunnyXOrigin <= star4.getStarX() + 180) && ((bunnyYOrigin + 50) >= star4.getStarY() + 75 && bunnyYOrigin <= (star4.getStarY() + 75) + 180) && star4.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star4.setVisible(false);
                }

                if ((bunnyXOrigin >= (star5.getStarX() + 50) && bunnyXOrigin <= star5.getStarX() + 180) && ((bunnyYOrigin + 50) >= star5.getStarY() + 75 && bunnyYOrigin <= (star5.getStarY() + 75) + 180) && star5.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star5.setVisible(false);
                }

                if ((bunnyXOrigin >= (star6.getStarX() + 50) && bunnyXOrigin <= star6.getStarX() + 180) && ((bunnyYOrigin + 50) >= star6.getStarY() + 75 && bunnyYOrigin <= (star6.getStarY() + 75) + 180) && star6.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star6.setVisible(false);
                }
            }

            //Carrot collision code
            if ((bunnyXOrigin >= (carrot.getCarrotX() - 30)) && (bunnyXOrigin <= (carrot.getCarrotX() + 100)) && ((bunnyYOrigin + 50) >= (carrot.getCarrotY())) && (bunnyYOrigin <= (carrot.getCarrotY() + 190)) && carrot.getVisible()) {
                bunny.setInvincible(true);
                carrot.setVisible(false);
                carrot.setCarrotLive(false);

                int newRandCarrotX = random.nextInt(600 - 10) + 1;
                carrot.setCarrotX(newRandCarrotX);
                carrot.setCarrotY(-80);
            }

            //Balloon collision code
            if ((bunnyXOrigin >= (balloon.getBalloonX() - 30)) && (bunnyXOrigin <= (balloon.getBalloonX() + 80)) && ((bunnyYOrigin + 50) >= (balloon.getBalloonY())) && (bunnyYOrigin <= (balloon.getBalloonY() + 245)) && balloon.getVisible()) {
                if (bunny.getBunnyLives() < 3)
                    bunny.addBunnyLife(1);
                balloon.setVisible(false);
                balloon.setBalloonLive(false);

                int newRandBalloonX = random.nextInt(600 - 10) + 1;
                balloon.setBalloonX(newRandBalloonX);
                balloon.setBalloonY(-120);
            }

            if ((bunnyXOrigin >= (blackhole.getBlackholeX() - 30)) && (bunnyXOrigin <= (blackhole.getBlackholeX() + 100)) && ((bunnyYOrigin + 50) >= (blackhole.getBlackholeY())) && (bunnyYOrigin <= (blackhole.getBlackholeY() + 190)) && blackhole.getVisible()) {
                blackhole.setVisible(false);
                blackhole.setBlackholeLive(false);

                int newRandBlackholeX = random.nextInt(600 - 10) + 1;
                blackhole.setBlackholeX(newRandBlackholeX);
                blackhole.setBlackholeY(-135);

                bunny.death();
            }
        }

        if (bunny.getBunnyLives() == 3) {
            if (!bunny.getInvincible()) {
                if ((bunnyXOrigin >= (star1.getStarX() + 70) && bunnyXOrigin <= star1.getStarX() + 200) && ((bunnyYOrigin + 50) >= star1.getStarY() + 75 && bunnyYOrigin <= (star1.getStarY() + 75) + 180) && star1.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star1.setVisible(false);
                }

                if ((bunnyXOrigin >= (star2.getStarX() + 70) && bunnyXOrigin <= star2.getStarX() + 200) && ((bunnyYOrigin + 50) >= star2.getStarY() + 75 && bunnyYOrigin <= (star2.getStarY() + 75) + 180) && star2.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star2.setVisible(false);
                }

                if ((bunnyXOrigin >= (star3.getStarX() + 70) && bunnyXOrigin <= star3.getStarX() + 200) && ((bunnyYOrigin + 50) >= star3.getStarY() + 75 && bunnyYOrigin <= (star3.getStarY() + 75) + 180) && star3.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star3.setVisible(false);
                }

                if ((bunnyXOrigin >= (star4.getStarX() + 70) && bunnyXOrigin <= star4.getStarX() + 200) && ((bunnyYOrigin + 50) >= star4.getStarY() + 75 && bunnyYOrigin <= (star4.getStarY() + 75) + 180) && star4.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star4.setVisible(false);
                }

                if ((bunnyXOrigin >= (star5.getStarX() + 70) && bunnyXOrigin <= star5.getStarX() + 200) && ((bunnyYOrigin + 50) >= star5.getStarY() + 75 && bunnyYOrigin <= (star5.getStarY() + 75) + 180) && star5.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star5.setVisible(false);
                }

                if ((bunnyXOrigin >= (star6.getStarX() + 70) && bunnyXOrigin <= star6.getStarX() + 200) && ((bunnyYOrigin + 50) >= star6.getStarY() + 75 && bunnyYOrigin <= (star6.getStarY() + 75) + 180) && star6.getVisible()) {
                    if (soundEffectsOn)
                        sound.playHitSound();
                    bunny.addBunnyLife(-1);
                    star6.setVisible(false);
                }
            }

            //Carrot collision code
            if ((bunnyXOrigin >= (carrot.getCarrotX() - 30)) && (bunnyXOrigin <= (carrot.getCarrotX() + 100)) && ((bunnyYOrigin + 50) >= (carrot.getCarrotY())) && (bunnyYOrigin <= (carrot.getCarrotY() + 190)) && carrot.getVisible()) {
                bunny.setInvincible(true);
                carrot.setVisible(false);
                carrot.setCarrotLive(false);

                int newRandCarrotX = random.nextInt(600 - 10) + 1;
                carrot.setCarrotX(newRandCarrotX);
                carrot.setCarrotY(-80);
            }

            //Balloon collision code
            if ((bunnyXOrigin >= (balloon.getBalloonX() - 30)) && (bunnyXOrigin <= (balloon.getBalloonX() + 80)) && ((bunnyYOrigin + 50) >= (balloon.getBalloonY())) && (bunnyYOrigin <= (balloon.getBalloonY() + 245)) && balloon.getVisible()) {
                if (bunny.getBunnyLives() < 3)
                    bunny.addBunnyLife(1);
                balloon.setVisible(false);
                balloon.setBalloonLive(false);

                int newRandBalloonX = random.nextInt(600 - 10) + 1;
                balloon.setBalloonX(newRandBalloonX);
                balloon.setBalloonY(-120);
            }
            if ((bunnyXOrigin >= (blackhole.getBlackholeX() - 30)) && (bunnyXOrigin <= (blackhole.getBlackholeX() + 100)) && ((bunnyYOrigin + 50) >= (blackhole.getBlackholeY())) && (bunnyYOrigin <= (blackhole.getBlackholeY() + 190)) && blackhole.getVisible()) {
                blackhole.setVisible(false);
                blackhole.setBlackholeLive(false);

                int newRandBlackholeX = random.nextInt(600 - 10) + 1;
                blackhole.setBlackholeX(newRandBlackholeX);
                blackhole.setBlackholeY(-135);

                bunny.death();
            }
        }
    }
}