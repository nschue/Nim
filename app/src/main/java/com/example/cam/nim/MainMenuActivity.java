package com.example.cam.nim;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainMenuActivity extends Activity {
    private Dialog creditsDialog,selectScoreBoard;
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
                showBoardSelection();
                break;
            }
            case(R.id.Credits):
            {
                creditsDialog();
                break;
            }

        }
    }

    public void showBoardSelection()
    {
        selectScoreBoard = new Dialog(MainMenuActivity.this);
        selectScoreBoard.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectScoreBoard.setContentView(R.layout.dailog_scoreboard);
        selectScoreBoard.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button easyButton = (Button) selectScoreBoard.findViewById(R.id.easyButton);
        Button medButton = (Button) selectScoreBoard.findViewById(R.id.medButton);
        Button hardButton = (Button) selectScoreBoard.findViewById(R.id.hardButton);
        Button friendButton = (Button) selectScoreBoard.findViewById(R.id.friendButton);


        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreIntent = new Intent(MainMenuActivity.this,ScoreboardActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putInt("gameType",0);
                scoreIntent.putExtra("typeBundle", mBundle);
                startActivity(scoreIntent);
                selectScoreBoard.dismiss();
            }
        });
        medButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreIntent = new Intent(MainMenuActivity.this,ScoreboardActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putInt("gameType",1);
                scoreIntent.putExtra("typeBundle", mBundle);
                startActivity(scoreIntent);
                selectScoreBoard.dismiss();
            }
        });
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreIntent = new Intent(MainMenuActivity.this,ScoreboardActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putInt("gameType",2);
                scoreIntent.putExtra("typeBundle", mBundle);
                startActivity(scoreIntent);
                selectScoreBoard.dismiss();
            }
        });
        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreIntent = new Intent(MainMenuActivity.this,ScoreboardActivity.class);
                Bundle mBundle = new Bundle();
                scoreIntent.putExtra("typeBundle", mBundle);
                mBundle.putInt("gameType", -1);
                startActivity(scoreIntent);
                selectScoreBoard.dismiss();
            }
        });

        selectScoreBoard.show();
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

