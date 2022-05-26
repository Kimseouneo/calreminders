package com.example.calreminder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Holder extends RecyclerView.ViewHolder {
    TextView tx;
    public Holder(@NonNull View itemView){
        super(itemView);
        tx = itemView.findViewById(R.id.yesdw2);
    }
}
