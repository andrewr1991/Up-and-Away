package com.andrewvanpeter.upandaway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {
    int difficulty;
    Boolean soundFXOn;
    Boolean musicOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Intent backToMain = new Intent(this, MainActivity.class);

        //Button code
        final Button easyButton = (Button) findViewById(R.id.easyButton);
        final Button mediumButton = (Button) findViewById(R.id.mediumButton);
        final Button hardButton = (Button) findViewById(R.id.hardButton);
        final Button backButton = (Button) findViewById(R.id.backButton);
        final Button musicButton = (Button) findViewById(R.id.musicButton);
        final Button soundFXButton = (Button) findViewById(R.id.soundFXButton);

        Intent settingsData = getIntent();
        difficulty = settingsData.getIntExtra("Difficulty", 0);
        soundFXOn = settingsData.getBooleanExtra("soundFX", true);
        musicOn = settingsData.getBooleanExtra("music", true);

        switch(difficulty) {
            case 1:
                //Set new button background for easy
                easyButton.setBackgroundResource(R.drawable.settings_easydown);
                mediumButton.setBackgroundResource(R.drawable.settings_medup);
                hardButton.setBackgroundResource(R.drawable.settings_hardup);
                break;
            case 2:
                //Set new button background for medium
                mediumButton.setBackgroundResource(R.drawable.settings_meddown);
                easyButton.setBackgroundResource(R.drawable.settings_easyup);
                hardButton.setBackgroundResource(R.drawable.settings_hardup);
                break;
            case 3:
                //Set new button background for hard
                hardButton.setBackgroundResource(R.drawable.settings_harddown);
                easyButton.setBackgroundResource(R.drawable.settings_easyup);
                mediumButton.setBackgroundResource(R.drawable.settings_medup);
                break;
            default:
                break;
        }

        if (musicOn)
            musicButton.setBackgroundResource(R.drawable.settings_switchon);
        else
            musicButton.setBackgroundResource(R.drawable.settings_switchoff);

        if (soundFXOn)
            soundFXButton.setBackgroundResource(R.drawable.settings_switchon);
        else
            soundFXButton.setBackgroundResource(R.drawable.settings_switchoff);

        //Easy button code
        easyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Set difficulty to easy
                difficulty = 1;
                easyButton.setBackgroundResource(R.drawable.settings_easydown);

                if (difficulty == 1) {
                    mediumButton.setBackgroundResource(R.drawable.settings_medup);
                    hardButton.setBackgroundResource(R.drawable.settings_hardup);
                }
            }
        });

        //Medium button code
        mediumButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Set difficulty to medium
                difficulty = 2;

                mediumButton.setBackgroundResource(R.drawable.settings_meddown);

                if (difficulty == 2) {
                    easyButton.setBackgroundResource(R.drawable.settings_easyup);
                    hardButton.setBackgroundResource(R.drawable.settings_hardup);
                }
            }
        });

        //Hard button code
        hardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Set difficulty to hard
                difficulty = 3;

                hardButton.setBackgroundResource(R.drawable.settings_harddown);

                if (difficulty == 3) {
                    easyButton.setBackgroundResource(R.drawable.settings_easyup);
                    mediumButton.setBackgroundResource(R.drawable.settings_medup);
                }
            }
        });

        //Music button code
        musicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (musicOn) {
                    musicOn = false;
                    musicButton.setBackgroundResource(R.drawable.settings_switchoff);
                }
                else if (!musicOn) {
                    musicOn = true;
                    musicButton.setBackgroundResource(R.drawable.settings_switchon);
                }
            }
        });

        //SoundFX button code
        soundFXButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (soundFXOn) {
                    soundFXOn = false;
                    soundFXButton.setBackgroundResource(R.drawable.settings_switchoff);
                }
                else if (!soundFXOn) {
                    soundFXOn = true;
                    soundFXButton.setBackgroundResource(R.drawable.settings_switchon);
                }
            }
        });

        //Back to main menu
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backButton.setBackgroundResource(R.drawable.settings_donedown);
                backToMain.putExtra("Difficulty", difficulty);
                backToMain.putExtra("soundFX", soundFXOn);
                backToMain.putExtra("music", musicOn);
                startActivity(backToMain);
            }
        });
    }
}
