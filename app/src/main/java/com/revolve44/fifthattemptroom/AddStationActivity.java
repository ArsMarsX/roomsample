package com.revolve44.fifthattemptroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddStationActivity extends AppCompatActivity {
    public StationDatabase noteDatabase;
    //public final String TABLE_NAME =
    public static final String EXTRA_NAME =
            "com.revolve44.fifthattemptroom.EXTRA_NAME";
    public static final String EXTRA_NOMINALPOWER =
            "com.revolve44.fifthattemptroom.EXTRA_DESCRIPTION";//?
    public static final String EXTRA_LATITUDE =
            "com.revolve44.fifthattemptroom.EXTRA_PRIORITY";
    public static final String EXTRA_LONGITUDE =
            "com.revolve44.fifthattemptroom.EXTRA_PRIORITY";
    private EditText NameInput;
    private EditText NominalPowerInput;
    private EditText LatInput;
    private EditText LonInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        SharedPreferences prefs = getSharedPreferences("keeper", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

        NameInput = findViewById(R.id.edit_text_title);
        NominalPowerInput = findViewById(R.id.edit_text_nominalpower);
        LatInput = findViewById(R.id.edit_text_lat);
        LonInput = findViewById(R.id.edit_text_lon);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_save);
        setTitle("Add Note");
    }
    private void saveNote() {
        String name = NameInput.getText().toString();
        int nominalpower = Integer.parseInt(NominalPowerInput.getText().toString());
        float lat = Float.parseFloat(LatInput.getText().toString());
        float lon = Float.parseFloat(LonInput.getText().toString());

        if (name.trim().isEmpty() || nominalpower == 0 || lat == 0 || lon == 0) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_NOMINALPOWER, nominalpower);
        data.putExtra(EXTRA_LATITUDE, lat);
        data.putExtra(EXTRA_LONGITUDE, lon);
        setResult(RESULT_OK, data);
        finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
