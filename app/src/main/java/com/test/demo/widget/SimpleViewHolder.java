package com.test.demo.widget;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


/**
 * @author This Man
 * 版本：1.0
 * 创建日期：2020-12-26
 * 描述：
 */
public class SimpleViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public SimpleViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        this.mConvertView = itemView;
    }

    public static SimpleViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        SimpleViewHolder holder = new SimpleViewHolder(convertView);
        return holder;
    }

    public SimpleViewHolder setTextView(int id, CharSequence text) {
        TextView view = (TextView) getView(id);
        view.setText(text);
        return this;
    }


    public SimpleViewHolder setTextViewColor(int id, int color) {
        TextView view = (TextView) getView(id);
        view.setTextColor(color);
        return this;
    }

    public SimpleViewHolder setBackground(int id, Drawable drawable) {
        View view = getView(id);
        view.setBackground(drawable);
        return this;
    }

    public SimpleViewHolder setBackgroundResource(int id, int resource) {
        View view = getView(id);
        view.setBackgroundResource(resource);
        return this;
    }

    public SimpleViewHolder setOnClickListener(int id, View.OnClickListener l) {
        View view = getView(id);
        view.setOnClickListener(l);
        return this;
    }

    public SimpleViewHolder setImageResource(int id, int resource) {
        ImageView imageView = getImageView(id);
        imageView.setImageResource(resource);
        return this;
    }

    public SimpleViewHolder setImageDrawable(int id, Drawable drawable) {
        ImageView imageView = getImageView(id);
        imageView.setImageDrawable(drawable);
        return this;
    }

    public void setVisibility(int id, int visibility) {
        View view = getView(id);
        view.setVisibility(visibility);
    }

    public int getVisibility(int id) {
        View view = getView(id);
        return view.getVisibility();
    }

    public ImageView getImageView(int id) {
        return (ImageView) getView(id);
    }

    public final <T extends View> T getView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = mConvertView.findViewById(id);
            mViews.put(id, view);
        }
        T t = (T) view;
        return t;
    }


    View getConvertView() {
        return mConvertView;
    }

}
