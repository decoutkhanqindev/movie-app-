package com.example.movieapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.movieapp.R;
import com.example.movieapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();

        binding.moveToSignInFragment.setOnClickListener(v -> showFragment(signInFragment));

        binding.moveToSignUpFragment.setOnClickListener(v -> showFragment(signUpFragment));

        getSupportFragmentManager().addOnBackStackChangedListener(this::handleBackStackChanged);

        // Mã này trong context của MainActivity của bạn được sử dụng để điều khiển việc hiển thị và ẩn các thành phần giao diện
        // như ScrollView dựa trên trạng thái của back stack của FragmentManager. Khi bạn thực hiện việc add, replace fragment và commit vào back stack,
        // nó sẽ tự động ẩn ScrollView khi có fragment được hiển thị và hiển thị lại ScrollView khi back stack trống (không có fragment nào được hiển thị).

        authStateListener = firebaseAuth -> {
            currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                binding.linearLayoutContainer.setVisibility(View.GONE);
                binding.moveToHomeLauncherBtn.setVisibility(View.VISIBLE);
                moveToHomeLauncherActivity();
            } else {
                binding.linearLayoutContainer.setVisibility(View.VISIBLE);
                binding.moveToHomeLauncherBtn.setVisibility(View.GONE);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    private void hideSignInAndUpBtnLayout() {
        binding.moveToSignInFragment.setVisibility(View.GONE);
        binding.moveToSignUpFragment.setVisibility(View.GONE);
    }

    private void showSignInAndUpBtnLayout() {
        binding.moveToSignInFragment.setVisibility(View.VISIBLE);
        binding.moveToSignUpFragment.setVisibility(View.VISIBLE);
    }

    private void showFragment(Fragment fragment) {
        hideSignInAndUpBtnLayout();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

//        Back Stack:Trong Android, FragmentManager duy trì một ngăn xếp gọi là back stack.Back stack
//        này lưu trữ các transaction của fragment mà bạn thực hiện, cho phép người dùng quay lại từng
//        trạng thái của ứng dụng bằng việc nhấn nút back của thiết bị.

//        Transaction:Mỗi lần bạn thực hiện một transaction(thêm, thay thế hoặc xóa fragment và commit),
//        bạn có thể quyết định có thêm transaction đó vào back stack hay không.

//        addToBackStack(null):Phương thức này được sử dụng để thêm transaction hiện tại vào
//        back stack mà không cần một tên cụ thể(null được truyền vào thay vì một chuỗi tên).Khi
//        bạn thực hiện điều này, transaction sẽ được thêm vào back stack và người dùng có thể quay
//        lại trạng thái trước đó bằng cách nhấn nút back của thiết bị.
    }

    private void handleBackStackChanged() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 0) {
            showSignInAndUpBtnLayout();
        } else {
            hideSignInAndUpBtnLayout();
        }

//        getBackStackEntryCount():Phương thức này trả về số lượng entry (transaction) hiện tại trong
//        back stack của FragmentManager.Nếu getBackStackEntryCount () trả về 0, có nghĩa là không có
//        fragment nào đang được hiển thị từ back stack.
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }

//        Phương thức onBackPressed() trong MainActivity của bạn được sử dụng để xử lý sự kiện khi người dùng nhấn nút back trên thiết bị.

//        popBackStack():Phương thức này được gọi để xóa transaction hiện tại ra khỏi back stack
//        và quay lại fragment trước đó.Nếu không có fragment nào trong back stack
//        (getBackStackEntryCount() bằng 0),thì phương thức super.onBackPressed() sẽ được gọi để
//        hoàn thành hành động back mặc định(thoát khỏi ứng dụng hoặc activity hiện tại).
    }

    private void moveToHomeLauncherActivity() {
        binding.moveToHomeLauncherBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HomeLauncherActivity.class);
            startActivity(intent);
        });
    }
}