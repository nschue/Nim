package com.example.cam.nim;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class GameActivity extends Activity
{
    private GameInfo mGameInfo;
    private Button mEndButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //GridView gridView = (GridView) findViewById(R.id.play_grid);
       //gridView.setAdapter(new ImageAdapter(this));
/*
        gridView.setOnClickListener(new View.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?>parent, View v, int position, long id)
            {
                Toast.makeText(GameActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });*/

        mEndButton = (Button) findViewById(R.id.end_game_button);
        mEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(v.getContext(), MainMenuActivity.class);
                startActivity(menuIntent);
                finish();
            }
        });


        /*Unbundles extras passed from OptionsActivity to populate local GameInfo object*/
        Bundle extras = getIntent().getExtras();
        mGameInfo = new GameInfo();
        mGameInfo.setBoolEnableAudio(extras.getBoolean("boolEnableAudio"));
        mGameInfo.setBoolPlayerTurn(extras.getBoolean("boolPlayerTurn"));
        mGameInfo.setComputerDifficulty(extras.getDouble("computerDifficulty"));
        mGameInfo.setnRowAmount(extras.getInt("rowAmount"));
        mGameInfo.setComputerSpeed(extras.getDouble("computerSpeed"));

        mGameInfo.populateGameBoard();
    }

    public class ImageAdapter extends BaseAdapter
    {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mGameInfo.getnRowAmount();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(R.drawable.game_piece);
            return imageView;
        }

    }
}
