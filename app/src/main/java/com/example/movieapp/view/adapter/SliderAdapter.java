package com.example.movieapp.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapp.R;
import com.example.movieapp.model.SliderItem;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private final ArrayList<SliderItem> sliderItemArrayList;
    private final ViewPager2 viewPager2;
    private final Context context;
    private final Runnable runnable = new Runnable() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void run() {
            sliderItemArrayList.addAll(sliderItemArrayList);
            notifyDataSetChanged();
        }
    };

//    Runnable là một interface trong Java, đại diện cho một đoạn mã có thể chạy trên một luồng riêng biệt.
//    Bạn đã tạo một đối tượng Runnable mới và triển khai phương thức run() của nó.

//    Runnable này được thiết kế để tạo hiệu ứng tự động lặp lại hoặc làm mới danh sách các mục trong ViewPager2.

//    Bằng cách nhân đôi các phần tử trong sliderItemList, danh sách các mục được mở rộng, có thể để tạo cảm giác trượt
//    vô hạn hoặc để duy trì sự chuyển động liên tục của các mục trong ViewPager2.

//    notifyDataSetChanged() được sử dụng để cập nhật lại giao diện người dùng sau khi danh sách được thay đổi.


    public SliderAdapter(Context context, ArrayList<SliderItem> sliderItemArrayList, ViewPager2 viewPager2) {
        this.context = context;
        this.sliderItemArrayList = sliderItemArrayList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_viewholder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        SliderItem sliderItem = sliderItemArrayList.get(position);
        holder.setSliderItemViewHolder(sliderItem);

        if (position == sliderItemArrayList.size() - 2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItemArrayList.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {
        private final ImageView slideImg;
        private final TextView slideAge;
        private final TextView slideGenre;
        private final TextView slideName;
        private final TextView slideTime;
        private final TextView slideYear;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            slideImg = itemView.findViewById(R.id.slideImg);
            slideAge = itemView.findViewById(R.id.slideAge);
            slideGenre = itemView.findViewById(R.id.slideGenre);
            slideName = itemView.findViewById(R.id.slideName);
            slideTime = itemView.findViewById(R.id.slideTime);
            slideYear = itemView.findViewById(R.id.slideYear);
        }

        void setSliderItemViewHolder(@NonNull SliderItem sliderItem) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(60));

            Glide.with(context)
                    .load(sliderItem.getImage())
                    .apply(requestOptions)
                    .into(slideImg);

            slideAge.setText(sliderItem.getAge());
            slideGenre.setText(sliderItem.getGenre());
            slideName.setText(sliderItem.getName());
            slideTime.setText(sliderItem.getTime());
            slideYear.setText(sliderItem.getYear());

//            RequestOptions là một lớp của thư viện Glide được sử dụng để cấu hình các tùy chọn cho việc tải và hiển thị hình ảnh.
//            áp dụng hai biến đổi (transform):
//            CenterCrop(): Cắt hình ảnh để phù hợp với chiều rộng và chiều cao của ImageView, giữ trung tâm của hình ảnh.
//            RoundedCorners(60): Làm tròn các góc của hình ảnh với bán kính 60 pixel.
        }
    }
}
