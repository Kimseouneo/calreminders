package com.example.calreminder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToDoOfCalendarAdapter extends RecyclerView.Adapter<ToDoOfCalendarHolder> {
    //캘린더 다이얼로그를 만들 때 쓸 리사이클러뷰 어댑터
    ArrayList<Component> todos;
    Context context;
    public ToDoOfCalendarAdapter(Context context, ArrayList<Component> todos){
        this.context = context;
        this.todos = todos;
    }

    @NonNull
    @Override
    public ToDoOfCalendarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.todolist_calendar, parent, false);
        return new ToDoOfCalendarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoOfCalendarHolder holder, @SuppressLint("RecyclerView") int position){
        holder.todoList.setText(todos.get(position).text);
        holder.time.setText(todos.get(position).time);
        holder.place.setText(todos.get(position).place);
    }

    @Override
    public int getItemCount(){
        return todos.size();
    }
}
