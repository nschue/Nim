package com.example.cam.nim;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
/*
Class:Menu
The activity creates the dialog box for menu to setup the game
 */
public class Menu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


    }

    public void addSpinner() {
    }
    /* Sets if  the audio is on*/

    public void onAudioRadio(View view) {
        boolean enabledAudio = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case (R.id.radioDisable): {
                if (enabledAudio)

                    break;
            }
            case (R.id.radioEnable): {
                if (enabledAudio)
                    break;
            }
        }


    }
}
