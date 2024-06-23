package com.example.movieapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.model.Film;
import com.example.movieapp.model.FilmRepository;
import com.example.movieapp.model.SliderItem;

import java.util.ArrayList;

public class FilmViewModel extends ViewModel {
    private final FilmRepository filmRepository;
    private final MutableLiveData<ArrayList<SliderItem>> sliderItemsMultiLiveData;
    private final MutableLiveData<ArrayList<Film>> topMoviesMutableLiveData;
    private final MutableLiveData<ArrayList<Film>> upComingMutableLiveData;

    public FilmViewModel() {
        filmRepository = FilmRepository.getInstance();
        sliderItemsMultiLiveData = filmRepository.getSliderItems();
        topMoviesMutableLiveData = filmRepository.getTopMovies();
        upComingMutableLiveData = filmRepository.getUpComing();
    }

    public MutableLiveData<ArrayList<SliderItem>> getSliderItems() {
        return sliderItemsMultiLiveData;
    }

    public MutableLiveData<ArrayList<Film>> getTopMovies() {
        return topMoviesMutableLiveData;
    }

    public MutableLiveData<ArrayList<Film>> getUpComing() {
        return upComingMutableLiveData;
    }
}
