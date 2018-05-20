package com.dubizzle.app.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dubizzle.app.R;

public class ImageUtils {

    /**
     * Loads images in imageviews
     * @param context
     * @param iv imageview
     * @param imgUrl the url to load
     */
    public static void loadImage(final Context context, final ImageView iv, String imgUrl) {
        String url = AppConsts.IMAGE_BASE_URL + imgUrl;
        try {
            Glide.with(context).load(url).placeholder(new ColorDrawable(ContextCompat.getColor(context, R.color.colorPrimary))).skipMemoryCache(true).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}