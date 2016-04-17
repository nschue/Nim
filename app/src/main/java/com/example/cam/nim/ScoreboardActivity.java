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
        dbHandlerEasy = new DatabaseHelper(this,"easy2.db","easy_table");

        // In method insertData(name, score,winstreak), will be inserting to different levels
        // Fake data below for testing. Replace with real data later on

        dbHandlerEasy.insertData("Vincent", "99.6", "1");
        dbHandlerEasy.insertData("Ken", "99.7", "3");
        dbHandlerEasy.insertData("Nic", "99.8", "5");
        dbHandlerEasy.insertData("Cam", "99.9", "2");

        medtxtText = (TextView) findViewById(R.id.mediumtextView);
        dbHandlerMed = new DatabaseHelper(this,"medium2.db", "medium_table");

        dbHandlerMed.insertData("Vincent", "99.6", "1");
        dbHandlerMed.insertData("Ken", "99.7", "3");
        dbHandlerMed.insertData("Nic", "99.8", "5");
        dbHandlerMed.insertData("Cam", "99.9", "2");

        hardtxtText = (TextView) findViewById(R.id.hardtextView);
        dbHandlerHard = new DatabaseHelper(this,"hard2.db", "hard_table");

        dbHandlerHard.insertData("Vincent", "99.6", "1");
        dbHandlerHard.insertData("Ken", "99.7", "3");
        dbHandlerHard.insertData("Nic", "99.8", "5");
        dbHandlerHard.insertData("Cam", "99.9", "2");

        playertxtText = (TextView) findViewById(R.id.playertextView);
        dbHandlerPlayer = new DatabaseHelper(this,"player2.db", "player_table");

        dbHandlerPlayer.insertData("Vincent", "99.6", "1");
        dbHandlerPlayer.insertData("Ken", "99.7", "3");
        dbHandlerPlayer.insertData("Nic", "99.8", "5");
        dbHandlerPlayer.insertData("Cam", "99.9", "2");


        //deleting player, for testing purpose
        /*
        dbHandlerEasy.deletePlayer("Vincent");
        dbHandlerEasy.deletePlayer("Ken");
        dbHandlerEasy.deletePlayer("Cam");
        dbHandlerEasy.deletePlayer("Nic");

        dbHandlerMed.deletePlayer("Vincent");
        dbHandlerMed.deletePlayer("Ken");
        dbHandlerMed.deletePlayer("Cam");
        dbHandlerMed.deletePlayer("Nic");

        dbHandlerHard.deletePlayer("Vincent");
        dbHandlerHard.deletePlayer("Ken");
        dbHandlerHard.deletePlayer("Cam");
        dbHandlerHard.deletePlayer("Nic");

        dbHandlerPlayer.deletePlayer("Vincent");
        dbHandlerPlayer.deletePlayer("Ken");
        dbHandlerPlayer.deletePlayer("Cam");
        dbHandlerPlayer.deletePlayer("Nic");
        */


        //print out the data
        try {
            printDatabase();
        } catch (Exception e) {
            //Log.i("exxxx", e.toString());
        }

    }
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.namebuttonE):
            case (R.id.namebuttonM):
            case (R.id.namebuttonH):
            case (R.id.namebuttonP):
            {
                printDataSortName();
                break;
            }
            case (R.id.winbuttonE):
            case (R.id.winbuttonM):
            case (R.id.winbuttonH):
            case (R.id.winbuttonP):
            {
                printDatabase();
                break;
            }
            case (R.id.streakbuttonE):
            case (R.id.streakbuttonM):
            case (R.id.streakbuttonH):
            case (R.id.streakbuttonP):
            {
                printDataSortStreak();
                break;
            }

        }

    }

    public void printDatabase() {
        String dbStringE = dbHandlerEasy.databaseToString();
        easytxtText.setText(dbStringE);
        String dbStringM = dbHandlerMed.databaseToString();
        medtxtText.setText(dbStringM);
        String dbStringH = dbHandlerHard.databaseToString();
        hardtxtText.setText(dbStringH);
        String dbStringP = dbHandlerPlayer.databaseToString();
        playertxtText.setText(dbStringP);

    }
    public void printDataSortName() {
        String dbStringE = dbHandlerEasy.databaseSortNameToString();
        easytxtText.setText(dbStringE);
        String dbStringM = dbHandlerMed.databaseSortNameToString();
        medtxtText.setText(dbStringM);
        String dbStringH = dbHandlerHard.databaseSortNameToString();
        hardtxtText.setText(dbStringH);
        String dbStringP = dbHandlerPlayer.databaseSortNameToString();
        playertxtText.setText(dbStringP);
    }
    public void printDataSortStreak() {
        String dbStringE = dbHandlerEasy.databaseSortStreakToString();
        easytxtText.setText(dbStringE);
        String dbStringM = dbHandlerMed.databaseSortStreakToString();
        medtxtText.setText(dbStringM);
        String dbStringH = dbHandlerHard.databaseSortStreakToString();
        hardtxtText.setText(dbStringH);
        String dbStringP = dbHandlerPlayer.databaseSortStreakToString();
        playertxtText.setText(dbStringP);

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

