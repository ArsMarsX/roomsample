package com.revolve44.fifthattemptroom;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Station.class}, version = 1)
public abstract class StationDatabase extends RoomDatabase {
    private static StationDatabase instance;

    public abstract StationDao stationDao();

    public static synchronized StationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    StationDatabase.class, "station_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private StationDao stationDao;
        private PopulateDbAsyncTask(StationDatabase db) {
            stationDao = db.stationDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
              //stationDao.insert(new Station("Title 1", "Description 1", 1));
//            stationDao.insert(new Station("Title 2", "Description 2", 2));
//            stationDao.insert(new Station("Title 3", "Description 3", 3));
            return null;
        }
    }
}
