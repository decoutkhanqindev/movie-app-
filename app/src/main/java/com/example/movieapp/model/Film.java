package com.example.movieapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Film implements Serializable {
    private ArrayList<Cast> Casts;
    private String Description;
    private ArrayList<String> Genre;
    private int Imdb;
    private String Poster;
    private String Time;
    private String Title;
    private String Trailer;
    private String Year;

    public Film() {
    }

    public ArrayList<Cast> getCasts() {
        return Casts;
    }

    public void setCasts(ArrayList<Cast> casts) {
        Casts = casts;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public ArrayList<String> getGenre() {
        return Genre;
    }

    public void setGenre(ArrayList<String> genre) {
        Genre = genre;
    }

    public int getImdb() {
        return Imdb;
    }

    public void setImdb(int imdb) {
        Imdb = imdb;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTrailer() {
        return Trailer;
    }

    public void setTrailer(String trailer) {
        Trailer = trailer;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }
}
