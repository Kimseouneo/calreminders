package com.example.calreminder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoOfCalendarHolder extends RecyclerView.ViewHolder {
    //캘린더 다이얼로그를 만들 때 리사이클러뷰에 넣을 뷰홀더
    TextView todoList;
    TextView time;
    TextView place;
    public ToDoOfCalendarHolder(@NonNull View itemView){
        super(itemView);
        todoList = itemView.findViewById(R.id.todos);
        time = itemView.findViewById(R.id.time);
        place = itemView.findViewById(R.id.place);
    }

}
