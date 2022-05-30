package com.example.calreminder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoOfCalendarHolder extends RecyclerView.ViewHolder {
    TextView todolist;
    public ToDoOfCalendarHolder(@NonNull View itemView){
        super(itemView);
        todolist = itemView.findViewById(R.id.todos);
    }

}
