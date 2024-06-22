package com.example.movieapp.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieapp.databinding.ActivityHomeLauncherBinding;
import com.example.movieapp.model.SliderItem;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private List<SliderItem> sliderItemList;
    private ViewPager2 viewPager2;
    private Context context;
    private Runnable runnable = new Runnable() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void run() {
            sliderItemList.addAll(sliderItemList);
            notifyDataSetChanged();
        }
    };

//    Runnable là một interface trong Java, đại diện cho một đoạn mã có thể chạy trên một luồng riêng biệt.
//    Bạn đã tạo một đối tượng Runnable mới và triển khai phương thức run() của nó.

//    Runnable này được thiết kế để tạo hiệu ứng tự động lặp lại hoặc làm mới danh sách các mục trong ViewPager2.

//    Bằng cách nhân đôi các phần tử trong sliderItemList, danh sách các mục được mở rộng, có thể để tạo cảm giác trượt
//    vô hạn hoặc để duy trì sự chuyển động liên tục của các mục trong ViewPager2.

//    notifyDataSetChanged() được sử dụng để cập nhật lại giao diện người dùng sau khi danh sách được thay đổi.


    public SliderAdapter(Context context, List<SliderItem> sliderItemList) {
        this.context = context;
        this.sliderItemList = sliderItemList;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder{
        private ActivityHomeLauncherBinding binding;

        public SliderViewHolder(@NonNull ActivityHomeLauncherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
