package com.revolve44.fifthattemptroom;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "station_table")
public class Station {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
//    private String description;
//    private int priority;
    private int nominalpower; //
    private float latitude; //
    private float longitude; //


    public Station(String name, int nominalpower, float latitude, float longitude) {
        this.name = name;
        this.nominalpower = nominalpower;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNominalpower(int nominalpower) {
        this.nominalpower = nominalpower;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNominalpower() {
        return nominalpower;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
