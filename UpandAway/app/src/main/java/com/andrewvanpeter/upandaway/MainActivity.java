package com.andrewvanpeter.upandaway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Intent game;
    Intent help;
    Intent highscores;
    Intent settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent for GameActivity
        game = new Intent(this, GameActivity.class);
        help = new Intent(this, HelpActivity.class);
        highscores = new Intent(this, HighScoresActivity.class);
        settings = new Intent(this, SettingsActivity.class);

        //Button object for playButton
        final Button playButton = (Button) findViewById(R.id.playButton);
        final Button helpButton = (Button) findViewById(R.id.helpButton);
        final Button highscoresButton = (Button) findViewById(R.id.highscoresButton);
        final Button settingsButton = (Button) findViewById(R.id.settingsButton);


        //Listener for playButton
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //start GameActivity
                startActivity(game);
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //start HelpActivity
                startActivity(help);
            }
        });

        highscoresButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //start HighScoresActivity
                startActivity(highscores);
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