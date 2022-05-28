package com.example.calreminder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Holder extends RecyclerView.ViewHolder {
    TextView day;
    TextView schedule;
    public Holder(@NonNull View itemView){
        super(itemView);
        day = itemView.findViewById(R.id.days);
        schedule = itemView.findViewById(R.id.schedule);
    }
}
