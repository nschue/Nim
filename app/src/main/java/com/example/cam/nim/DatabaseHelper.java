package com.example.cam.nim;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper{

    String PLAYER_DATABASE = null;
    String TABLE_NAME = null;
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_WIN = "WIN";
    public static final String COL_TOTAL = "TOTAL";
  //  public static final String COL_WIN_PERCENT = "WIN_PERCENT";
    public static final String COL_STREAK = "STREAK";
    private ArrayList<ScoreItem>  databaseInfo = new ArrayList<>();


    public DatabaseHelper(Context context, String dataName, String tableName) {
        super(context, dataName, null, 1);
        PLAYER_DATABASE = dataName;
        TABLE_NAME = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT UNIQUE,WIN INTEGER,TOTAL INTEGER,STREAK INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String name,String win,String total,String streak) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_NAME,name);
        contentValues.put(COL_WIN,win);
        contentValues.put(COL_TOTAL,total);
        contentValues.put(COL_STREAK, streak);
        //contentValues.put(COL_WIN_PERCENT, "0.0");
        //check if not exist then add new one
        if(checkName(name) == null) {
            this.getWritableDatabase().insertOrThrow(TABLE_NAME, "", contentValues);
            db.close();
            return true;
        }
        else
        {
            Cursor existname = checkName(name);
            updateData(name,win,total,streak);
            db.close();
            return true;
        }
    }
    public void deletePlayer(String playerName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COL_NAME + "=\"" + playerName + "\";");
    }
    public void deleteAllData() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }

    //return string sorted base on winning percentage
    public ArrayList<ScoreItem> databaseToString(String sortBy){

        SQLiteDatabase db =  getWritableDatabase();
        databaseInfo = new ArrayList<>();

        Cursor res = db.query(TABLE_NAME, null, null, null, null, null,sortBy);

        while(res.moveToNext())
        {
            ScoreItem temp = new ScoreItem(res.getString(1),res.getString(2),res.getString(3),res.getString(4));
            databaseInfo.add(temp);
        }
        db.close();
        return databaseInfo;
    }
    public Cursor checkName(String name)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sortOrder = COL_NAME + " ASC";
        Cursor res = db.query(TABLE_NAME, null, null, null, null, null, sortOrder);

        while(res.moveToNext())
        {
            if(name.equalsIgnoreCase(res.getString(1))){
                return res;
            }
        }
        return null;
    }
    public void updateData(String name, String win,String total, String streak)
    {
        int update_win = Integer.parseInt(win);
        int update_total = Integer.parseInt(total);
        int update_streak = Integer.parseInt(streak);

        Cursor data = checkName(name);
        int dwin = data.getInt(2);
        int dtotal = data.getInt(3);
        int dstreak = data.getInt(4);

        update_win += dwin;
        update_total += dtotal;
        if(update_total != -1)
            update_streak += dstreak;
        else
            update_streak = 0;

        SQLiteDatabase db = getWritableDatabase();
        Cursor res = checkName(name);
        ContentValues cv = new ContentValues();
        cv.put(COL_WIN,Integer.toString(update_win));
        cv.put(COL_TOTAL,Integer.toString(update_total));
        cv.put(COL_STREAK,Integer.toString(update_streak));
       // cv.put(COL_WIN_PERCENT,winPercent);
        db.update(TABLE_NAME,cv,"ID="+res.getString(0),null);
    }
    public int getWins(String name){
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = checkName(name);
        int wins = res.getInt(2);
        return wins;
    }
}
