package com.example.calreminder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarHolder extends RecyclerView.ViewHolder {
    TextView day;
    TextView schedule;
    public CalendarHolder(@NonNull View itemView){
        super(itemView);
        day = itemView.findViewById(R.id.days);
        schedule = itemView.findViewById(R.id.schedule);
    }
}
