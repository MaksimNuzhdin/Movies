package com.example.muvies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {
    private static final String TAG="MovieDetailViewModel";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();
    private final MutableLiveData<List<Comment>> commentsLD = new MutableLiveData<>();
    private final movieDao dao;

    public LiveData<List<Comment>> getComments() {
        return commentsLD;
    }
    public void insertMovie(Movie movie){
        Disposable disposable = dao.insertMovie(movie)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }
    public void removeMovie(int movieID){
        Disposable disposable = dao.removeMovie(movieID)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        dao=MovieDatabase.getInstance(getApplication()).movieDao();
    }
    public LiveData<Movie> getFavouritesMovie(int movieID){
        return dao.getFavouriteMovie(movieID);

    }

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    public void loadTrailers(int id) {
        Disposable disposable = ApiFactory.apiService.loadTrailers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<TrailerResponse, List<Trailer>>() {//метод преозбразовывает класс респонс в класс лист трейлер
                    @Override
                    public List<Trailer> apply(TrailerResponse trailerResponse) throws Throwable {
                        return trailerResponse.getTrailersList().getTrailers();//переопределили метод указали как преобразовать класс
                    }
                })
                .subscribe(new Consumer<List<Trailer>>() {//указали новый класс
                    @Override
                    public void accept(List<Trailer> trailerList) throws Throwable {
                        trailers.setValue(trailerList);//вставили нужное значение
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        throwable.toString();
                    }
                });
        compositeDisposable.add(disposable);
    }
    public void loadComments(int id){
        Disposable disposable = ApiFactory.apiService.loadComments(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<CommentsResponse, List<Comment>>() {
                    @Override
                    public List<Comment> apply(CommentsResponse commentsResponse) throws Throwable {
                        return commentsResponse.getComments();
                    }
                })
                .subscribe(new Consumer<List<Comment>>() {
                    @Override
                    public void accept(List<Comment> comments) throws Throwable {
                        commentsLD.setValue(comments);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}

