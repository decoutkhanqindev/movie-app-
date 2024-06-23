package com.example.movieapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieapp.databinding.FragmentSIgnUpUserNameBinding;

public class SIgnUpUserNameFragment extends Fragment {
    private FragmentSIgnUpUserNameBinding binding;

    public SIgnUpUserNameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSIgnUpUserNameBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }
}