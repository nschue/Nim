package com.example.cam.nim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

public class ScoreboardActivity extends AppCompatActivity {
    TextView easytxtText,medtxtText, hardtxtText;
    DatabaseHelperEasy dbHandlerEasy;
    DatabaseHelperMedium dbHandlerMed;
    DatabaseHelperHard dbHandlerHard;
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
        tabSpec.setIndicator("Medium");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("hard");
        tabSpec.setContent(R.id.tabHard);
        tabSpec.setIndicator("Hard");
        tabHost.addTab(tabSpec);

        easytxtText = (TextView)findViewById(R.id.easytextView);
        dbHandlerEasy = new DatabaseHelperEasy(this);

        // In method insertData(name, score), will be inserting to different levels
        // Fake data below for testing. Replace with real data later on
        dbHandlerEasy.insertData("Vincent", "99.6");
        dbHandlerEasy.insertData("Ken", "99.7");
        dbHandlerEasy.insertData("Nic", "99.8");
        dbHandlerEasy.insertData("Cam", "99.9");


        medtxtText = (TextView)findViewById(R.id.mediumtextView);
        dbHandlerMed = new DatabaseHelperMedium(this);


        hardtxtText = (TextView)findViewById(R.id.hardtextView);
        dbHandlerHard = new DatabaseHelperHard(this);

        //deleting player, for testing purpose
        /*
        dbHandlerEasy.deletePlayer("Vincent");
        dbHandlerEasy.deletePlayer("Ken");
        dbHandlerEasy.deletePlayer("Cam");
        dbHandlerEasy.deletePlayer("Nic");
        */

        //print out the data
        try {
            printDatabase();
        }catch (Exception e){
            //Log.i("exxxx", e.toString());
        }

    }
    public void printDatabase() {
        String dbStringE = dbHandlerEasy.databaseToString();
        easytxtText.setText(dbStringE);
        String dbStringM = dbHandlerMed.databaseToString();
        medtxtText.setText(dbStringM);
        String dbStringH = dbHandlerHard.databaseToString();
        hardtxtText.setText(dbStringH);

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

