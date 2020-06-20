package com.revolve44.fifthattemptroom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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





    public static final int ADD_NOTE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final StationAdapter adapter = new StationAdapter();
        recyclerView.setAdapter(adapter);




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
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms

                List<Station> stations2 = new ArrayList<>();
                // You can directly print your ArrayList
                System.out.println(stations2 + "   999");
                Station station = new Station("hey",null, 10);
                System.out.println(station.toString() + "   999");


                Log.d("TAG", "run: " + station.toString());
                //station.setId(0);

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
