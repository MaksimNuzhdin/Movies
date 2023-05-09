package com.example.muvies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentsResponse {
    @SerializedName("docs")
private List<Comment> comments;

    @Override
    public String toString() {
        return "CommentsResponse{" +
                "comments=" + comments +
                '}';
    }

    public List<Comment> getComments() {
        return comments;
    }

    public CommentsResponse(List<Comment> comments) {
        this.comments = comments;
    }
}
