package com.example.muvies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Video {
    @SerializedName("trailers")
   private List<Trailer> trailers;

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public Video(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    @Override
    public String toString() {
        return "Video{" +
                "trailers=" + trailers +
                '}';
    }
}
