package com.example.muvies;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("author")
    private String author;
    @SerializedName("review")
    private String review;
    @SerializedName("type")
    private String type;

    @Override
    public String toString() {
        return "Comment{" +
                "author='" + author + '\'' +
                ", review='" + review + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public String getReview() {
        return review;
    }

    public String getType() {
        return type;
    }

    public Comment(String author, String review, String type) {
        this.author = author;
        this.review = review;
        this.type = type;
    }
}
