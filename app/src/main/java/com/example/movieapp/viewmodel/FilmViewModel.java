package com.example.movieapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.model.Film;
import com.example.movieapp.model.FilmRepository;
import com.example.movieapp.model.SliderItem;
import com.example.movieapp.model.User;

import java.util.ArrayList;

public class FilmViewModel extends ViewModel {
    private final FilmRepository filmRepository;
    private final MutableLiveData<ArrayList<SliderItem>> sliderItemsMultiLiveData;
    private final MutableLiveData<ArrayList<Film>> topMoviesMutableLiveData;
    private final MutableLiveData<ArrayList<Film>> upComingMutableLiveData;
    private final MutableLiveData<ArrayList<User>> userMutableLiveData;

    public FilmViewModel() {
        filmRepository = FilmRepository.getInstance();
        sliderItemsMultiLiveData = filmRepository.getSliderItems();
        topMoviesMutableLiveData = filmRepository.getTopMovies();
        upComingMutableLiveData = filmRepository.getUpComing();
        userMutableLiveData = filmRepository.getUsers();
    }

    public MutableLiveData<ArrayList<SliderItem>> getSliderItems(){
        return sliderItemsMultiLiveData;
    }

    public MutableLiveData<ArrayList<Film>> getTopMovies(){
        return topMoviesMutableLiveData;
    }

    public MutableLiveData<ArrayList<Film>> getUpComing(){
        return upComingMutableLiveData;
    }

    public MutableLiveData<ArrayList<User>> getUsers(){
        return userMutableLiveData;
    }

}
