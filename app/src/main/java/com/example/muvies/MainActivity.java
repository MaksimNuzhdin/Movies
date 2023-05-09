package com.example.muvies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    MainViewModel viewModel;
    public static final String TAG="MainActivity";
    private RecyclerView recyclerViewMovies;
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewMovies=findViewById(R.id.recyclerViewMovies);
        moviesAdapter=new MoviesAdapter();
        progressBar=findViewById(R.id.progressBar);
        recyclerViewMovies.setAdapter(moviesAdapter);
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this,2));//отображение сеткой
        viewModel=new ViewModelProvider(this).get( MainViewModel.class);
        viewModel.getMuvies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesAdapter.setMovies(movies);
            }
        });
        moviesAdapter.setOnReachEndListener(new MoviesAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                viewModel.loadMovies();
            }
        });
    viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isLoading) {
            if (isLoading){
                progressBar.setVisibility(View.VISIBLE);
            }else {
                progressBar.setVisibility(View.GONE);
            }

        }
    });
   moviesAdapter.setOnMovieClickListener(new MoviesAdapter.OnMovieClickListener() {
       @Override
       public void onMovieClick(Movie movie) {
           Intent intent = MovieDetailActivity.newIntent(MainActivity.this, movie);
           startActivity(intent);
       }
   });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//добавлем меню на экран
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true; //при тру меню показывается на экране а при фолс нет
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//слушатель клика меню
        if(item.getItemId()==R.id.FavouriteMoviesItem){
            Intent intent = FavouriteMoviesActivity.newIntent(this);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}