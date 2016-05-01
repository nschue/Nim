package com.example.cam.nim;

/**
 * Created by Cam on 4/29/2016.
 * Used to display the Scoreboard in a neat list view
 */
public class ScoreItem {
    private String playerName;
    private String winAmount;
    private String totalGames;
    private String streak;

    ScoreItem(String name, String win, String total, String streak)
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

    public String getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(String winAmount) {
        this.winAmount = winAmount;
    }

    public String getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(String totalGames) {
        this.totalGames = totalGames;
    }

    public String getStreak() {
        return streak;
    }

    public void setStreak(String streak) {
        this.streak = streak;
    }
}
