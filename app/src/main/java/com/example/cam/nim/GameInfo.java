package com.example.cam.nim;

import java.util.ArrayList;

/**
 * Created by Cam on 3/21/2016.
 */
public class GameInfo {

    private boolean boolEnableAudio;
    private boolean boolPlayerTurn;
    private double computerSpeed;
    private double mComputerDifficulty;
    private int nRowAmount;

    private ArrayList<ArrayList<Boolean>> mRemainingDots;


    GameInfo() {
        this.boolEnableAudio = true;
        this.boolPlayerTurn = true;
        this.nRowAmount = 5;
    }
    GameInfo(boolean enableAudio,boolean playerTurn, double computerSpeed,int nRowAmount)
    {
        this.boolPlayerTurn = playerTurn;
        this.boolEnableAudio = enableAudio;
        this.computerSpeed = computerSpeed;
        this.nRowAmount = nRowAmount;
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

    public double getComputerSpeed() {
        return computerSpeed;
    }

    public void setComputerSpeed(double computerSpeed) {
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
}
