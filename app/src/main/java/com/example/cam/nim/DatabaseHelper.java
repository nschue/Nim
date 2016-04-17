package com.example.cam.nim;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper{

    String PLAYER_DATABASE = null;
    String TABLE_NAME = null;
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_SCORE = "SCORE";
    public static final String COL_STREAK = "STREAK";

    public DatabaseHelper(Context context, String dataName, String tableName) {
        super(context, dataName, null, 1);
        PLAYER_DATABASE = dataName;
        TABLE_NAME = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SCORE DOUBLE,STREAK INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public double findWinStreak(int winCount,int gamePlayed)
    {
        return  gamePlayed/winCount;
    }
    public boolean insertData(String name,String score,String streak) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_SCORE,score);
        contentValues.put(COL_STREAK,streak);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_SCORE, null);
        return true;

    }
    public void deletePlayer(String playerName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COL_NAME + "=\"" + playerName + "\";");
    }
    //return string sorted base on winning percentage
    public String databaseToString(){
        int count = 1;
        SQLiteDatabase db =  getWritableDatabase();
        String sortOrder = COL_SCORE + " DESC";

        Cursor res = db.query(TABLE_NAME, null, null, null, null, null, sortOrder);
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext())
        {
            //buffer.append("ID:" +res.getString(0)+"\n");
            buffer.append(count+".\t\tWin:"+res.getString(2)+"%\t\t");
            buffer.append("Streak:"+res.getString(3)+"\t\t\t");
            buffer.append(res.getString(1)+"\n\n");
            count++;
        }
        db.close();
        return buffer.toString();
    }
    //return string that is alphabetize by player name
    public String databaseSortNameToString(){
        int count = 1;
        SQLiteDatabase db =  getWritableDatabase();
        String sortOrder = COL_NAME + " ASC";

        Cursor res = db.query(TABLE_NAME, null, null, null, null, null, sortOrder);
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext())
        {
            //buffer.append("ID:" +res.getString(0)+"\n");
            buffer.append(count+".\t\tWin:"+res.getString(2)+"%\t\t");
            buffer.append("Streak:"+res.getString(3)+"\t\t\t");
            buffer.append(res.getString(1)+"\n\n");
            count++;
        }
        db.close();
        return buffer.toString();
    }

    //return string that sorted by winning streak
    public String databaseSortStreakToString(){
        int count = 1;
        SQLiteDatabase db =  getWritableDatabase();
        String sortOrder = COL_STREAK + " DESC";

        Cursor res = db.query(TABLE_NAME, null, null, null, null, null, sortOrder);
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext())
        {
            //buffer.append("ID:" +res.getString(0)+"\n");
            buffer.append(count+".\t\tWin:"+res.getString(2)+"%\t\t");
            buffer.append("Streak:"+res.getString(3)+"\t\t\t");
            buffer.append(res.getString(1)+"\n\n");
            count++;
        }
        db.close();
        return buffer.toString();
    }
}

