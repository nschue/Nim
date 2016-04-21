package com.example.cam.nim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class ScoreboardActivity extends AppCompatActivity {
    TextView easytxtText,medtxtText, hardtxtText, playertxtText;

    DatabaseHelper dbHandlerEasy, dbHandlerMed, dbHandlerHard, dbHandlerPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        dbHandlerEasy = new DatabaseHelper(this,"easy4.db","easy_table");
        dbHandlerMed = new DatabaseHelper(this,"medium4.db", "medium_table");
        dbHandlerHard = new DatabaseHelper(this,"hard4.db", "hard_table");
        dbHandlerPlayer = new DatabaseHelper(this,"player4.db", "player_table");

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("easy");
        tabSpec.setContent(R.id.tabEasy);
        tabSpec.setIndicator("Easy");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("medium");
        tabSpec.setContent(R.id.tabMedium);
        tabSpec.setIndicator("Med");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("hard");
        tabSpec.setContent(R.id.tabHard);
        tabSpec.setIndicator("Hard");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("player");
        tabSpec.setContent(R.id.tabPlayer);
        tabSpec.setIndicator("PvP");
        tabHost.addTab(tabSpec);

        easytxtText = (TextView) findViewById(R.id.easytextView);
        medtxtText = (TextView) findViewById(R.id.mediumtextView);
        hardtxtText = (TextView) findViewById(R.id.hardtextView);
        playertxtText = (TextView) findViewById(R.id.playertextView);

        //dbHandlerMed.deletePlayer("vicky");
        //print out the data
        try {
            printData("easy","WIN DESC");
            printData("med","WIN DESC");
            printData("hard","WIN DESC");
            printData("pvp","WIN DESC");
        } catch (Exception e) {
            //Log.i("exxxx", e.toString());
        }

    }
    public void onClick(View view) {
        switch (view.getId()) {
            //onClick for Name Button
            case (R.id.namebuttonE):{
                printData("easy","NAME ASC");
                break;
            }
            case (R.id.namebuttonM):{
                printData("med","NAME ASC");
                break;
            }
            case (R.id.namebuttonH):{
                printData("hard","NAME ASC");
                break;
            }
            case (R.id.namebuttonP):
            {
                printData("pvp","NAME ASC");
                break;
            }

            //onClick for Win Button
            case (R.id.winbuttonE):{
                printData("easy","WIN DESC");
                break;
            }
            case (R.id.winbuttonM):{
                printData("med","WIN DESC");
                break;
            }
            case (R.id.winbuttonH):{
                printData("hard","WIN DESC");
                break;
            }
            case (R.id.winbuttonP): {
                printData("pvp","WIN DESC");
                break;
            }

            //onClick for Streak Button
            case (R.id.streakbuttonE):{
                printData("easy","STREAK DESC");
                break;
            }
            case (R.id.streakbuttonM):
            {
                printData("med","STREAK DESC");
                break;
            }
            case (R.id.streakbuttonH):
            {
                printData("hard","STREAK DESC");
                break;
            }
            case (R.id.streakbuttonP):
            {
                printData("pvp","STREAK DESC");
                break;
            }

            //onClick for Loses Button
            case (R.id.losesbuttonE):{
                printData("easy","LOSES DESC");
                break;
            }
            case (R.id.losesbuttonM):{
                printData("med","LOSES DESC");
                break;
            }
            case (R.id.losesbuttonH):{
                printData("hard","LOSES DESC");
                break;
            }
            case (R.id.losesbuttonP):{
                printData("pvp","LOSES DESC");
                break;
            }

            //onClick for Win% Button
            case (R.id.winPercentbuttonE):{
                printData("easy","WIN_PERCENT DESC");
                break;
            }
            case (R.id.winPercentbuttonM):{
                printData("med","WIN_PERCENT DESC");
                break;
            }
            case (R.id.winPercentbuttonH):{
                printData("hard","WIN_PERCENT DESC");
                break;
            }
            case (R.id.winPercentbuttonP):{
                printData("pvp","WIN_PERCENT DESC");
                break;
            }

        }

    }

    public void printData(String level, String sortBy){
        switch(level) {
            case("easy"): {
                String dbStringE = dbHandlerEasy.databaseToString(sortBy);
                easytxtText.setText(dbStringE);
                break;
            }
            case("med"): {
                String dbStringM = dbHandlerMed.databaseToString(sortBy);
                medtxtText.setText(dbStringM);
                break;
            }
            case("hard"): {
                String dbStringH = dbHandlerHard.databaseToString(sortBy);
                hardtxtText.setText(dbStringH);
                break;
            }
            case("pvp"): {
                String dbStringP = dbHandlerPlayer.databaseToString(sortBy);
                playertxtText.setText(dbStringP);
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
}

