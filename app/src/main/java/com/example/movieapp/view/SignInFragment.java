package com.example.movieapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movieapp.databinding.FragmentSignInBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class SignInFragment extends Fragment {
    private FragmentSignInBinding binding;
    private FirebaseAuth firebaseAuth;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);

        binding.backIntroBtn.setOnClickListener(v -> hideFragment());

        binding.signInBtn.setOnClickListener(v -> signInEmailAndPwd());

        return binding.getRoot();
    }

    private void hideFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    private void signInEmailAndPwd() {
        String email = binding.inputEmailSignIn.getText().toString().trim();
        String pwd = binding.inputPwdSignIn.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd)) {
            firebaseAuth.signInWithEmailAndPassword(email, pwd)
                    .addOnSuccessListener(authResult -> Toast.makeText(requireContext(), "Signed in successfully", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> {
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(requireContext(), "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
                        } else if (e instanceof FirebaseAuthInvalidUserException) {
                            Toast.makeText(requireContext(), "No account with this email found.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Failed to sign in: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(requireContext(), "No Empty Fields are Allowed!", Toast.LENGTH_SHORT).show();
        }
    }
}