package com.example.cam.nim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class CreditsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_credits);

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent mainMenuIntent = new Intent(this,MainMenuActivity.class);
        startActivity(mainMenuIntent);
        finish();
    }
}
