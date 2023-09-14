package com.appteam4.postella.ui;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appteam4.postella.R;
import com.appteam4.postella.dto.Review;

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ReviewViewHolder";
    private  int revNo = 1;
    private TextView usName;
    private TextView prdName;
    private TextView revContent;
    private TextView revDate;
    private RatingBar revStarRate;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        // 아이템 UI 얻기
        this.usName = itemView.findViewById(R.id.ans);
        this.prdName = itemView.findViewById(R.id.prd_name);
        this.revContent = itemView.findViewById(R.id.rev_content);
        this.revDate = itemView.findViewById(R.id.rev_date);
        this.revStarRate = itemView.findViewById(R.id.rev_start_rate);
    }

    public void setData(Review review) {
        this.revNo = review.getRevNo();
        this.usName.setText(review.getUsName());
        this.prdName.setText(review.getPrdName());
        this.revContent.setText(review.getRevContent());
        this.revDate.setText(review.getRevDate());
        this.revStarRate.setRating(review.getRating());
    }

}
