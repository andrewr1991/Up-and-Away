//Base code taken from Learning Java by Building Android Games by John Horton

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
    Intent settings;
    Boolean musicOn;
    Boolean soundFXOn;
    int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        //Intent for GameActivity
        game = new Intent(this, GameActivity.class);
        help = new Intent(this, HelpActivity.class);
        settings = new Intent(this, SettingsActivity.class);

        //Button object for playButton
        final Button playButton = (Button) findViewById(R.id.playButton);
        final Button helpButton = (Button) findViewById(R.id.helpButton);
        final Button settingsButton = (Button) findViewById(R.id.settingsButton);

        Intent settingsData = getIntent();
        difficulty = settingsData.getIntExtra("Difficulty", 0);
        soundFXOn = settingsData.getBooleanExtra("soundFX", true);
        musicOn = settingsData.getBooleanExtra("music", true);

        //Listener for playButton
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Change button background
                playButton.setBackgroundResource(R.drawable.title_button_play_down);

                //start GameActivity
                game.putExtra("Difficulty", difficulty);
                game.putExtra("soundFX", soundFXOn);
                game.putExtra("music", musicOn);
                startActivity(game);
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Change button background
                helpButton.setBackgroundResource(R.drawable.title_button_help_down);

                //start HelpActivity
                startActivity(help);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Change button background
                settingsButton.setBackgroundResource(R.drawable.title_button_settings_down);

                //start SettingsActivity
                settings.putExtra("Difficulty", difficulty);
                settings.putExtra("soundFX", soundFXOn);
                settings.putExtra("music", musicOn);
                startActivity(settings);
            }
        });
    }
}