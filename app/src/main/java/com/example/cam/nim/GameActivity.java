package com.example.cam.nim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class GameActivity extends Activity
{
    private GameInfo mGameInfo;
    private Button mEndButton;
    private LinearLayout mGameBoardContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mGameBoardContainer = (LinearLayout) findViewById(R.id.gameboard_container);
        mGameBoardContainer.removeAllViews();

        mEndButton = (Button) findViewById(R.id.end_game_button);
        mEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(v.getContext(), MainMenuActivity.class);
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

        mGameInfo.populateGameBoard();
        createGameBoard();

    }

    private void updateGameBoard()
    {

    }

    private void createGameBoard()
    {
        for( int i = 0; i < mGameInfo.getnRowAmount(); i++)
        {
            LinearLayout temp = new LinearLayout(GameActivity.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 0);
            temp.setLayoutParams(layoutParams);
            temp.setOrientation(LinearLayout.HORIZONTAL);
            temp.setGravity(Gravity.CENTER_HORIZONTAL);
            for(int j = 0; j < i+1; j++)
            {
                ImageButton tempButton = new ImageButton(GameActivity.this);
                tempButton.setImageResource(R.drawable.game_piece);
                temp.addView(tempButton);
            }
            mGameBoardContainer.addView(temp);
        }

    }
}
