package com.example.muvies;

import com.google.gson.annotations.SerializedName;

public class TrailerResponse {
    @SerializedName("videos")
    private Video trailersList;

    @Override
    public String toString() {
        return "TrailerResponse{" +
                "trailersList=" + trailersList +
                '}';
    }

    public Video getTrailersList() {
        return trailersList;
    }

    public TrailerResponse(Video trailersList) {
        this.trailersList = trailersList;
    }
}
