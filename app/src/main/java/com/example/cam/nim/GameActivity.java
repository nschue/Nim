package com.example.cam.nim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameActivity extends Activity
{
    private GameInfo mGameInfo;
    private Button mEndButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mEndButton = (Button) findViewById(R.id.end_game_button);
        mEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(v.getContext(),MainMenuActivity.class);
                startActivity(menuIntent);
                finish();
            }
        });


        /*Unbundles extras passed from OptionsActivity to populate local GameInfo object*/
        Bundle extras = getIntent().getExtras();
        mGameInfo = new GameInfo();
        mGameInfo.setBoolEnableAudio(extras.getBoolean("boolEnableAudio"));
        mGameInfo.setBoolPlayerTurn(extras.getBoolean("boolPlayerTurn"));
        mGameInfo.setComputerDifficulty(extras.getDouble("computerDifficulty"));
        mGameInfo.setnRowAmount(extras.getInt("rowAmount"));
        mGameInfo.setComputerSpeed(extras.getDouble("computerSpeed"));
    }
}
