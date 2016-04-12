package com.example.cam.nim;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Cam on 3/21/2016.
 */
public class GameInfo {

    private boolean boolEnableAudio;
    private boolean boolPlayerTurn;
    private boolean boolComputer;
    private long computerSpeed;
    private double mComputerDifficulty;
    private int nRowAmount;

    private ArrayList<ArrayList<Boolean>> mRemainingDots;


    GameInfo() {
        this.boolEnableAudio = true;
        this.boolPlayerTurn = true;
        this.nRowAmount = 5;
        this.mComputerDifficulty = 2.0;
        this.computerSpeed = 1;
        this.mRemainingDots = new ArrayList<>();

    }
    GameInfo(boolean enableAudio,boolean playerTurn, long computerSpeed,int nRowAmount, double computerDifficulty)
    {
        this.boolPlayerTurn = playerTurn;
        this.boolEnableAudio = enableAudio;
        this.computerSpeed = computerSpeed;
        this.nRowAmount = nRowAmount;
        this.mComputerDifficulty = computerDifficulty;
        this.mRemainingDots = new ArrayList<>();
    }

    public boolean isBoolEnableAudio() {
        return boolEnableAudio;
    }

    public void setBoolEnableAudio(boolean boolEnableAudio) {
        this.boolEnableAudio = boolEnableAudio;
    }

    public boolean isBoolPlayerTurn() {
        return boolPlayerTurn;
    }

    public void setBoolPlayerTurn(boolean boolPlayerTurn) {
        this.boolPlayerTurn = boolPlayerTurn;
    }

    public long getComputerSpeed() {
        return computerSpeed;
    }

    public void setComputerSpeed(long computerSpeed) {
        this.computerSpeed = computerSpeed;
    }

    public int getnRowAmount() {
        return nRowAmount;
    }

    public void setnRowAmount(int nRowAmount) {
        this.nRowAmount = nRowAmount;
    }

    public double getComputerDifficulty() {
        return mComputerDifficulty;
    }

    public void setComputerDifficulty(double computerDifficulty) {
        mComputerDifficulty = computerDifficulty;
    }

    public ArrayList<ArrayList<Boolean>> getRemainingDots() {
        return mRemainingDots;
    }

    public void setRemainingDots(ArrayList<ArrayList<Boolean>> remainingDots) {
        mRemainingDots = remainingDots;
    }

    public void populateGameBoard(ArrayList<ArrayList<Boolean>> remainingDots)
    {

    }

    /*Populates remainingDots arraylist using this.getnRowAmount*/
    public void populateGameBoard()
    {
        for(int i = 0; i < this.getnRowAmount();i++)
        {
            //Log.d("GameInfo", "Adding row");
            ArrayList<Boolean> tempList = new ArrayList<>();
            for(int j = 0; j <= i;j++)
            {
                tempList.add(Boolean.TRUE);
                //Log.d("GameInfo", "Adding bool");
            }
            this.getRemainingDots().add(tempList);
        }
    }

    public boolean isBoolComputer() {
        return boolComputer;
    }

    public void setBoolComputer(boolean boolComputer) {
        this.boolComputer = boolComputer;
    }
}
