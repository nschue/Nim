package com.example.cam.nim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Extra extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

    }

    public void onClick(View view) {
        switch(view.getId()) {

            case(R.id.Credits):
                break;
            case(R.id.howToPlay):
                Intent howToPlayIntent = new Intent(this,HowToPlay.class);
                startActivity(howToPlayIntent);
                finish();
                break;
            case(R.id.Scoreboard):
                break;
            case(R.id.Back):
                break;

        }
    }

 }

