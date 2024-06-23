package com.example.movieapp.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FilmRepository {
    private static FilmRepository instance;
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final ArrayList<SliderItem> sliderItemArrayList = new ArrayList<>();
    private final MutableLiveData<ArrayList<SliderItem>> sliderItemsMutableLiveData = new MutableLiveData<>();
    private final ArrayList<Film> topMoviesArrayList = new ArrayList<>();
    private final MutableLiveData<ArrayList<Film>> topMoviesMutableLiveData = new MutableLiveData<>();
    private final ArrayList<Film> upComingArrayList = new ArrayList<>();
    private final MutableLiveData<ArrayList<Film>> upComingMutableLiveData = new MutableLiveData<>();
    private final ArrayList<User> userArrayList = new ArrayList<>();
    private final MutableLiveData<ArrayList<User>> userMutableLiveData = new MutableLiveData<>();

    public static  synchronized FilmRepository getInstance(){
        if (instance == null){
            instance = new FilmRepository();
        }
        return instance;
    }


    public MutableLiveData<ArrayList<SliderItem>> getSliderItems() {
        DatabaseReference databaseReference = firebaseDatabase.getReference("Banners");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue : snapshot.getChildren()){
                        SliderItem sliderItem = issue.getValue(SliderItem.class);
                        if (sliderItem != null){
                            sliderItemArrayList.add(sliderItem);
                        }
                    }
                    sliderItemsMutableLiveData.setValue(sliderItemArrayList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("onCancelled to get banners movies ", error.getMessage());
            }
        });

        return sliderItemsMutableLiveData;
    }

    public MutableLiveData<ArrayList<Film>> getTopMovies(){
        DatabaseReference databaseReference = firebaseDatabase.getReference("Items");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue : snapshot.getChildren()){
                        Film film = issue.getValue(Film.class);
                        if (film != null){
                            topMoviesArrayList.add(film);
                        }
                    }
                    topMoviesMutableLiveData.setValue(topMoviesArrayList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("onCancelled to get top movies ", error.getMessage());
            }
        });

        return topMoviesMutableLiveData;
    }

    public MutableLiveData<ArrayList<Film>> getUpComing(){
        DatabaseReference databaseReference = firebaseDatabase.getReference("Upcomming");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue : snapshot.getChildren()){
                        Film film = issue.getValue(Film.class);
                        if (film != null){
                            upComingArrayList.add(film);
                        }
                    }
                    upComingMutableLiveData.setValue(upComingArrayList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("onCancelled to get up coming movies ", error.getMessage());
            }
        });

        return upComingMutableLiveData;
    }


    public MutableLiveData<ArrayList<User>> getUsers() {
        DatabaseReference databaseReference = firebaseDatabase.getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue : snapshot.getChildren()){
                        User user = issue.getValue(User.class);
                        if (user != null){
                            userArrayList.add(user);
                        }
                    }
                    userMutableLiveData.setValue(userArrayList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("onCancelled to get user ", error.getMessage());
            }
        });

        return userMutableLiveData;
    }
}



