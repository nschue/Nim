package com.example.cam.nim;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;

import java.text.DecimalFormat;

public class DatabaseHelper extends SQLiteOpenHelper{

    String PLAYER_DATABASE = null;
    String TABLE_NAME = null;
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_WIN = "WIN";
    public static final String COL_LOSES = "LOSES";
    public static final String COL_WIN_PERCENT = "WIN_PERCENT";
    public static final String COL_STREAK = "STREAK";
    DecimalFormat formatter = new DecimalFormat("#0.00");


    public DatabaseHelper(Context context, String dataName, String tableName) {
        super(context, dataName, null, 1);
        PLAYER_DATABASE = dataName;
        TABLE_NAME = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT UNIQUE,WIN INTEGER,LOSES INTEGER,WIN_PERCENT DOUBLE,STREAK INTEGER)");
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
    public boolean insertData(String name,String win,String loses,String streak) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        //String winPercent = String.format("%.2f", (double)(Integer.parseInt(win))/(Integer.parseInt(win)+Integer.parseInt(loses))*100);
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_WIN,win);
        contentValues.put(COL_LOSES,loses);
        contentValues.put(COL_STREAK, streak);
        contentValues.put(COL_WIN_PERCENT,"0.0");

        if(checkName(name) == null) {
            this.getWritableDatabase().insertOrThrow(TABLE_NAME, "", contentValues);
            db.close();
            return true;
        }
        db.close();
        return false;
    }
    public void deletePlayer(String playerName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COL_NAME + "=\"" + playerName + "\";");
    }
    //return string sorted base on winning percentage
    public String databaseToString(String sortBy){
        int count = 1;
        SQLiteDatabase db =  getWritableDatabase();


        Cursor res = db.query(TABLE_NAME, null, null, null, null, null, sortBy);
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext())
        {
            buffer.append(count+".\t\tW:"+res.getString(2)+"\t\t");
            buffer.append("L:"+res.getString(3)+"\t\t");
            buffer.append("W:"+formatter.format(res.getDouble(4))+"%\t\t");
            buffer.append("Stk:"+res.getString(5)+"\t\t");
            buffer.append(res.getString(1)+"\n\n");
            count++;
        }
        db.close();
        return buffer.toString();
    }
    public Cursor checkName(String name)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sortOrder = COL_NAME + " ASC";
        Cursor res = db.query(TABLE_NAME, null, null, null, null, null, sortOrder);

        //check for duplicate name if it exist then it won't add to the data
        while(res.moveToNext())
        {
            if(name.equalsIgnoreCase(res.getString(1))){
                return res;
            }
        }
        return null;
    }
    public void updateData(String name, String win,String loses, String streak)
    {
        int update_win = Integer.parseInt(win);
        int update_loses = Integer.parseInt(loses);
        int update_streak = Integer.parseInt(streak);

        Cursor data = checkName(name);
        int dwin = data.getInt(2);
        int dloses = data.getInt(3);
        int dstreak = data.getInt(5);

        update_win += dwin;
        update_loses += dloses;
        update_streak += dstreak;

        if(update_streak <= 0){ update_streak = 0;}


        String winPercent = String.format("%.2f", (double)update_win/(update_win+update_loses)*100);

        SQLiteDatabase db = getWritableDatabase();
        Cursor res = checkName(name);
        ContentValues cv = new ContentValues();
        cv.put(COL_WIN,Integer.toString(update_win));
        cv.put(COL_LOSES,Integer.toString(update_loses));
        cv.put(COL_STREAK,Integer.toString(update_streak));
        cv.put(COL_WIN_PERCENT,winPercent);
        db.update(TABLE_NAME,cv,"ID="+res.getString(0),null);
    }
}


