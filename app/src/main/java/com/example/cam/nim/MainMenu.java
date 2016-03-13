package com.example.cam.nim;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void play(View view){
        Intent playIntent = new Intent(this,GameStatus.class);
    }

    public void options(View view){
        Intent optionsIntent = new Intent(this,Options.class);
    }
    public void extra(View view){
        Intent extraIntent = new Intent(this,Extra.class);
        startActivity(extraIntent);
        finish();

    }
}
