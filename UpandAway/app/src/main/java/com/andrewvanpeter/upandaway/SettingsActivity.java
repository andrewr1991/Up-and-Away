package com.andrewvanpeter.upandaway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {
    Boolean soundFXOn;
    int difficulty;

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

        switch(difficulty) {
            case 1:
                //Set new button background for easy
                easyButton.setBackgroundResource(R.drawable.settings_easydown);
                break;
            case 2:
                //Set new button background for medium
                mediumButton.setBackgroundResource(R.drawable.settings_meddown);
                break;
            case 3:
                //Set new button background for hard
                hardButton.setBackgroundResource(R.drawable.settings_harddown);
                break;
            default:
                break;
        }

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

        //Back to main menu
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backToMain.putExtra("Difficulty", difficulty);
                backToMain.putExtra("soundFX", soundFXOn);
                startActivity(backToMain);
            }
        });
    }
}
