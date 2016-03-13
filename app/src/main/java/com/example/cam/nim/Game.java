package com.example.cam.nim;

import android.app.Activity;
import android.os.Bundle;



public class Game extends Activity {

    private GameStatus gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //gameView = new GameStatus(this);
        //setContentView(gameView);

    }
    @Override
    protected  void onPause(){
        super.onPause();
        gameView.pause();
    }


}
