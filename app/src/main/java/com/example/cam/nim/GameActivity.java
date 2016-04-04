package com.example.cam.nim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class GameActivity extends Activity
{
    private GameInfo mGameInfo;
    private Button mEndButton;
    private Button mEndTurnButton;
    private LinearLayout mGameBoardContainer;
    private ArrayList<Integer> mSelectedPieces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mSelectedPieces = new ArrayList<>();

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


        mEndTurnButton = (Button) findViewById(R.id.endTurnButton);

        //Listens for endturn button to be pressed.
        //Hides and disables buttons that are listed in mSelectedPieces
        //Needs to check for player turn.
        mEndTurnButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                for(Integer id: mSelectedPieces)
                {
                    View selectedButton = findViewById(id);
                    selectedButton.setEnabled(false);
                    selectedButton.setVisibility(View.GONE);
                }
            }
        });


        /*Unbundles extras passed from OptionsActivity to populate local GameInfo object*/
        Bundle extras = getIntent().getBundleExtra("mBundle");
        mGameInfo = new GameInfo();
        mGameInfo.setBoolEnableAudio(extras.getBoolean("boolEnableAudio"));
        mGameInfo.setBoolPlayerTurn(extras.getBoolean("boolPlayerTurn"));
        mGameInfo.setComputerDifficulty(extras.getDouble("computerDifficulty"));
        mGameInfo.setnRowAmount(extras.getInt("rowAmount"));
        mGameInfo.setComputerSpeed(extras.getDouble("computerSpeed"));

        //mGameInfo.populateGameBoard();
        createGameBoard();

    }

    private void updateGameBoard()
    {

    }

    //Creates the gameboard using number of rows.
    //Each image button is created with an onClickListener to listen for selection. 
    private void createGameBoard()
    {
        int buttonID = 0;
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
                tempButton.setId(buttonID++);
                tempButton.setBackground(null);
                tempButton.setPadding(0,0,0,0);
                temp.addView(tempButton);

                //If user clicks button, button is added to a list of buttons to be removed.
                //Needs to check for player turn and if button is in same row.
                tempButton.setOnClickListener(new ImageButton.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mSelectedPieces.add(v.getId());
                    }
                });
            }
            mGameBoardContainer.addView(temp);
        }

    }

    private void playGame()
    {

    }
    public void onHowToPlayClick(View view)
    {
        Intent howToPlayIntent = new Intent(this,HowToPlay.class);
        startActivity(howToPlayIntent);

    }
}
