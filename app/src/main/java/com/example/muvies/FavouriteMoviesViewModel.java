package com.example.muvies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FavouriteMoviesViewModel extends AndroidViewModel {
    private final movieDao dao ;
    public FavouriteMoviesViewModel(@NonNull Application application) {
        super(application);
        dao= MovieDatabase.getInstance(getApplication()).movieDao();
    }
    public LiveData<List<Movie>> getMovies(){
        return dao.getAllFavouriteMovies();
    }
}
