package com.example.cam.nim;

import android.app.Activity;
import android.content.Intent;
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
    private GameInfo gameInfo;
    private Spinner rowSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        this.gameInfo = new GameInfo();
        setUpSpinner();
        onSpinnerItemSelected();

    }

    public void setUpSpinner()
    {
        rowSpinner = (Spinner) findViewById(R.id.spinnerRows);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.rowArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rowSpinner.setAdapter(adapter);
        onSpinnerItemSelected();
        onSpinnerNoItemSelected();
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent mainMenuIntent = new Intent(this,MainMenu.class);
        startActivity(mainMenuIntent);
        finish();
    }

    public void onSpinnerItemSelected() {

        switch (Integer.parseInt(rowSpinner.getSelectedItem().toString()))
        {
            case(3):
                this.gameInfo.setnRowAmount(3);
                break;
            case(4):
                this.gameInfo.setnRowAmount(4);
                break;
            case(5):
                this.gameInfo.setnRowAmount(5);
                break;
            case(6):
                this.gameInfo.setnRowAmount(6);
                break;
            case(7):
                this.gameInfo.setnRowAmount(7);
                break;
            case(8):
                this.gameInfo.setnRowAmount(8);
                break;
            case(9):
                this.gameInfo.setnRowAmount(9);
                break;
            case(10):
                this.gameInfo.setnRowAmount(10);
                break;
            default:
                this.gameInfo.setnRowAmount(5);
                break;
        }
    }

    public void onSpinnerNoItemSelected(){
        if(rowSpinner.getSelectedItem() == null)
            this.gameInfo.setnRowAmount(5);
    }
    //Sets if  the audio is on

    public void onAudioRadio(View view) {

        boolean enabledAudio = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case (R.id.radioDisable): {
                if (enabledAudio)
                        this.gameInfo.setBoolEnableAudio(false);
                    break;
            }
            case (R.id.radioEnable): {
                if (enabledAudio)
                        this.gameInfo.setBoolEnableAudio(true);
                    break;
            }
        }


    }
}
