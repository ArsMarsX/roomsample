package com.revolve44.fifthattemptroom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StationViewModel stationViewModel;

    private DrawerLayout mDrawer;
    private Toolbar toolbarx;
    private NavigationView nvDrawer;
    private StationDao stationDao2;

    private List<Station> stations2 = new ArrayList<>();
    int position;


    //Context context;
    //int selectedPosition = 2;

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

            Toast.makeText(MainActivity.this, "You Clicked: " + position, Toast.LENGTH_SHORT).show();
        }
    };



    public static final int ADD_NOTE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);



        SharedPreferences prefs = getApplicationContext().getSharedPreferences(
                "keeper", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
       // editor.putInt("selected", selectedPosition);
        editor.apply();

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

        stationViewModel = ViewModelProviders.of(this).get(StationViewModel.class);
        stationViewModel.getAllStations().observe(this, new Observer<List<Station>>() {
            @Override
            public void onChanged(@Nullable List<Station> stations) {
                //update RecyclerView
                adapter.setStations(stations);
                //stations.get()
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms


                // You can directly print your ArrayList
                System.out.println(stations2 + "   999");
                Station station = new Station("hey",null, 10);
                System.out.println(station.toString() + "   999");

//               StationDatabase database = StationDatabase.getInstance(context);
//               Log.d("TAG", "run: 000" + database.stationDao().getById(1));

                //Log.d("TAG", "onCreate: 888"+ stationDao2.getAllStations().toString());
                //station.setId(0);

//                Employee employee = getEmployeeById(11L);
//                System.out.println(employee);

//                Station currentStation2 = stations2.get(0);
//                Log.d("TYT", "run: rock2" + currentStation2.getTitle());// here i may call for a position
                //Station station1 = stations2.get(1);




            }
        }, 2000);

    }
    //just transition data from another activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            //getting from another activity
            String title = data.getStringExtra(AddStationActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddStationActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddStationActivity.EXTRA_PRIORITY, 1);

            // sending catching data
            Station station = new Station(title, description, priority); // init constructor and class entity
            stationViewModel.insert(station); // call method from viewmodel


            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
