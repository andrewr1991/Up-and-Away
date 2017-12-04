package com.andrewvanpeter.upandaway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        final Button backButton = (Button) findViewById(R.id.backButton);

        final Intent backToMain = new Intent(this, MainActivity.class);

        //Back to main menu
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backButton.setBackgroundResource(R.drawable.settings_donedown);
                startActivity(backToMain);
            }
        });
    }
}
