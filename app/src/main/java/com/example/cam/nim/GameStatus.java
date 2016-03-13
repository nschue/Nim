package com.example.cam.nim;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * Created by Cam on 3/11/2016.
 */
public class GameStatus extends SurfaceView implements Runnable{
    volatile boolean playing;
    Thread gameThread = null;

    public GameStatus(Context context)
    {
        super(context);
    }

    @Override
    public void run() {
        while(this.playing)
        {
            update();
            draw();
            control();

        }
    }

    public void pause(){
        this.playing = false;
        try{
            gameThread.join();
        }
        catch(InterruptedException e){

        }
    }
    public void resume(){
        this.playing = true;
        this.gameThread = new Thread();
        this.gameThread.start();
    }

    private void update(){

    }
    private void draw(){

    }
    private void control(){

    }
}
