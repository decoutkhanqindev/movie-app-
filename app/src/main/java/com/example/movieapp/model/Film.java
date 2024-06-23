package com.example.movieapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Film implements Serializable {
    private ArrayList<Cast> castArrayList;
    private String description;
    private ArrayList<String> genre;
    private int imdb;
    private String poster;
    private String time;
    private String title;
    private String trailer;
    private String year;

    public Film() {
    }

    public ArrayList<Cast> getCastArrayList() {
        return castArrayList;
    }

    public void setCastArrayList(ArrayList<Cast> castArrayList) {
        this.castArrayList = castArrayList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }

    public int getImdb() {
        return imdb;
    }

    public void setImdb(int imdb) {
        this.imdb = imdb;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
