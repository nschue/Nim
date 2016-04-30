package com.example.cam.nim;

/**
 * Created by Cam on 4/29/2016.
 */
public class ScoreItem {
    private String playerName;
    private int winAmount;
    private int totalGames;
    private int streak;

    ScoreItem(String name, int win, int total, int streak)
    {
        this.playerName = name;
        this.winAmount = win;
        this.totalGames = total;
        this.streak = streak;
    }


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(int winAmount) {
        this.winAmount = winAmount;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }
}
