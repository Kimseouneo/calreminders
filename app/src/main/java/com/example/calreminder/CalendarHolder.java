package com.example.calreminder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class CalendarHolder extends RecyclerView.ViewHolder {
    //캘린더를 리사이클러뷰로 구현할 때 사용할 뷰홀더
    TextView day;
    TextView schedule1;
    TextView schedule2;
    TextView schedule3;
    public CalendarHolder(@NonNull View itemView){
        super(itemView);
        day = itemView.findViewById(R.id.days);
        schedule1 = itemView.findViewById(R.id.schedule1);
        schedule1.setVisibility(View.INVISIBLE);
        schedule2 = itemView.findViewById(R.id.schedule2);
        schedule2.setVisibility(View.INVISIBLE);
        schedule3 = itemView.findViewById(R.id.schedule3);
        schedule3.setVisibility(View.INVISIBLE);
    }

}
