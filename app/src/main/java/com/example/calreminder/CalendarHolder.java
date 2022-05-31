package com.example.calreminder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarHolder extends RecyclerView.ViewHolder {
    //캘린더를 리사이클러뷰로 구현할 때 사용할 뷰홀더
    TextView day;
    RecyclerView schedule;
    public CalendarHolder(@NonNull View itemView){
        super(itemView);
        day = itemView.findViewById(R.id.days);
        schedule = itemView.findViewById(R.id.schedule);
    }

    public void setVisibility(int invisible) {
        schedule.setVisibility(invisible);
    }
}
