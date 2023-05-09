package com.example.muvies;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
@Entity(tableName = "favourite_movies")
public class Movie implements Serializable {
    //преобразуем в байты  чтобы можно было положить в ПУТ экста при переходе активити
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("year")
    private int year;
    @Embedded//аннотация чтобы сохранить в БД поля того типа которым вы сами написали
    @SerializedName("poster")
    private Poster poster;
    @Embedded
    @SerializedName("rating")
    private Rating rating;




    public Movie(int id, String name, String description, int year, Poster poster, Rating rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
        this.poster = poster;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

//    public String getDiscription() {
//        return description;
//    }


    public String getDescription() {
        return description;
    }

    public Rating getRating() {
        return rating;
    }

    public int getYear() {
        return year;
    }

    public Poster getPoster() {
        return poster;
    }

    public Rating getRaiting() {
        return rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", poster=" + poster +
                ", rating=" + rating +
                '}';
    }
}
