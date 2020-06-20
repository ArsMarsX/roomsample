package com.revolve44.fifthattemptroom;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class StationRepository {

    private StationDao stationDao;
    private LiveData<List<Station>> allStation;
    public StationRepository(Application application){
        // initialize
        StationDatabase database = StationDatabase.getInstance(application);
        stationDao = database.stationDao();
        allStation = stationDao.getAllStations();
    }

    public void insert(Station station){
        new InsertStationAsyncTask(stationDao).execute(station);

    }
    public void update(Station station){
        new UpdateStationAsyncTask(stationDao).execute(station);

    }
    public void delete(Station station){
        new DeleteStationAsyncTask(stationDao).execute(station);

    }
    public void deleteAllStations(){
        new DeleteAllStationsAsyncTask(stationDao).execute();

    }
    public LiveData<List<Station>> getAllStation(){
        return allStation;
    }

    private static class InsertStationAsyncTask extends AsyncTask<Station, Void, Void> {
        private StationDao stationDao;
        private InsertStationAsyncTask(StationDao stationDao){
            this.stationDao = stationDao;
        }

        @Override
        protected Void doInBackground(Station... stations) {
            stationDao.insert(stations[0]);
            return null;
        }
    }
    private static class UpdateStationAsyncTask extends AsyncTask<Station, Void, Void>{
        private StationDao stationDao;
        private UpdateStationAsyncTask(StationDao stationDao){
            this.stationDao = stationDao;
        }
        @Override
        protected Void doInBackground(Station... stations) {
            stationDao.update(stations[0]);
            return null;
        }
    }
    private static class DeleteStationAsyncTask extends AsyncTask<Station, Void, Void>{
        private StationDao stationDao;

        public DeleteStationAsyncTask(StationDao stationDao) {
            this.stationDao = stationDao;
        }

        @Override
        protected Void doInBackground(Station... stations) {
            stationDao.delete(stations[0]);
            return null;
        }
    }
    private static class DeleteAllStationsAsyncTask extends AsyncTask<Void, Void, Void>{
        private StationDao stationDao;

        public DeleteAllStationsAsyncTask(StationDao stationDao) {
            this.stationDao = stationDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            stationDao.deleteAllStations();
            return null;
        }
    }
}
