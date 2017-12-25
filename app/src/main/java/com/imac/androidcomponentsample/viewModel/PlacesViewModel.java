package com.imac.androidcomponentsample.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.imac.androidcomponentsample.model.Place;
import com.imac.androidcomponentsample.repository.PlacesRepository;
import com.imac.androidcomponentsample.repository.room.AppDatabase;

import java.util.List;

/**
 * Created by imac on 11/17/17.
 */

public class PlacesViewModel extends AndroidViewModel {

    private LiveData<List<Place>> mPlacesObservable;

    private AppDatabase mAppdatabase;

    public PlacesViewModel(@NonNull Application application) {
        super(application);

        mAppdatabase = AppDatabase.getAppDatabase(getApplication());
        mPlacesObservable = mAppdatabase.placeModel().getPlacesList();
    }

    public LiveData<List<Place>> getPlacesObservable() {
        return mPlacesObservable;
    }

    public void callApi() {
        PlacesRepository.getInstance().getPlacesFromApi(getApplication().getBaseContext());
    }
}
