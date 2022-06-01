package com.example.calreminder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ViewHolder extends RecyclerView.ViewHolder{
    TextView textView;
    TextView Time;

    ViewHolder(View item){
        super(item);

        textView = item.findViewById(R.id.text);
        Time = item.findViewById(R.id.Time);
    }
}