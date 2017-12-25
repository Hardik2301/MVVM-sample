package com.imac.androidcomponentsample.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.imac.androidcomponentsample.data.AsyncLoadVolley;
import com.imac.androidcomponentsample.data.AsyncResponse;
import com.imac.androidcomponentsample.data.OnAsyncTaskListener;
import com.imac.androidcomponentsample.model.Place;
import com.imac.androidcomponentsample.repository.room.AppDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by imac on 11/17/17.
 */

public class PlacesRepository {

    private static PlacesRepository mInstance;
    private AppDatabase mAppdatabase;

    public static PlacesRepository getInstance() {
        if (mInstance == null) {
            mInstance = new PlacesRepository();
        }
        return mInstance;
    }

    public void getPlacesFromApi(Context context) {
        mAppdatabase = AppDatabase.getAppDatabase(context);

        AsyncLoadVolley asyncLoadVolley = new AsyncLoadVolley(context, "https://maps.googleapis.com/maps/api/place/nearbysearch/json");
        asyncLoadVolley.setOnAsyncTaskListener(new OnAsyncTaskListener() {
            @Override
            public void onTaskBegin() {

            }

            @Override
            public void onTaskComplete(boolean success, String response) {
                if (success) {
                    AsyncResponse mResponse = new AsyncResponse(response);
                    if (mResponse.ifSuccess()) {
                        Log.e("Total Location from Api", mResponse.getPlacelist().size() + "");
                        for (Place place : mResponse.getPlacelist()) {
                            new addAsyncTask(mAppdatabase).execute(place);
                        }
                    }
                }
            }
        });

        Map<String, String> param = new HashMap<String, String>();
        param.put("location", "72.5577,23.0261");
        param.put("radius", "5000");
        param.put("types", "atm,restaurant,bank");
        param.put("sensor", "true");
        param.put("key", "AIzaSyBJvlD3dqnz42r9obhEClc2dEJAdXt9IK8");

        asyncLoadVolley.setParameters(param);
        asyncLoadVolley.beginTask("?location=23.0260983,72.5576983" +
                "&radius=50000&types=atm,restaurant,bank" +
                "&sensor=true&key=AIzaSyBJvlD3dqnz42r9obhEClc2dEJAdXt9IK8");
    }

    private static class addAsyncTask extends AsyncTask<Place, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Place... params) {
            db.placeModel().addPlace(params[0]);
            return null;
        }

    }
}
