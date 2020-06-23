package com.revolve44.fifthattemptroom;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class  StationAdapter extends RecyclerView.Adapter<StationAdapter.StationHolder> {
    //private Context context;
    private List<Station> stations = new ArrayList<>();
    private int selectedPosition = -1;// no selection by default***
    private String nameofStation;
    private int nominalPowerofStation;
    private Context context;

    private View.OnClickListener mOnItemClickListener;





    @NonNull
    @Override
    public StationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        Log.d(TAG, "onBindViewHolder: TTT"+ stations.get(1).toString());
//        System.out.println(stations + " 777");
         context = parent.getContext();

        SharedPreferences prefs = context.getSharedPreferences(
                "keeper", Context.MODE_PRIVATE);
        selectedPosition = prefs.getInt("selected", selectedPosition);

//        SharedPreferences prefs = PreferenceManager
//                .getDefaultSharedPreferences(this);
        Log.d(TAG, "444 onBindViewHolder: 0");
//        selectedPosition = prefs.getInt("selected", 1); // getting Integer
//
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString("name",nameofStation);
//        editor.putInt("selected", selectedPosition);
//        editor.apply();

        return new StationHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull final StationHolder holder, final int position) {
        Station currentStation2 = stations.get(0);
        Log.d("TYT", "run: rock" + currentStation2.getTitle());// here i may call for a position
        Log.d(TAG, "444 onBindViewHolder: 1");


        // Position started from 0
        if(selectedPosition == position){
            holder.setMyStation.setChecked(true);
        }
        else{
            holder.setMyStation.setChecked(false);
        }
        // set data from Database
        Station currentStation = stations.get(position);

        holder.textViewTitle.setText(currentStation.getTitle());
        holder.textViewDescription.setText(currentStation.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentStation.getPriority()));

        Log.i(TAG, "StationHolder: LIST " + currentStation.toString());
        if (position == selectedPosition){
            nameofStation = currentStation.getTitle();
            nominalPowerofStation = currentStation.getPriority();
        }

        holder.setMyStation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                selectedPosition = holder.getAdapterPosition();
                //saveSelect(selectedPosition);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms

                        notifyDataSetChanged();

                    }
                }, 100);
            }
        });



        Log.d(TAG, "onBindViewHolder: position is" + selectedPosition );
        Log.d(TAG, "444 onBindViewHolder: 2");
    }

    //TODO: Step 2 of 4: Assign itemClickListener to your local View.OnClickListener variable
    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        Log.d(TAG, "setOnItemClickListener: its works!");
        mOnItemClickListener = itemClickListener;

    }
    @Override
    public int getItemCount() {
        return stations.size();
    }
    public void setStations(List<Station> stations) {
        this.stations = stations;
        notifyDataSetChanged(); // recyclerview refresh
    }
    class StationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private CheckBox setMyStation;
        // Started first

        public StationHolder(final View itemView) {
            super(itemView);

            Log.d(TAG, "444 onBindViewHolder: 3");

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
            setMyStation = itemView.findViewById(R.id.checkBox);



            //TODO: Step 3 of 4: setTag() as current view holder along with
            // setOnClickListener() as your local View.OnClickListener variable.
            // You can set the same mOnItemClickListener on multiple views if required
            // and later differentiate those clicks using view's id.
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);

            setMyStation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    //selectedPosition = getAdapterPosition();


                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms

                            //notifyDataSetChanged();
                            SharedPreferences prefs = context.getSharedPreferences(
                                    "keeper", Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("selected", selectedPosition);
                            editor.apply();
                            Log.d(TAG, "run: check prefs");

                        }
                    }, 50);
                }
            });

//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    // Do something after 5s = 5000ms
//
//                }
//            }, 300);

            Log.d(TAG, "444 onBindViewHolder: 4");


        }


        @Override
        public void onClick(View view) {
            Log.i(TAG, "onClick: test1 " + textViewTitle.getText().toString());
            ;
        }
    }
    private void saveSelect(int selectedPosition) {
        //SharedPreferences.Editor editor2 = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE).edit();
        SharedPreferences prefs = context.getSharedPreferences(
                "keeper", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("selected", selectedPosition);
        editor.apply();
    }
}
