package com.andrewvanpeter.upandaway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    Boolean soundFXOn;
    Switch soundFXSwitch;
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
        final Button backToMainButton = (Button) findViewById(R.id.backToMainButton);

        //Switch code
        soundFXSwitch = (Switch) findViewById(R.id.soundFXSwitch);

        Intent settingsData = getIntent();
        difficulty = settingsData.getIntExtra("Difficulty", 0);

        switch(difficulty) {
            case 1:
                //Set new button background for easy
                break;
            case 2:
                //Set new button background for easy
                break;
            case 3:
                //Set new button background for easy
                break;
            default:
                break;
        }

        easyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Set difficulty to easy
                difficulty = 1;
            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Set difficulty to medium
                difficulty = 2;
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Set difficulty to hard
                difficulty = 3;
            }
        });

        //Back to main menu
        backToMainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backToMain.putExtra("Difficulty", difficulty);
                backToMain.putExtra("soundFX", soundFXOn);
                startActivity(backToMain);
            }
        });
    }
}
