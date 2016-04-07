package com.example.cam.nim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GameActivity extends Activity
{
    private GameInfo mGameInfo;
    private Button mEndButton;
    private Button mEndTurnButton;
    private LinearLayout mGameBoardContainer;
    private ArrayList<Integer> mSelectedPieces;
    private TextView currentPlayer;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] choices;
    private final Animation fadeInPlayerText = new AlphaAnimation(0.0f,1.0f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mSelectedPieces = new ArrayList<>();

        choices = getResources().getStringArray(R.array.NavigatorBar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        //mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item,choices));
        // Set the list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        /*Unbundles extras passed from OptionsActivity to populate local GameInfo object*/
        Bundle extras = getIntent().getBundleExtra("mBundle");
        this.mGameInfo = new GameInfo();
        this.mGameInfo.setBoolEnableAudio(extras.getBoolean("boolEnableAudio"));
        this.mGameInfo.setBoolPlayerTurn(extras.getBoolean("boolPlayerTurn"));
        this.mGameInfo.setComputerDifficulty(extras.getDouble("computerDifficulty"));
        this.mGameInfo.setnRowAmount(extras.getInt("rowAmount"));
        this.mGameInfo.setComputerSpeed(extras.getDouble("computerSpeed"));

        this.fadeInPlayerText.setDuration(5000);
        this.currentPlayer = (TextView) findViewById(R.id.currentPlayerTextView);

        correctPlayerName();
        System.out.printf("%s",this.currentPlayer);
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
        mEndTurnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGameInfo.isBoolPlayerTurn() && !mSelectedPieces.isEmpty()) {
                    ChangePlayerText();
                    updateGameBoard();
                    mSelectedPieces.clear();

                }
            }
        });


        mGameInfo.populateGameBoard();
        createGameBoard();

    }
    private void correctPlayerName()
    {
        //changes the text if it isn't the player
        if(!this.mGameInfo.isBoolPlayerTurn())
        {

            this.currentPlayer.setText(R.string.computerString);
        }
        //changes it back the the player
        else {

            this.currentPlayer.setText(R.string.PlayerString);
        }
    }
    private void ChangePlayerText()
    {
        //Does a fade animation
        this.currentPlayer.setAnimation(fadeInPlayerText);
        //switches the player turn
        this.mGameInfo.setBoolPlayerTurn(!this.mGameInfo.isBoolPlayerTurn());
        correctPlayerName();
    }

    private void updateGameBoard()
    {
        for(Integer id: mSelectedPieces)
        {
            View selectedButton = findViewById(id);
            selectedButton.setEnabled(false);
            selectedButton.setVisibility(View.GONE);
        }
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
                        if(mGameInfo.isBoolPlayerTurn())
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
}