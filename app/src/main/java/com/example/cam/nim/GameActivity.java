package com.example.cam.nim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class GameActivity extends Activity
{
    private GameInfo mGameInfo;
    private Button mEndButton;
    private Button mEndTurnButton;
    private LinearLayout mGameBoardContainer;
    private ArrayList<Integer> mSelectedPieces;
    private TextView currentPlayer;
    /* private DrawerLayout mDrawerLayout;
     private ListView mDrawerList;

     private String[] choices;*/
    private AI mAI;
    private String[] choices;

    private final Animation fadeInPlayerText = new AlphaAnimation(0.0f,1.0f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mSelectedPieces = new ArrayList<>();

        /*choices = getResources().getStringArray(R.array.NavigatorBar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);*/

        // Set the adapter for the list view
        //mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item,choices));
        // Set the list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        /*Unbundles extras passed from OptionsActivity to populate local GameInfo object*/
        getGameInfo();

        this.fadeInPlayerText.setDuration(5000);
        this.currentPlayer = (TextView) findViewById(R.id.currentPlayerTextView);

        correctPlayerName();

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
                //player v computer
                //checks if the computer was the opponent
                // checks if it is the player's turn and if the selection list wasn't empty
                if (mGameInfo.isBoolComputer())
                {
                    if (mGameInfo.isBoolPlayerTurn() && !mSelectedPieces.isEmpty()) {
                        ChangePlayerText();
                        updateGameBoard();
                        mSelectedPieces.clear();
                        aiMove();
                    }
                }
                //Player v Player
                //Checks pieces were selected
                // then changes the current player displayed updates the peices
                // then clears the selection list
                else {
                    if (!mSelectedPieces.isEmpty()) {
                        ChangePlayerText();
                        updateGameBoard();
                        mSelectedPieces.clear();
                    }
                }
            }

        });


        mGameInfo.populateGameBoard();
        createGameBoard();

        if(!mGameInfo.isBoolPlayerTurn())
        {
            aiMove();
        }

    }

    //Gets information from the Option's bundle and stores into the gameactvity
    private void getGameInfo()
    {
        Bundle extras = getIntent().getBundleExtra("mBundle");
        this.mGameInfo = new GameInfo();
        this.mGameInfo.setBoolEnableAudio(extras.getBoolean("boolEnableAudio"));
        this.mGameInfo.setBoolPlayerTurn(extras.getBoolean("boolPlayerTurn"));
        this.mGameInfo.setBoolComputer(extras.getBoolean("boolComputer"));
        this.mGameInfo.setComputerDifficulty(extras.getDouble("computerDifficulty"));
        this.mGameInfo.setnRowAmount(extras.getInt("rowAmount"));
        this.mGameInfo.setComputerSpeed(extras.getDouble("computerSpeed"));

    }
    //Assigns the correct name to the current player text
    private void correctPlayerName() {
        if(!this.mGameInfo.isBoolPlayerTurn())
        {    //changes the text if it isn't the player
            if(this.mGameInfo.isBoolComputer())
            {
                this.currentPlayer.setText(R.string.computerString);
            }
            else
                this.currentPlayer.setText(R.string.friendString);
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
            int i = 0;
            int row = 0;

            //Matches button id to row and column of ArrayList<ArrayList<Boolean>>
            for(ArrayList<Boolean> al: mGameInfo.getRemainingDots())
            {
                int column = 0;
                for(Boolean bool: al)
                {
                    if(id == i)
                    {
                        mGameInfo.getRemainingDots().get(row).set(column, false);
                    }
                    i++;
                    column++;
                }
                row++;
            }
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
                final ImageButton tempButton = new ImageButton(GameActivity.this);
                tempButton.setBackgroundResource(R.drawable.game_piece);
                tempButton.setId(buttonID++);
                //tempButton.setBackground(null);
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
                        {
                            //If the game piece has already been selected, deselect it and reset image
                            if(mSelectedPieces.contains(v.getId()))
                            {
                                v.setBackgroundResource(R.drawable.game_piece);
                                mSelectedPieces.remove(new Integer(v.getId()));
                                return;
                            }
                            //Only executes code below if game piece was not already selected
                            else
                            {
                                checkRowSelection(v.getId());
                                v.setBackgroundResource(R.drawable.selected_game_piece);
                                mSelectedPieces.add(v.getId());

//                                if(mGameInfo.isBoolComputer())
//                                {
//                                }
//                                else
//                                {
//                                }
                            }
                        }
                    }
                });
            }
            mGameBoardContainer.addView(temp);
        }
    }

    private ArrayList<Integer> convertToGrid(int index)
    {
        ArrayList<Integer> result = new ArrayList();
        int precedingDots = 0;
        int dotsInRow = 1;
        for(int i = 0; i <= index; i++)
        {
            if(i == dotsInRow + precedingDots)
            {
                precedingDots = precedingDots + dotsInRow;
                dotsInRow = dotsInRow + 1;
            }
        }
        result.add(dotsInRow - 1);
        result.add(index - (precedingDots - 1));

        return result;
    }

    private Boolean isInSameRow(int index)
    {
        boolean result = true;
        if(!(mSelectedPieces.size() == 0))
        {
            for(int i = 0; i < mSelectedPieces.size(); i++)
            {
                if(Objects.equals(convertToGrid(mSelectedPieces.get(i)).get(0), convertToGrid(index).get(0)))
                {
                    result = false;
                }
            }
        }
        return result;
    }

    private void checkRowSelection(int index)
    {
        if(!isInSameRow(index))
        {
            for(int i = 0; i < mSelectedPieces.size(); i++)
            {
                //ArrayList<Integer> selectedPiece = convertToGrid(mSelectedPieces.get(i))
                findViewById(i).setBackgroundResource(R.drawable.game_piece);
                mSelectedPieces.remove(new Integer(index));
            }
        }
    }

    private void aiMove()
    {
        // The following returns a linear ArrayList consisting of the AI's choices
        //mAI.calculateNextMove(mGameInfo.getRemainingDots());

        //AI animate
        updateGameBoard();
        mSelectedPieces.clear();
        mGameInfo.setBoolPlayerTurn(true);
    }


}