package com.example.movieapp.view;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;

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

import com.example.movieapp.databinding.FragmentSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpFragment extends Fragment {
    private FragmentSignUpBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    public SignUpFragment() {
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
        binding = FragmentSignUpBinding.inflate(inflater, container, false);

        binding.backIntroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFragment();
            }
        });

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpEmailAndPwd();
            }
        });

        return binding.getRoot();
    }

    private void hideFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    private void signUpEmailAndPwd() {
        String email = binding.inputEmailSignUp.getText().toString().trim();
        String pwd = binding.inputPwdSignUp.getText().toString().trim();
        String pwd2 = binding.inputPwd2SignUp.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(pwd2)){
            if (pwd.compareTo(pwd2) == 0){
                firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(requireContext(), "Sign up an account is successful", Toast.LENGTH_SHORT).show();
                                    currentUser = firebaseAuth.getCurrentUser();
                                    hideFragment();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(requireContext(), "Passwords are not the same!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(), "No Empty Fields are Allowed!", Toast.LENGTH_SHORT).show();
        }
    }
}