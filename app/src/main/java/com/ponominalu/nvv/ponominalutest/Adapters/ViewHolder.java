package com.ponominalu.nvv.ponominalutest.Adapters;

import android.util.SparseArray;
import android.view.View;


public class ViewHolder {
    public final View rootView;
    private final SparseArray<View> views;

    public ViewHolder(View rootView) {
        this.views = new SparseArray<View>();
        this.rootView = rootView;
    }

    public static ViewHolder from(View rootView) {
        ViewHolder holder = (ViewHolder) rootView.getTag();
        if (holder == null) {
            holder = new ViewHolder(rootView);
        }
        return holder;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T get(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = rootView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }
}
