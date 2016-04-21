package com.example.cam.nim;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
//import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
//import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
//import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class GameActivity extends Activity
{
    private GameInfo mGameInfo;
    private Button mEndButton;
    private Button mEndTurnButton,howToPlay;
    private LinearLayout mGameBoardContainer;
    private ArrayList<Integer> mSelectedPieces;
    private TextView currentPlayer;
    private Dialog winDialog,howToPlayDialog;

    DatabaseHelper dbHandlerEasy, dbHandlerMed, dbHandlerHard, dbHandlerPlayer;


   // private DrawerLayout mDrawerLayout;
   // private ListView mDrawerList;
    private AI mAI;
   // private String[] choices;

    private final Animation fadeInPlayerText = new AlphaAnimation(0.0f,1.0f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mSelectedPieces = new ArrayList<>();
        dbHandlerEasy = new DatabaseHelper(this,"easy4.db","easy_table");
        dbHandlerMed = new DatabaseHelper(this,"medium4.db", "medium_table");
        dbHandlerHard = new DatabaseHelper(this,"hard4.db", "hard_table");
        dbHandlerPlayer = new DatabaseHelper(this,"player4.db", "player_table");

        /*choices = getResources().getStringArray(R.array.NavigatorBar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item,choices));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());*/

        /*Unbundles extras passed from OptionsActivity to populate local GameInfo object*/
        getGameInfo();
        mAI = new AI(mGameInfo.getComputerDifficulty());

        this.fadeInPlayerText.setDuration(1000);
        this.currentPlayer = (TextView) findViewById(R.id.currentPlayerTextView);

        correctPlayerName();

        mGameBoardContainer = (LinearLayout) findViewById(R.id.gameboard_container);
        mGameBoardContainer.removeAllViews();

        howToPlay = (Button) findViewById(R.id.howToPlayButton);
        howToPlay.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                howToPlay();
            }
        });

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
                if (mGameInfo.isBoolComputer()) {
                    if (mGameInfo.isBoolPlayerTurn() && !mSelectedPieces.isEmpty()) {
                        updateGameBoard();
                        ChangePlayerText();
                        mSelectedPieces.clear();
                        mSelectedPieces = new ArrayList<>();
                       if(mGameInfo.getTotalPieces() > 0)
                         aiMove();
                    }
                }
                //Player v Player
                //Checks pieces were selected
                // then changes the current player displayed updates the pieces
                // then clears the selection list
                else {
                    if (!mSelectedPieces.isEmpty()) {
                        updateGameBoard();
                        ChangePlayerText();
                        mSelectedPieces.clear();
                        mSelectedPieces = new ArrayList<>();
                    }
                }
            }

        });


        this.mGameInfo.populateGameBoard();
        createGameBoard();
        
        if(!mGameInfo.isBoolPlayerTurn()&& mGameInfo.isBoolComputer())
        {
            if (mGameInfo.getTotalPieces() > 0) {
                aiMove();
            }
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
        this.mGameInfo.setTotalPieces(this.mGameInfo.findTotal(this.mGameInfo.getnRowAmount()));
        this.mGameInfo.setComputerSpeed(extras.getLong("computerSpeed"));
        this.mGameInfo.setUpdatedPlayer1(extras.getString("newPlayerName"));
        this.mGameInfo.setUpdatePlayer2(extras.getString("newOtherPlayerName"));

    }
    //Tells the player who won
    // gives them the option to
    //exit, view scoreboard, or play with the same settings
    public void WinDialog(){
        winDialog = new Dialog(GameActivity.this);
        winDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        winDialog.setContentView(R.layout.dialog_win);
        winDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView winnerName = (TextView) winDialog.findViewById(R.id.winnerName);
        final Button scoreboard = (Button) winDialog.findViewById(R.id.viewScoreboardButton);
        final Button playAgain = (Button) winDialog.findViewById(R.id.playAgainButton);
        final Button exitButton = (Button) winDialog.findViewById(R.id.exitButton);

        //check winner and update score to database
        getGameInfo();
        int level=mGameInfo.getdifficultyCoversion();
        String winner = winnerName.toString();

        switch(level){
            case 0:{
                if(winner.equals(mGameInfo.getUpdatedPlayer1()))
                {
                    dbHandlerEasy.updateData(mGameInfo.getUpdatedPlayer1(),"1","0","1"); //if win

                }
                else
                {
                    dbHandlerEasy.updateData(mGameInfo.getUpdatedPlayer1(),"0","1","-1");
                }
                break;
            }

        }


        scoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreIntent = new Intent(GameActivity.this, ScoreboardActivity.class);
                startActivity(scoreIntent);
                finish();
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenuIntent = new Intent(GameActivity.this, MainMenuActivity.class);
                startActivity(mainMenuIntent);
                finish();
            }
        });
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playAgainIntent = new Intent(GameActivity.this, GameActivity.class);
                Bundle mBundle = getIntent().getBundleExtra("mBundle");
                playAgainIntent.putExtra("mBundle", mBundle);
                startActivity(playAgainIntent);
                finish();
            }
        });
        winnerName.setText(currentPlayer.getText().toString() + " Wins!");
        winDialog.show();
    }

    public void howToPlay() {

        howToPlayDialog = new Dialog(GameActivity.this);
        howToPlayDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        howToPlayDialog.setContentView(R.layout.dialog_howtoplay);
        howToPlayDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button okayButton = (Button) howToPlayDialog.findViewById(R.id.okay);
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howToPlayDialog.dismiss();
            }
        });
        howToPlayDialog.show();
    }

    //Assigns the correct name to the current player text
    private void correctPlayerName() {
        //changes the text if it isn't the player
        if(!this.mGameInfo.isBoolPlayerTurn())
        {
            if(this.mGameInfo.isBoolComputer())
                this.currentPlayer.setText(R.string.computerString);
            else if(mGameInfo.getUpdatePlayer2()!= null && !mGameInfo.isBoolComputer() )
                this.currentPlayer.setText(mGameInfo.getUpdatePlayer2());
            else
                this.currentPlayer.setText(R.string.friendString);
        }
        //changes it back the the player
        else {
            if(mGameInfo.getUpdatedPlayer1() != null)
                this.currentPlayer.setText(mGameInfo.getUpdatedPlayer1());
            else
                this.currentPlayer.setText(R.string.PlayerString);
        }
    }

    private void ChangePlayerText() {
        if(mGameInfo.getTotalPieces() > 0) { //checks if there are pieces to remove
            this.currentPlayer.setAnimation(fadeInPlayerText);//Does a fade animation
            //switches the player turn
            this.mGameInfo.setBoolPlayerTurn(!this.mGameInfo.isBoolPlayerTurn());
            correctPlayerName();
        }
    }


    private void updateGameBoard()
    {
        if(mGameInfo.getTotalPieces() > 0) {
            for (Integer id : mSelectedPieces) {
                int i = 0;
                int row = 0;

                //Matches button id to row and column of ArrayList<ArrayList<Boolean>>
                for (ArrayList<Boolean> al : mGameInfo.getRemainingDots()) {
                    int column = 0;
                    for (Boolean bool : al) {
                        if (id == i) {
                            mGameInfo.getRemainingDots().get(row).set(column, false);
                        }
                        i++;
                        column++;
                    }
                    row++;
                }
                View selectedButton = findViewById(id);
                selectedButton.setEnabled(false);
                selectedButton.setBackgroundResource(R.drawable.blank_game_piece);
                //selectedButton.setVisibility(View.GONE);
            }
            mGameInfo.setTotalPieces(mGameInfo.getTotalPieces() - mSelectedPieces.size());
        }
        if (mGameInfo.getTotalPieces() == 0)
        {
            currentPlayer.setVisibility(View.GONE);
            WinDialog();

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
                tempButton.setOnClickListener(new ImageButton.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                    if(mGameInfo.isBoolPlayerTurn()||!mGameInfo.isBoolComputer())
                    {
                        //If the game piece has already been selected, deselect it and reset image
                        if(mSelectedPieces.contains(v.getId()))
                        {
                            v.setBackgroundResource(R.drawable.game_piece);
                            mSelectedPieces.remove(new Integer(v.getId()));
                        }
                        //Only executes code below if game piece was not already selected
                        else
                        {
                            //checkRowSelection(v.getId());
                            if(!mSelectedPieces.isEmpty())
                            {
                                checkRowSelect(v.getId());
                            }
                            if(mGameInfo.isBoolPlayerTurn() && mGameInfo.isBoolComputer())
                                mSelectedPieces.add(v.getId());
                            else if(!mGameInfo.isBoolComputer())
                                mSelectedPieces.add(v.getId());
                            v.setBackgroundResource(R.drawable.selected_game_piece);
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
        ArrayList<Integer> result = new ArrayList<>();
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
        ArrayList<Integer> tempAIList = new ArrayList<>(mAI.calculateNextMove(this.mGameInfo.getRemainingDots()));
        for(Integer selectedButton:tempAIList)
        {
            mSelectedPieces.add(selectedButton);
        }
        final Handler handler = new Handler();

        for(Integer id:mSelectedPieces) {
            final View tempButton = findViewById(id);
;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after .5s = 500ms
                    tempButton.setBackgroundResource(R.drawable.selected_game_piece);
                }
            }, 500);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after .5s = 500ms
                updateGameBoard();
                mSelectedPieces.clear();
                mSelectedPieces = new ArrayList<>();
                ChangePlayerText();
            }
        }, 5*mGameInfo.getComputerSpeed()+500);
    }

    private void checkRowSelect(int currentID)
    {
        int row;
        int prevRow = -1;
        int currentRow = -1;
        int prevID = mSelectedPieces.get(0);
        int i = 0;
        for(row = 0; row <= mGameInfo.getnRowAmount(); row++)
        {
            for(int column = 0; column <= row; column++)
            {
                if(i==prevID)
                {
                    prevRow = row;
                }
                if(i == currentID)
                {
                    currentRow = row;
                }
                i++;
            }
        }
        if(currentRow!=prevRow)
        {
            for(Integer id: mSelectedPieces)
            {
                View tempButton = findViewById(id);
                tempButton.setBackgroundResource(R.drawable.game_piece);
            }
            mSelectedPieces.clear();
            mSelectedPieces = new ArrayList<>();
        }
    }
}

