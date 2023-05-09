package com.example.muvies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG ="MovieDetailActivity";
    private static final String EXTRA_Movie="movie";
    private TextView texyViewTitle;
    private ImageView imageViewPoster;
    private TextView texyViewDescription;
    private TextView texyViewYear;
    private TrailersAdapter trailersAdapter;
    private RecyclerView recyclerViewTrailers;
    private MovieDetailViewModel viewModel;
    private CommentsAdapter commentsAdapter;
    private RecyclerView recyclerViewComments;
    private ImageView ImageViewStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();

        recyclerViewTrailers.setAdapter(trailersAdapter);
        recyclerViewComments.setAdapter(commentsAdapter);
        Movie movie= (Movie) getIntent().getSerializableExtra(EXTRA_Movie);//получили объект
        if (movie.getPoster()!=null){
            Glide.with(this)
                    .load(movie.getPoster().getUrl())
                    .into(imageViewPoster);
        } else {

            Glide.with(this)
                    .load("https://sd.keepcalms.com/i/keep-calm-poster-not-found.png")
                    .into(imageViewPoster);
        }


        texyViewTitle.setText(movie.getName());
        texyViewDescription.setText(movie.getDescription());
        texyViewYear.setText(String.valueOf(movie.getYear()));
        trailersAdapter.setOnTrailerClickListener(new TrailersAdapter.OnTrailerClickListener() {
            @Override
            public void onTralerClick(Trailer trailer) {//запускаем видео в браузере
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()) );
                startActivity(intent);
            }
        });

//        Log.d(TAG, "onCreate: "+ApiFactory.apiService.loadTrailers(movie.getId()));

        viewModel.loadComments(movie.getId());
        viewModel.getComments().observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                commentsAdapter.setComments(comments);
                Log.d(TAG, "onChanged: "+comments.toString());
            }
        });
        viewModel.loadTrailers(movie.getId());
    viewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
        @Override
        public void onChanged(List<Trailer> trailers) {
            trailersAdapter.setTrailers(trailers);
            Log.d(TAG, "onChanged: "+trailers.toString());

        }
    });
        Drawable starOff = ContextCompat
                .getDrawable(MovieDetailActivity.this, android.R.drawable.star_big_off);
        Drawable starOn = ContextCompat
                .getDrawable(MovieDetailActivity.this, android.R.drawable.star_big_on);
    viewModel.getFavouritesMovie(movie.getId())
            .observe(this, new Observer<Movie>() {
                @Override
                public void onChanged(Movie movieFromDb) {
                    if(movieFromDb==null){

                        ImageViewStar.setImageDrawable(starOff);
                        ImageViewStar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                viewModel.insertMovie(movie);
                            }
                        });
                    }else {

                        ImageViewStar.setImageDrawable(starOn);
                        ImageViewStar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                viewModel.removeMovie(movie.getId());
                            }
                        });

                    }

                }
            });

    }
    public void initViews(){
        texyViewTitle=findViewById(R.id.texyViewTitle);
        imageViewPoster=findViewById(R.id.imageViewPoster);
        texyViewDescription=findViewById(R.id.texyViewDescription);
        texyViewYear=findViewById(R.id.texyViewYear);
        recyclerViewTrailers=findViewById(R.id.recyclerViewTrailers);
        viewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        trailersAdapter=new TrailersAdapter();
        commentsAdapter= new CommentsAdapter();
        recyclerViewComments=findViewById(R.id.RecyclerViewComments);
        ImageViewStar=findViewById(R.id.ImageViewStar);
    }
    public static Intent newIntent(Context context, Movie movie){
        Intent intent=new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_Movie, movie);//положили обьект
        return intent;

    }

}