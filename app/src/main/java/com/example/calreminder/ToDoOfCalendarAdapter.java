package com.example.calreminder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToDoOfCalendarAdapter extends RecyclerView.Adapter<ToDoOfCalendarHolder> implements ItemTouchHelperListener{
    //캘린더 다이얼로그를 만들 때 쓸 리사이클러뷰 어댑터
    //삭제 후 갱신이 즉각되지 않음 그렇다면 배열을 이용?
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
        View view = inflater.inflate(R.layout.todolist_item, parent, false);
        return new ToDoOfCalendarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoOfCalendarHolder holder, @SuppressLint("RecyclerView") int position){
        holder.todoList.setText(todos.get(position).text);
        holder.time.setText(todos.get(position).time);
        holder.place.setText(todos.get(position).place);
        holder.todoList.setBackgroundColor(todos.get(position).color);
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        return false;
    }

    @Override
    public void onItemSwipe(int position){
        //데이터베이스 데이터도 제거해야 함
    }

    @Override
    public void onLeftClick(int position, RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder){
        CalreminderData.componentDataDao.deleteComponent(todos.get(position).Id);
        todos.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount(){
        return todos.size();
    }
}
