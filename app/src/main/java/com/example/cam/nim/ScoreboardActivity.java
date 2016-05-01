package com.example.cam.nim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import java.util.ArrayList;


public class ScoreboardActivity extends Activity {

    DatabaseHelper dbHandlerEasy, dbHandlerMed, dbHandlerHard, dbHandlerPlayer;
    private ArrayList<ScoreItem> databaseInfo = new ArrayList<>();
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
        scoreBoardType =  intent.getBundleExtra("typeBundle").getInt("gameType");

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

        ScoreboardSelection(scoreBoardType);


        host.setup();
        //Tab Name
        TabHost.TabSpec spec = host.newTabSpec("Name");
        spec.setIndicator("Name");
        spec.setContent(R.id.listbyName);
        host.addTab(spec);

        //Tab Win
        spec = host.newTabSpec("Wins");
        spec.setIndicator("Wins");
        spec.setContent(R.id.listbyWins);
        host.addTab(spec);

        //Tab Total
        spec = host.newTabSpec("Total");
        spec.setIndicator("Total");
        spec.setContent(R.id.listbyTotal);
        host.addTab(spec);

        //Tab Streak
        spec = host.newTabSpec("Streak");
        spec.setIndicator("Streak");
        spec.setContent(R.id.listByStreak);
        host.addTab(spec);

        printData(currentBoard, "NAME ASC");//sorting by alphabetical
        final customListAdapter adapterName = new customListAdapter(ScoreboardActivity.this,this.databaseInfo);
        nameList.setAdapter(adapterName);

        printData(currentBoard, "TOTAL DESC");//sort by wins
        final customListAdapter adapterTotal = new customListAdapter(ScoreboardActivity.this,this.databaseInfo);
        totalList.setAdapter(adapterTotal);

        printData(currentBoard, "WIN DESC");//sort by totals
        final customListAdapter adapterWin = new customListAdapter(ScoreboardActivity.this,this.databaseInfo);
        winList.setAdapter(adapterWin);

        printData(currentBoard, "STREAK DESC");//sort by streak
        final customListAdapter adapterStreak = new customListAdapter(ScoreboardActivity.this,this.databaseInfo);
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

    public void setCurrentBoard(String string)
    {
        this.currentBoard = string;
    }
}