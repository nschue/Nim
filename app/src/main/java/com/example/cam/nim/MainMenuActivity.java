package com.example.cam.nim;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainMenuActivity extends Activity {
    private Dialog creditsDialog;
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
                creditsDialog();
                break;
            }

        }
    }
    public void creditsDialog() {

        creditsDialog = new Dialog(MainMenuActivity.this);
        creditsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        creditsDialog.setContentView(R.layout.dialog_credits);
        creditsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button okayButton = (Button) creditsDialog.findViewById(R.id.okayCredit);
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creditsDialog.dismiss();
            }
        });
        creditsDialog.show();
    }
}

