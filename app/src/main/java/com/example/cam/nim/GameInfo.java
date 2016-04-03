package com.example.cam.nim;

import java.util.ArrayList;

/**
 * Created by Cam on 3/21/2016.
 */
public class GameInfo {

    private Boolean boolEnableAudio;
    private Boolean boolPlayerTurn;
    private Double computerSpeed;
    private Double mComputerDifficulty;
    private Integer nRowAmount;

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
    }

    public Boolean isBoolEnableAudio() {
        return boolEnableAudio;
    }

    public void setBoolEnableAudio(boolean boolEnableAudio) {
        this.boolEnableAudio = boolEnableAudio;
    }

    public Boolean isBoolPlayerTurn() {
        return boolPlayerTurn;
    }

    public void setBoolPlayerTurn(boolean boolPlayerTurn) {
        this.boolPlayerTurn = boolPlayerTurn;
    }

    public Double getComputerSpeed() {
        return computerSpeed;
    }

    public void setComputerSpeed(Double computerSpeed) {
        this.computerSpeed = computerSpeed;
    }

    public Integer getnRowAmount() {
        return nRowAmount;
    }

    public void setnRowAmount(Integer nRowAmount) {
        this.nRowAmount = nRowAmount;
    }

    public Double getComputerDifficulty() {
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
           ArrayList<Boolean> tempList = new ArrayList<Boolean>();
            for(int j = 0; j < i;j++)
            {
                tempList.add(true);
            }
            getRemainingDots().add(tempList);
        }
    }
}
