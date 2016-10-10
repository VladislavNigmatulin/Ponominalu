package com.ponominalu.nvv.ponominalutest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ponominalu.nvv.ponominalutest.dao.Category;
import com.ponominalu.nvv.ponominalutest.dao.Event;

import java.util.List;


public class EasyArrayAdapter<T> extends ArrayAdapter<T> {

    private final int mResourceId;

    public EasyArrayAdapter(Context context, int resource) {
        super(context, resource);
        mResourceId = resource;
    }

    public EasyArrayAdapter(Context context, int resource, T[] objects) {
        super(context, resource, objects);
        mResourceId = resource;
    }

    public EasyArrayAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, (List<T>) objects);
        mResourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T item = getItem(position);
        Context context = getContext();
        View v = convertView == null ? createView(context, item, parent) : convertView;
        ViewHolder holder = ViewHolder.from(v);
        bindView(holder, ViewBinder.withHolder(holder, context), item, position);
        return v;
    }

    protected View createView(Context context, T item, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(mResourceId, null);
    }

    protected void bindView(ViewHolder holder, ViewBinder binder, T item, int position) {
    }
}
