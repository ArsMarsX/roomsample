package com.revolve44.fifthattemptroom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {



    private StationViewModel stationViewModel;

    private DrawerLayout mDrawer;
    private Toolbar toolbarx;
    private NavigationView nvDrawer;
    private StationDao stationDao2;

    private List<Station> stations2 = new ArrayList<>();
    private List<Station> stations3 = new ArrayList<>();
    private int position;

    String TAG = "check";
    RecyclerView recyclerView;

    //Context context;
    //int selectedPosition = 2;
    /*
    Need:

    Name
    Nominal Power
    Longitude
    Latitude

     */


    public static final int ADD_NOTE_REQUEST = 1;
    public void FirstLaunch(){
        SharedPreferences prefs = getSharedPreferences("keeper", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            Intent intent = new Intent(MainActivity.this, AddStationActivity.class);
            startActivityForResult(intent, ADD_NOTE_REQUEST);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirstLaunch();
        final SharedPreferences prefs = getSharedPreferences("keeper", Context.MODE_PRIVATE);


        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

         final StationAdapter adapter = new StationAdapter();
        recyclerView.setAdapter(adapter);
        //TODO: Step 1 of 4: Create and set OnItemClickListener to the adapter.
        adapter.setOnItemClickListener(onItemClickListener);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddStationActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////
        stationViewModel = ViewModelProviders.of(this).get(StationViewModel.class);
        stationViewModel.getAllStations().observe(this, new Observer<List<Station>>() {
            @Override
            public void onChanged(@Nullable final List<Station> stations ) {
                adapter.setStations(stations);
                final int listSize = stations.size();

//                for(int i = 0; i < listSize; ++i){
//                    stations2.add("whatever");
//                }
                stations2 = stations;
                position = prefs.getInt("selected", position);
                //update RecyclerView
                //stations.get(position);
                try {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 100ms
                            try {
                                Station currentStation = stations.get(position);
                                Log.i(TAG, "onChanged: test2" + stations);
                                Toast.makeText(MainActivity.this, "Lenght is "+ listSize+" position is "+ position+ " onChanged " + currentStation.getName(), Toast.LENGTH_SHORT).show();
                                AccessData();
                            }catch (Exception r){

                            }
                        }
                    }, 600);

                }catch (Exception e){

                }

            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                SharedPreferences prefs = getSharedPreferences("keeper", Context.MODE_PRIVATE);


                if (position == viewHolder.getAdapterPosition()){
                    int newpos = viewHolder.getAdapterPosition();
                    newpos = prefs.getInt("selected", -1);
                    Snackbar.make(findViewById(android.R.id.content),"Сannot be deleted" ,Snackbar.LENGTH_SHORT).show();

                }else{

                    Snackbar.make(findViewById(android.R.id.content),"Station deleted" ,Snackbar.LENGTH_SHORT).show();
                }
                stationViewModel.delete(adapter.getStationAt(viewHolder.getAdapterPosition()));
                adapter.notifyDataSetChanged();

                Log.i(TAG, "onSwiped: test5 "+ position+ " and swiped pos "+ viewHolder.getAdapterPosition()+ " direction "+ direction);
                Snackbar.make(findViewById(android.R.id.content),"Station deleted" ,Snackbar.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms


                // You can directly print your ArrayList
                System.out.println(stations2 + "   999");
               // Station station = new Station("hey",null, 10);
               // System.out.println(station.toString() + "   999");
            }
        }, 2000);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

    }
    //just transition data from another activity
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO: Step 4 of 4: Finally call getTag() on the view.
            // This viewHolder will have all required values.
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            position = viewHolder.getAdapterPosition();

            // viewHolder.getItemId();
            // viewHolder.getItemViewType();
//            viewHolder.itemView.;
            //Station thisItem = stations2.get(position);
            //Station currentStation = stations2.get(position);
            //SharedPreferences sharedPreferences = getSharedPreferences("MasterSave", MODE_PRIVATE);
            SharedPreferences prefs = getSharedPreferences("keeper", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            //editor.putString("name",nameofStation);
            editor.putInt("selected", position);
            editor.apply();
            Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();//? may NULL

            Toast.makeText(MainActivity.this, "You Clicked: " + position, Toast.LENGTH_SHORT).show();
        }
    };

    public void AccessData(){
        Station currentStation2 = stations2.get(position);
        //Station currentStation3 = stations3.get(2);
        //String a = String.valueOf(stations2.get(1));
        Snackbar.make(findViewById(android.R.id.content),"Your text "+ currentStation2.getName(),Snackbar.LENGTH_SHORT).show();

        Log.i(TAG, "AccessData: " +currentStation2.getName() + "and second is " );
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {// for what this checking?
            //getting from another activity
            String name = data.getStringExtra(AddStationActivity.EXTRA_NAME);
            int nominalpower = data.getIntExtra(AddStationActivity.EXTRA_NOMINALPOWER, 100);
            float latitude = data.getFloatExtra(AddStationActivity.EXTRA_LATITUDE,0f);
            float longitude = data.getFloatExtra(AddStationActivity.EXTRA_LONGITUDE,0f);
//            String description = data.getStringExtra(AddStationActivity.EXTRA_LATITUDE);
//            int priority = data.getIntExtra(AddStationActivity.EXTRA_PRIORITY);

            // sending catching data
            Station station = new Station( name,  nominalpower,  latitude,  longitude); // init constructor and class entity
            stationViewModel.insert(station); // call method from viewmodel


            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
