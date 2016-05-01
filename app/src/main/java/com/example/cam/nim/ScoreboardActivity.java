package com.example.cam.nim;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import java.util.ArrayList;


public class ScoreboardActivity extends Activity {

    DatabaseHelper dbHandlerEasy, dbHandlerMed, dbHandlerHard, dbHandlerPlayer;
    private ArrayList<ScoreItem> databaseInfo = new ArrayList<>();
    private Dialog selectScoreBoard;
    private TextView ScoreboardName;
    private String currentBoard = new String();
    private ListView nameList,streakList,totalList,winList;
    private boolean fromGame;
    private int scoreBoardType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        Intent intent = getIntent();
        fromGame = intent.getBundleExtra("boolBundle").getBoolean("fromGame");

        if(!fromGame)
        {   showBoardSelection();
        Log.d("CurrentBoard info: ",currentBoard);}
        else
        {
            scoreBoardType =  intent.getBundleExtra("boolBundle").getInt("gameType");
            ScoreboardSelection(scoreBoardType);
        }

        this.nameList = (ListView) findViewById(R.id.listbyName);
        this.streakList = (ListView) findViewById(R.id.listByStreak);
        this.totalList = (ListView) findViewById(R.id.listbyTotal);
        this.winList = (ListView) findViewById(R.id.listbyWins);

        ScoreboardName = (TextView) findViewById(R.id.scoreboardName);

        this.dbHandlerEasy = new DatabaseHelper(this,"easy4.db","easy_table");
        this.dbHandlerMed = new DatabaseHelper(this,"medium4.db", "medium_table");
        this.dbHandlerHard = new DatabaseHelper(this,"hard4.db", "hard_table");
        this.dbHandlerPlayer = new DatabaseHelper(this,"player4.db", "player_table");

        final TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Name");
        spec.setIndicator("Name");
        spec.setContent(R.id.listbyName);
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Wins");
        spec.setIndicator("Wins");
        spec.setContent(R.id.listbyWins);
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Total");
        spec.setIndicator("Total");
        spec.setContent(R.id.listbyTotal);
        host.addTab(spec);

        //Tab 4
        spec = host.newTabSpec("Streak");
        spec.setIndicator("Streak");
        spec.setContent(R.id.listByStreak);
        host.addTab(spec);

        printData(currentBoard, "NAME ASC");
        customListAdapter adapterName = new customListAdapter(ScoreboardActivity.this,this.databaseInfo);
        nameList.setAdapter(adapterName);

        printData(currentBoard, "WIN DESC");
        customListAdapter adapterTotal = new customListAdapter(ScoreboardActivity.this,this.databaseInfo);
        totalList.setAdapter(adapterTotal);

        printData(currentBoard, "TOTAL DESC");
        customListAdapter adapterWin = new customListAdapter(ScoreboardActivity.this,this.databaseInfo);
        winList.setAdapter(adapterWin);

        printData(currentBoard, "STREAK DESC");
        customListAdapter adapterStreak = new customListAdapter(ScoreboardActivity.this,this.databaseInfo);
        streakList.setAdapter(adapterStreak);

    }
    public void ScoreboardSelection(int scoreBoardType)
    {
        ScoreboardName = (TextView) findViewById(R.id.scoreboardName);
        switch(scoreBoardType)
        {
            case 0:
                setCurrentBoard("easy");
                ScoreboardName.setText("Easy Scoreboard");
                break;
            case 1:
                setCurrentBoard("med");
                ScoreboardName.setText("Medium Scoreboard");
                break;
            case 2:
                setCurrentBoard("hard");
                ScoreboardName.setText("Hard ScoreBoard");
                break;
            case -1:
                setCurrentBoard("pvp");
                ScoreboardName.setText("Friend Scoreboard");
                break;
        }
    }

    public void printData(String level, String sortBy){
        switch(level) {
            case("easy"): {
                databaseInfo = dbHandlerEasy.databaseToString(sortBy);
                break;
            }
            case("med"): {
                databaseInfo = dbHandlerMed.databaseToString(sortBy);
                break;
            }
            case("hard"): {
               databaseInfo = dbHandlerHard.databaseToString(sortBy);
                break;
            }
            case("pvp"): {
                databaseInfo = dbHandlerPlayer.databaseToString(sortBy);
                break;
            }
        }
    }


    //Takes the player back to the main menu if the player clicks the back button
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent mainMenuIntent = new Intent(this,MainMenuActivity.class);
        startActivity(mainMenuIntent);
        finish();
    }
    public void showBoardSelection()
    {
        selectScoreBoard = new Dialog(ScoreboardActivity.this);
        selectScoreBoard.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectScoreBoard.setContentView(R.layout.dailog_scoreboard);
        selectScoreBoard.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final Button easyButton = (Button) selectScoreBoard.findViewById(R.id.easyButton);
        final Button medButton = (Button) selectScoreBoard.findViewById(R.id.medButton);
        final Button hardButton = (Button) selectScoreBoard.findViewById(R.id.hardButton);
        final Button friendButton = (Button) selectScoreBoard.findViewById(R.id.friendButton);

        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBoard = "easy";
                ScoreboardName.setText("Easy Scoreboard");
                selectScoreBoard.dismiss();
            }
        });
        medButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBoard = "med";
                ScoreboardName.setText("Medium Scoreboard");
                selectScoreBoard.dismiss();
            }
        });
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBoard = "hard";
                ScoreboardName.setText("Hard Scoreboard");
                selectScoreBoard.dismiss();
            }
        });
        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBoard = "pvp";
                ScoreboardName.setText("Friend Scoreboard");
                selectScoreBoard.dismiss();
            }
        });

        selectScoreBoard.show();
        selectScoreBoard.setCancelable(false);
    }
    public void setCurrentBoard(String string)
    {
        this.currentBoard = string;
    }
}