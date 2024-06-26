package com.example.movieapp.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieapp.R;
import com.example.movieapp.databinding.ActivityHomeLauncherBinding;
import com.example.movieapp.model.SliderItem;
import com.example.movieapp.view.adapter.FilmListAdapter;
import com.example.movieapp.view.adapter.SliderAdapter;
import com.example.movieapp.viewmodel.FilmViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HomeLauncherActivity extends AppCompatActivity {
    private ActivityHomeLauncherBinding binding;
    private FilmViewModel viewModel;
    private SIgnUpUserNameFragment sIgnUpUserNameFragment;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    private final Handler sliderHandler = new Handler();
    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            binding.bannersImg.setCurrentItem(binding.bannersImg.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityHomeLauncherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.homeLauncher), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(FilmViewModel.class);
        sIgnUpUserNameFragment = new SIgnUpUserNameFragment();

        authStateListener = firebaseAuth -> {
            currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                if (currentUser.getDisplayName() == null) {
                    hideHomeLauncherScreen();
                    showFragment(sIgnUpUserNameFragment);
                } else {
                    updateUserNameAndEmail();
                    showHomeLauncherScreen();
                }
                getSliderBannersFromFirebase();
                getTopMoviesFromFirebase();
                getUpComingFromFirebase();
            }
        };

        getSupportFragmentManager().addOnBackStackChangedListener(this::handleBackStackChanged);
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

    private void hideHomeLauncherScreen() {
        binding.scrollView3.setVisibility(View.GONE);
        binding.chipNavigationBar.setVisibility(View.GONE);
    }

    private void showHomeLauncherScreen() {
        binding.scrollView3.setVisibility(View.VISIBLE);
        binding.chipNavigationBar.setVisibility(View.VISIBLE);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout2, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void updateUserNameAndEmail() {
        String userName = currentUser.getDisplayName();
        String userEmail = currentUser.getEmail();

        if (userName != null && userEmail != null) {
            binding.userName.setText(userName);
            binding.userEmail.setText(userEmail);
        }
    }

    private void handleBackStackChanged() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 0) {
            showHomeLauncherScreen();
        } else {
            hideHomeLauncherScreen();
        }
    }

    private void getSliderBannersFromFirebase() {
        binding.bannersProgressBar.setVisibility(View.VISIBLE);
        viewModel.getSliderItems().observe(HomeLauncherActivity.this, sliderItems -> {
            if (sliderItems != null) {
                setBannersTransform(sliderItems);
                binding.bannersProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setBannersTransform(ArrayList<SliderItem> sliderItemArrayList) {
        binding.bannersImg.setAdapter(new SliderAdapter(binding.bannersImg.getContext(), sliderItemArrayList, binding.bannersImg));
        binding.bannersImg.setClipToPadding(false);
        binding.bannersImg.setClipChildren(false);
        binding.bannersImg.setOffscreenPageLimit(3);
        binding.bannersImg.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

//        setClipToPadding(false):Thiết lập để ViewPager2 không cắt các view con theo padding của
//        nó.Điều này giúp các mục hiển thị bên ngoài khu vực chính của ViewPager2 không bị cắt.

//        setClipChildren(false): Thiết lập để ViewPager2 không cắt các view con của nó. Tương tự như trên,
//        điều này giúp các mục hiển thị bên ngoài khu vực chính của ViewPager2 không bị cắt.

//        setOffscreenPageLimit(3): Thiết lập số lượng trang sẽ được lưu trong bộ nhớ ngoài màn hình.
//        Điều này giúp giảm độ trễ khi người dùng cuộn qua các trang, vì các trang gần kề đã được tải sẵn.

//        setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER): Thiết lập chế độ cuộn để không hiển thị hiệu ứng cuộn quá (overscroll)
//        khi kéo đến đầu hoặc cuối danh sách. Điều này tạo ra trải nghiệm mượt mà hơn cho người dùng.

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

//        PageTransformer để tạo hiệu ứng chuyển đổi trang và xử lý sự kiện thay đổi trang. Dưới đây là giải thích chi tiết từng phần của đoạn mã:

//        CompositePageTransformer cho phép bạn kết hợp nhiều PageTransformer để tạo các hiệu ứng chuyển đổi trang phức tạp hơn cho ViewPager2.

//        MarginPageTransformer thêm khoảng cách giữa các trang.

//        ViewPager2.PageTransformer tùy chỉnh này tạo hiệu ứng thu nhỏ các trang ở hai bên và phóng to trang ở giữa.
//        position là vị trí của trang hiện tại so với trang giữa (trang ở trung tâm).
//        r = 1 - Math.abs(position) tính toán tỷ lệ thu nhỏ/phóng to.
//        page.setScaleY(0.85f + r * 0.15f) điều chỉnh tỷ lệ Y của trang, với các trang ở giữa được phóng to hơn (1.0) và các trang bên cạnh được thu nhỏ hơn (0.85).

        binding.bannersImg.setPageTransformer(compositePageTransformer);
        binding.bannersImg.setCurrentItem(1);
        binding.bannersImg.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
            }
        });

//        Gán một PageTransformer cho ViewPager2 để tạo hiệu ứng chuyển đổi trang khi người dùng cuộn.
//        compositePageTransformer đã được thiết lập với hai PageTransformer:
//        MarginPageTransformer để tạo khoảng cách giữa các trang.
//        Một PageTransformer tùy chỉnh để tạo hiệu ứng thu nhỏ/phóng to các trang.

//        Đặt trang hiện tại của ViewPager2 là trang thứ 2 (index 1) khi ViewPager2 khởi tạo.
//        Việc này đảm bảo rằng khi ViewPager2 xuất hiện, nó sẽ hiển thị trang thứ 2 (index 1) thay vì trang đầu tiên (index 0).
//        Điều này có thể hữu ích trong các tình huống cụ thể, ví dụ như khi bạn muốn trang đầu tiên để lại khoảng trống cho các trang khác hiển thị ở hai bên.

//        Lắng nghe sự kiện thay đổi trang trong ViewPager2
//        registerOnPageChangeCallback thêm một callback để lắng nghe sự kiện thay đổi trang.
//        onPageSelected( int position)được gọi khi một trang mới được chọn.
//        sliderHandler.removeCallbacks(sliderRunnable):
//        sliderHandler:
//        Có thể là một Handler được sử dụng để quản lý các hành động liên quan đến việc thay đổi
//        trang tự động.
//        sliderRunnable:Có thể là một Runnable được sử dụng để thực hiện việc thay đổi
//        trang tự động sau một khoảng thời gian nhất định.
//        removeCallbacks(sliderRunnable):Loại bỏ bất kỳ callback nào của sliderRunnable đã được
//        lên lịch trước đó.Điều này có nghĩa là khi người dùng chọn một trang mới thủ công, việc
//        thay đổi trang tự động(nếu có) sẽ bị dừng lại.
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);

//        super.onPause():Gọi phương thức onPause () của lớp cha để đảm bảo rằng bất kỳ hành động nào
//        cần được thực hiện trong lớp cha cũng sẽ được thực hiện.

//        sliderHandler.removeCallbacks(sliderRunnable):Loại bỏ bất kỳ callback nào đã được lên lịch
//        của sliderRunnable từ sliderHandler.Điều này dừng việc tự động cuộn khi Activity không còn ở
//        trạng thái hoạt động.
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 2000);

//        super.onResume():Gọi phương thức onResume () của lớp cha để đảm bảo rằng bất kỳ hành
//        động nào cần được thực hiện trong lớp cha cũng sẽ được thực hiện.
//
//        sliderHandler.postDelayed(sliderRunnable, 2000):Lên lịch cho sliderRunnable để được
//        thực hiện sau 2000 milliseconds(2giây).Điều này khởi động lại việc tự động cuộn khi Activity
//        trở lại trạng thái hoạt động.
    }

    private void getTopMoviesFromFirebase() {
        binding.topMoviesProgressBar.setVisibility(View.VISIBLE);
        viewModel.getTopMovies().observe(HomeLauncherActivity.this, films -> {
            binding.topMoviesProgressBar.setVisibility(View.GONE);
            if (films != null) {
                binding.topMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(HomeLauncherActivity.this, LinearLayoutManager.HORIZONTAL, false));
                binding.topMoviesRecyclerView.setAdapter(new FilmListAdapter(HomeLauncherActivity.this, films));
            }
        });
    }

    private void getUpComingFromFirebase() {
        binding.upComingProgressBar.setVisibility(View.VISIBLE);
        viewModel.getUpComing().observe(HomeLauncherActivity.this, films -> {
            binding.upComingProgressBar.setVisibility(View.GONE);
            if (films != null) {
                binding.upComingRecyclerView.setLayoutManager(new LinearLayoutManager(HomeLauncherActivity.this, LinearLayoutManager.HORIZONTAL, false));
                binding.upComingRecyclerView.setAdapter(new FilmListAdapter(HomeLauncherActivity.this, films));
            }
        });
    }
}