package com.example.cam.nim;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
/*
Class:Options
The activity creates the menu to setup the game
 */
public class OptionsActivity extends Activity {
    private GameInfo gameInfo;
    private Spinner rowSpinner,difficultySpinner;
    private Button okStart;
    private Button cancelStart;
    private RadioGroup playerGroup;
    private RadioGroup audioGroup;
    private RadioGroup computerGroup;
    private Dialog changePlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        playerGroup = (RadioGroup) findViewById(R.id.PlayerGroup);
        audioGroup = (RadioGroup) findViewById(R.id.AudioGroup);
        computerGroup = (RadioGroup) findViewById(R.id.computerGroup);

        gameInfo = new GameInfo();
        setUpRowSpinner();
        setUpDifficultySpinner();

        okStart = (Button) findViewById(R.id.okStart);
        okStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent playIntent = new Intent(v.getContext(), GameActivity.class);
                Bundle mBundle = new Bundle();
                /*Bundles game info up into type Bundle so that it can be passed when playIntent
                * is started. GameActivity will then "unbundle" and create a new GameInfo object
                * with identical values.                                                        */
                mBundle.putBoolean("boolEnableAudio", gameInfo.isBoolEnableAudio());//Add audio to bundle
                mBundle.putBoolean("boolPlayerTurn", gameInfo.isBoolPlayerTurn());//Add player turn to bundle
                mBundle.putBoolean("boolComputer", gameInfo.isBoolComputer());//Add player turn to bundle
                mBundle.putLong("computerSpeed", gameInfo.getComputerSpeed());//Add computer speed to bundle
                mBundle.putInt("rowAmount", gameInfo.getnRowAmount());//Add row amount to bundle
                mBundle.putDouble("computerDifficulty", gameInfo.getComputerDifficulty());//Add difficulty to bundle
                mBundle.putString("newPlayerName", gameInfo.getUpdatedPlayer1());
                mBundle.putString("newOtherPlayerName",gameInfo.getUpdatePlayer2());
                playIntent.putExtra("mBundle", mBundle);//Adds bundle to playIntent
                startActivity(playIntent);
                finish();
            }
        });

        cancelStart = (Button) findViewById(R.id.canelStart);
        cancelStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenuIntent = new Intent(OptionsActivity.this, MainMenuActivity.class);
                startActivity(mainMenuIntent);
                finish();
            }
        });

    }

    public void setUpRowSpinner()
    {
        rowSpinner = (Spinner) findViewById(R.id.spinnerRows); // finds the spinner ID
        // Sets the items  defined in the string.xml and layout of the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.rowArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rowSpinner.setAdapter(adapter);

        rowSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position + 3) {
                    case 3:
                        gameInfo.setnRowAmount(3);
                        break;
                    case 4:
                        gameInfo.setnRowAmount(4);
                        break;
                    case 5:
                        gameInfo.setnRowAmount(5);
                        break;
                    case 6:
                        gameInfo.setnRowAmount(6);
                        break;
                    case (7):
                        gameInfo.setnRowAmount(7);
                        break;
                    case (8):
                        gameInfo.setnRowAmount(8);
                        break;
                    case (9):
                        gameInfo.setnRowAmount(9);
                        break;
                    case (10):
                        gameInfo.setnRowAmount(10);
                        break;
                    default:
                        gameInfo.setnRowAmount(5);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }
    public void setUpDifficultySpinner()
    {
        difficultySpinner = (Spinner) findViewById(R.id.spinnerDiffculty); // finds the spinner ID
        // Sets the items  defined in the string.xml and layout of the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.DifficultyArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(adapter);

    }

    public void onDifficultySpinnerSelection()
    {
        switch(difficultySpinner.getSelectedItem().toString())
        {
            case("Easy"):
                break;
            case("Medium"):
                break;
            case("Hard"):
                break;
        }
    }

    //Takes the player back to the main menu if the player clicks the back button
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent mainMenuIntent = new Intent(this,MainMenuActivity.class);
        startActivity(mainMenuIntent);
        finish();
    }

    public void ChangePlayerName( View view){
        changePlayerName = new Dialog(OptionsActivity.this);
        changePlayerName.requestWindowFeature(Window.FEATURE_NO_TITLE);
        changePlayerName.setContentView(R.layout.dialog_name);
        changePlayerName.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button applyButton = (Button) changePlayerName.findViewById(R.id.okName);
        Button cancelButton = (Button) changePlayerName.findViewById(R.id.cancelName);
        final EditText playerEditText = (EditText) changePlayerName.findViewById(R.id.playerEditName);
        final RadioGroup playerSwitch = (RadioGroup) changePlayerName.findViewById(R.id.PlayerNameGroup);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int choice = playerSwitch.getCheckedRadioButtonId();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(playerEditText.getWindowToken(), 0);
                if (choice == R.id.playerOne && !playerEditText.getText().toString().isEmpty())
                    gameInfo.setUpdatedPlayer1(playerEditText.getText().toString());
                if (choice == R.id.playerTwo && !playerEditText.getText().toString().isEmpty())
                    gameInfo.setUpdatePlayer2(playerEditText.getText().toString());
                changePlayerName.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayerName.dismiss();
            }
        });

        changePlayerName.show();
    }

    //Sets who is first

    public void onPlayerRadio(View view) {

        int selectedRadio = playerGroup.getCheckedRadioButtonId();

        switch (selectedRadio) {
            case (R.id.radioOponent): {
                gameInfo.setBoolPlayerTurn(false);
                break;
            }
            case (R.id.radioPlayer): {
                gameInfo.setBoolPlayerTurn(true);
                break;
            }
            default:
            {
                gameInfo.setBoolEnableAudio(true);
                break;
            }
        }
    }

    //Sets if  the audio is on

    public void onComputerRadio(View view) {

        int selectedRadio = computerGroup.getCheckedRadioButtonId();

        switch (selectedRadio) {
            case (R.id.radioAgainstPlayer): {
                gameInfo.setBoolComputer(false);
                break;
            }
            case (R.id.radioAgainstComp): {
                gameInfo.setBoolComputer(true);
                break;
            }
            default:
            {
                gameInfo.setBoolComputer(true);
                break;
            }
        }
    }


    //Sets if  the audio is on

    public void onAudioRadio(View view) {

        int selectedRadio = audioGroup.getCheckedRadioButtonId();

        switch (selectedRadio) {
            case (R.id.radioDisable): {
                    gameInfo.setBoolEnableAudio(false);
                    break;
            }
            case (R.id.radioEnable): {
                    gameInfo.setBoolEnableAudio(true);
                    break;
            }
            default: {
                gameInfo.setBoolEnableAudio(true);
                break;
            }
        }
    }
}
