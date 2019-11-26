package com.mara.dogsapp.util;

import android.content.Context;
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mara.dogsapp.R;

public class Util {
    public static void loadImage(ImageView imageView, String imageUrl,
                                 CircularProgressDrawable circularProgressDrawable) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(circularProgressDrawable)
                .error(null);

        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(imageUrl)
                .into(imageView);
    }

    public static CircularProgressDrawable getProgressDrawable(Context context) {
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.setCenterRadius(50f);
        progressDrawable.start();
        return progressDrawable;
    }
}
