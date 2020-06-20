package com.revolve44.fifthattemptroom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class StationViewModel extends AndroidViewModel {
    private StationRepository repository;
    private LiveData<List<Station>> allStations;


    public StationViewModel(@NonNull Application application) {
        super(application);
        repository = new StationRepository(application);
        allStations = repository.getAllStation();
    }
    public void insert (Station station){
        repository.insert(station);
    }
    public void update(Station station){
        repository.update(station);
    }
    public void delete(Station station){
        repository.delete(station);
    }
    public void deleteAllStations(){
        repository.deleteAllStations();
    }
    public LiveData<List<Station>> getAllStations(){
        return allStations;
    }
}
