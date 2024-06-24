package com.example.movieapp.view;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapp.R;
import com.example.movieapp.databinding.ActivityFilmDetailBinding;
import com.example.movieapp.model.Film;
import com.example.movieapp.view.adapter.CastListAdapter;
import com.example.movieapp.view.adapter.GenreListAdapter;

import java.util.Objects;

import eightbitlab.com.blurview.RenderScriptBlur;

public class FilmDetailActivity extends AppCompatActivity {
    private ActivityFilmDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityFilmDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.filmDetailActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setFilmDetail();
    }

    @SuppressLint("SetTextI18n")
    private void setFilmDetail() {
        Film item = (Film) getIntent().getSerializableExtra("Film");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new GranularRoundedCorners(0, 0, 50, 50));
//        GranularRoundedCorners là một tính năng của thư viện Glide, dùng để áp dụng các góc bo
//        tròn riêng lẻ cho mỗi góc của hình ảnh.Điều này có nghĩa là bạn có thể thiết lập các giá
//        trị bo tròn khác nhau cho các góc trên cùng bên trái, trên cùng bên phải, dưới cùng bên
//        trái, và dưới cùng bên phải của hình ảnh.

        Glide.with(this).load(Objects.requireNonNull(item).getPoster()).apply(requestOptions).into(binding.filmDetailPoster);

        binding.filmDetailTitle.setText(item.getTitle());
        binding.filmDetailYear.setText(String.valueOf(item.getYear()));
        binding.filmDetailTime.setText("- " + item.getTime());
        binding.filmDetailIMDB.setText("IMDB: " + item.getImdb());
        binding.filmDetailDescription.setText(item.getDescription());

        binding.filmDetailWatchBtn.setOnClickListener(v -> {
            String ytbUrl = item.getTrailer().replace("https://www.youtube.com/watch?", "");
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + ytbUrl));
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getTrailer()));

            try {
                startActivity(appIntent);
            } catch (ActivityNotFoundException e) {
                startActivity(webIntent);
            }
        });

        binding.backHomeLauncherBtn.setOnClickListener(v -> finish());

        float radius = 10f; // Thiết lập bán kính làm mờ là 10 pixel.
        View decorView = getWindow().getDecorView(); // Lấy decorView của cửa sổ hiện tại. decorView là View gốc chứa toàn bộ nội dung của cửa sổ
        ViewGroup rootView = decorView.findViewById(android.R.id.content); // Lấy rootView, là ViewGroup chứa nội dung của ứng dụng (trong trường hợp này là FrameLayout chứa các Fragment).
        Drawable windowBg = decorView.getBackground(); // Lấy nền của decorView, thường là nền của cửa sổ.

        binding.detailLayer.setupWith(rootView, new RenderScriptBlur(this)).setFrameClearDrawable(windowBg).setBlurRadius(radius);
//        setupWith(rootView, new RenderScriptBlur(this)): Thiết lập RenderScriptBlur cho detailLayer với rootView làm gốc.
//        setFrameClearDrawable(windowBg): Sử dụng nền của cửa sổ làm nền để làm mờ.
//        setBlurRadius(radius): Thiết lập bán kính làm mờ.

        binding.detailLayer.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        binding.detailLayer.setClipToOutline(true);
//        setOutlineProvider(ViewOutlineProvider.BACKGROUND):Sử dụng outline của nền để xác định
//        hình dạng của detailLayer.
//        setClipToOutline(true):Giới hạn các nội dung con của detailLayer theo outline đã xác
//        định.Điều này đảm bảo rằng hiệu ứng làm mờ sẽ tuân theo các góc bo tròn.

        if (item.getGenre() != null){
            binding.filmDetailGenreView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false ));
            binding.filmDetailGenreView.setAdapter(new GenreListAdapter(this, item.getGenre()));
        }

        if (item.getCasts() != null){
            binding.filmDetailCastView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            binding.filmDetailCastView.setAdapter(new CastListAdapter(this, item.getCasts()));
        }
    }
}