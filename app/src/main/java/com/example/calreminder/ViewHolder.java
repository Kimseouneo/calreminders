package com.example.calreminder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ViewHolder extends RecyclerView.ViewHolder{
    TextView textView;

    ViewHolder(View item){
        super(item);

        textView = item.findViewById(R.id.text);
    }
}