package com.ponominalu.nvv.ponominalutest.Adapters;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ponominalu.nvv.ponominalutest.Interfaces.IPonominalu;
import com.ponominalu.nvv.ponominalutest.R;
import com.ponominalu.nvv.ponominalutest.dao.Event;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ViewBinder {

    private final View rootView;
    private final ViewHolder viewHolder;
    private final Context context;

    private ViewBinder(View rootView, ViewHolder viewHolder, Context context) {
        this.rootView = rootView;
        this.viewHolder = viewHolder;
        this.context = context;
    }

    public static ViewBinder withHolder(ViewHolder viewHolder, Context context) {
        return new ViewBinder(null, viewHolder, context);
    }

    public static ViewBinder withRoot(View rootView, Context context) {
        return new ViewBinder(rootView, null, context);
    }

    public static ViewBinder withFragment(Fragment fragment) {
        return new ViewBinder(fragment.getView(), null, fragment.getActivity());
    }

    public Context getContext() {
        return context;
    }

    public View getRootView() {
        return viewHolder != null ? viewHolder.rootView : rootView;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T get(int viewId) {
        return viewHolder != null
                ? (T)viewHolder.get(viewId)
                : (T)rootView.findViewById(viewId);
    }

    /**
     * Will set the text of a TextView.
     *
     * @param viewId The view id.
     * @param value  The text to put in the text view.
     * @return The ViewHolder for chaining.
     */
    public ViewBinder setText(int viewId, String value) {
        TextView view = get(viewId);
        view.setText(value);
        return this;
    }


    /**
     * Will set the image of an ImageView from a drawable.
     *
     * @param viewId   The view id.
     * @param drawable The image drawable.
     * @return The ViewHolder for chaining.
     */
    public ViewBinder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = get(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /**
     * Add an action to set the image of an image view. Can be called multiple times.
     */
    public ViewBinder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = get(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }
}
