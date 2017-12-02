package com.andrewvanpeter.upandaway;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.andrewvanpeter.upandaway.GameActivity;

public class SettingsActivity extends AppCompatActivity {
    int difficulty = 1;
    Boolean soundFXOn = true;
    Switch soundFXSwitch;
    public static final String PREFS_NAME = "soundFXSettingd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean silent = settings.getBoolean("soundFXOn", true);

        final Intent backToMain = new Intent(this, MainActivity.class);

        //Button code
        final Button easyButton = (Button) findViewById(R.id.easyButton);
        final Button mediumButton = (Button) findViewById(R.id.mediumButton);
        final Button hardButton = (Button) findViewById(R.id.hardButton);
        final Button backToMainButton = (Button) findViewById(R.id.backToMainButton);

        //Switch code
        soundFXSwitch = (Switch) findViewById(R.id.soundFXSwitch);

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

        backToMainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backToMain.putExtra("Difficulty", difficulty);
                backToMain.putExtra("soundFX", soundFXOn);
                startActivity(backToMain);
            }
        });

        soundFXSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (soundFXSwitch.isChecked()) {
                    soundFXOn = true;
                }
                else {
                    soundFXOn = false;
                }

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("soundFXOn", soundFXOn);
                editor.commit();

            }
        });
    }
}
