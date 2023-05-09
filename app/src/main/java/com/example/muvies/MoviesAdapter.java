package com.example.muvies;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<Movie> movies = new ArrayList<>();
    private OnReachEndListener onReachEndListener;
    private OnMovieClickListener onMovieClickListener;

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }



    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();//чтобы данные в aдаптере были обновлены
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.moovie_item, parent, false);// создали вью из активити
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        double rating = movie.getRaiting().getKp();
        int backgroundID;
        if (rating > 7) {
            backgroundID = R.drawable.circle_green;
        } else if (rating > 5) {
            backgroundID = R.drawable.circle_yellow;

        } else {
            backgroundID = R.drawable.circle_red;
        }
        Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), backgroundID);
        holder.textViewRaitong.setBackground(drawable);
        if (movie.getPoster() == null) {
            Glide.with(holder.itemView)
                    .load("https://sd.keepcalms.com/i/keep-calm-poster-not-found.png")
                    .into(holder.imageViewPoster);
        } else {
            Glide.with(holder.itemView)
                    .load(movie.getPoster().getUrl())
                    .into(holder.imageViewPoster);
        }


        holder.textViewRaitong.setText(String.valueOf(rating));
        if(position>=movies.size()-10 && onReachEndListener!=null){
            onReachEndListener.onReachEnd();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMovieClickListener!=null){
                    onMovieClickListener.onMovieClick(movie);
                }
            }
        });


    }
    interface OnReachEndListener{
        void onReachEnd();
    }
    interface OnMovieClickListener{
        void onMovieClick(Movie movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewPoster;
        private final TextView textViewRaitong;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewRaitong = itemView.findViewById(R.id.textViewRaitong);
        }
    }

}
