package com.example.cam.nim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends Activity {

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
                Intent playIntent = new Intent(this,OptionsActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putBoolean("PlayWithComp",true);
                playIntent.putExtra("mBundle", mBundle);
                startActivity(playIntent);
                finish();
                break;
            }
            case(R.id.playWithFriend):
            {   Intent playIntent = new Intent(this,OptionsActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putBoolean("PlayWithComp",false);
                playIntent.putExtra("mBundle",mBundle);
                startActivity(playIntent);
                finish();
                break;
            }

            case(R.id.Scoreboard):
            {
                Intent scoreIntent = new Intent(this,ScoreboardActivity.class);
                startActivity(scoreIntent);
                finish();
                break;
            }
            case(R.id.Credits):
            {
                Intent creditsButton = new Intent(this,CreditsActivity.class);
                startActivity(creditsButton);
                finish();
                break;
            }

        }
    }
}
