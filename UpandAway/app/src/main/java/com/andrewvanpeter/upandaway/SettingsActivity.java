package com.andrewvanpeter.upandaway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.andrewvanpeter.upandaway.GameActivity;

public class SettingsActivity extends AppCompatActivity {
    int difficulty = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Intent backToMain = new Intent(this, MainActivity.class);

        final Button easyButton = (Button) findViewById(R.id.easyButton);
        final Button mediumButton = (Button) findViewById(R.id.mediumButton);
        final Button hardButton = (Button) findViewById(R.id.hardButton);
        final Button backToMainButton = (Button) findViewById(R.id.backToMainButton);

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
                startActivity(backToMain);
            }
        });
    }
}
