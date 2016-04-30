package com.example.cam.nim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Cam on 4/29/2016.
 */
public class customListAdapter extends BaseAdapter {

        Context context;
        ArrayList<ScoreItem> data;
        private static LayoutInflater inflater = null;

        public customListAdapter(Context context, ArrayList<ScoreItem> data) {
            this.context = context;
            this.data = data;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = convertView;
            if (view == null)
                view = inflater.inflate(R.layout.row, null);
            TextView name = (TextView) view.findViewById(R.id.name);
            name.setText(data.get(position).getPlayerName());

            TextView win = (TextView) view.findViewById(R.id.wins);
            win.setText(data.get(position).getWinAmount());

            TextView total = (TextView) view.findViewById(R.id.total);
            total.setText(data.get(position).getTotalGames());

            TextView streak = (TextView) view.findViewById(R.id.streak);
            streak.setText(data.get(position).getStreak());

            return view;
        }


}
