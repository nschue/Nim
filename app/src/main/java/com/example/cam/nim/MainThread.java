package com.example.cam.nim;

/**
 * Created by Cam on 3/11/2016.
 */
public class MainThread extends Thread {
    private boolean endGame;

    public void setEndGame(boolean gameState) {
        this.endGame = gameState;
    }
    @Override
    public void run(){
        while(!this.endGame)
        {
            
        }
    }
}
