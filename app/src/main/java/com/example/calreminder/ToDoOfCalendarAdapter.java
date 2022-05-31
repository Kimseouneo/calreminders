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

    //구현해야 되는 함수 시작
    @Override
    public boolean onItemMove(int from_position, int to_position) {
        return false;
    }

    //아이템 스와이프 기능(추가할 거 없으면 놔둬도 됨)
    @Override
    public void onItemSwipe(int position){
    }

    //오른쪽으로 스와이프 했을 때 나오는 왼쪽 버튼의 수정 기능(삭제 기능만 구현할 거면 안해도 됨)
    @Override
    public void onLeftClick(int position, RecyclerView.ViewHolder viewHolder) {

    }

    //왼쪽으로 스와이프 했을 때 나오는 오른쪽 버튼의 삭제 기능
    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder){
        CalreminderData.componentDataDao.deleteComponent(todos.get(position).Id);
        todos.remove(position);
        notifyItemRemoved(position);
    }

    //구현해야 되는 함수 끝
    @Override
    public int getItemCount(){
        return todos.size();
    }
}
