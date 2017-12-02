package com.andrewvanpeter.upandaway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Intent game;
    Intent help;
    Intent highscores;
    Intent settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        //Intent for GameActivity
        game = new Intent(this, GameActivity.class);
        help = new Intent(this, HelpActivity.class);
        highscores = new Intent(this, HighScoresActivity.class);
        settings = new Intent(this, SettingsActivity.class);

        //Button object for playButton
        final Button playButton = (Button) findViewById(R.id.playButton);
        final Button helpButton = (Button) findViewById(R.id.helpButton);
        final Button settingsButton = (Button) findViewById(R.id.settingsButton);

        Intent settingsData = getIntent();
        final int difficulty = settingsData.getIntExtra("Difficulty", 0);
        final Boolean soundFX = settingsData.getBooleanExtra("soundFX", true);

        //Listener for playButton
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //start GameActivity
                game.putExtra("Difficulty", difficulty);
                game.putExtra("soundFx", soundFX);
                startActivity(game);
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //start HelpActivity
                startActivity(help);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //start SettingsActivity
                startActivity(settings);
            }
        });
    }
}