package com.example.cam.nim;

import java.util.ArrayList;

/**
 * Created by Cam on 3/21/2016.
 */
public class GameInfo {

    private boolean boolEnableAudio;
    private boolean boolPlayerTurn;
    private boolean boolComputer;
    private double computerSpeed;
    private double mComputerDifficulty;
    private double pieceCount;
    private int nRowAmount;
    private ArrayList<ArrayList<Boolean>> mRemainingDots;


    GameInfo() {
        this.boolEnableAudio = true;
        this.boolPlayerTurn = true;
        this.nRowAmount = 5;
        this.mComputerDifficulty = 2.0;
        this.computerSpeed = 1.0;
    }
    GameInfo(boolean enableAudio,boolean playerTurn, double computerSpeed,int nRowAmount, double computerDifficulty)
    {
        this.boolPlayerTurn = playerTurn;
        this.boolEnableAudio = enableAudio;
        this.computerSpeed = computerSpeed;
        this.nRowAmount = nRowAmount;
        this.mComputerDifficulty = computerDifficulty;
        this.pieceCount = findPieceCount();
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

    public void populateGameBoard(ArrayList<ArrayList<Boolean>> remainingDots)
    {

    }

    /*Populates remainingDots arraylist using this.getnRowAmount*/
    public void populateGameBoard()
    {
        setRemainingDots(new ArrayList<ArrayList<Boolean>>());
        for(int i = 0; i < this.getnRowAmount();i++)
        {
           ArrayList<Boolean> tempList = new ArrayList<Boolean>();
            for(int j = 0; j < i;j++)
            {
                tempList.add(true);
            }
            getRemainingDots().add(tempList);
        }
    }

    public boolean isBoolComputer() {
        return boolComputer;
    }
public double findPieceCount()
{
   return .5 * ( this.nRowAmount * ( this.nRowAmount + 1) );
}
    public void setBoolComputer(boolean boolComputer) {
        this.boolComputer = boolComputer;
    }

    public double getPieceCount() {
        return pieceCount;
    }

    public void setPieceCount(double pieceCount) {
        this.pieceCount = pieceCount;
    }
}
