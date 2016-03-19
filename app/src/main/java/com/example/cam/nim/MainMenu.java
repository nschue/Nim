package com.example.cam.nim;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {
    Button extraButton,optionButton,playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        playButton = (Button)findViewById(R.id.playMenuButton);
        extraButton = (Button)findViewById(R.id.extrasButton);
        optionButton = (Button)findViewById(R.id.optionsButton);
    }

    public void onClick(View view) {
        switch(view.getId())
        {
            case(R.id.playMenuButton):
            {
                Intent playIntent = new Intent(this,Game.class);
                startActivity(playIntent);
                break;
            }
            case(R.id.extrasButton):
            {
                Intent extraIntent = new Intent(this,Extra.class);
                startActivity(extraIntent);
                break;
            }
            case(R.id.optionsButton):
            {
                Intent optionsButton = new Intent(this,Options.class);
                startActivity(optionsButton);
                break;
            }
        }
    }
}
