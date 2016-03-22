package com.example.cam.nim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;

public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }
  //launches the activites based on the button selections
    public void onClick(View view) {
        switch(view.getId())
        {
            case(R.id.playMenuButton):
            {
                Intent playIntent = new Intent(this,Menu.class);
                startActivity(playIntent);
                finish();
                break;
            }
            case(R.id.Scoreboard):
            {

                break;
            }
            case(R.id.Credits):
            {
                Intent creditsButton = new Intent(this,Credits.class);
                startActivity(creditsButton);
                finish();
                break;
            }

            /*case(R.id.optionsButton):
            {
                Intent optionsButton = new Intent(this,Options.class);
                startActivity(optionsButton);
                finish();
                break;
            }*/
        }
    }
}
