package com.example.calreminder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ToDoOfCalendarAdapter extends RecyclerView.Adapter<ToDoOfCalendarAdapter.ToDoOfCalendarHolder> implements ItemTouchHelperListener{
    //캘린더 다이얼로그를 만들 때 쓸 리사이클러뷰 어댑터
    //삭제 후 갱신이 즉각되지 않음 그렇다면 배열을 이용?
    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }
    private OnItemClickListener mListener = null;
    private View view;
    private List<Component> todos;
    private Context context;
    private String mDate;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public class ToDoOfCalendarHolder extends RecyclerView.ViewHolder {
        //캘린더 다이얼로그를 만들 때 리사이클러뷰에 넣을 뷰홀더
        TextView todoList;
        TextView time;
        TextView place;
        public ToDoOfCalendarHolder(@NonNull View itemView){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
            todoList = itemView.findViewById(R.id.todos);
            time = itemView.findViewById(R.id.time);
        }

    }
     ToDoOfCalendarAdapter(Context context, String date, List<Component> components){
        this.context = context;
        this.mDate = date;
        this.todos = components;
    }

    @NonNull
    @Override
    public ToDoOfCalendarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.todolist_item, parent, false);
        return new ToDoOfCalendarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoOfCalendarHolder holder, @SuppressLint("RecyclerView") int position){
        todos = CalreminderData.componentDataDao.getSelectedDateData(mDate);
        holder.todoList.setText(todos.get(position).text);
        String time = todos.get(position).time;
        if (!time.equals(""))
            holder.time.setText(todos.get(position).time);
        else
            holder.time.setText("하루 종일");
        holder.todoList.setBackgroundColor(todos.get(position).color);
        holder.itemView.setTag(new Boolean(false));

        view.findViewById(R.id.todolist_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalreminderData.componentDataDao.deleteComponent(todos.get(holder.getLayoutPosition()).Id);
                todos.remove(holder.getLayoutPosition());
                notifyItemRemoved(holder.getLayoutPosition());
                v.setEnabled(false);
            }
        });
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

    //왼쪽으로 스와이프 했을 때 나오는 오른쪽 버튼의 삭제 기능
    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder){
        CalreminderData.componentDataDao.deleteComponent(todos.get(position).Id);
        todos.remove(position);
        Toast.makeText(context.getApplicationContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
        notifyItemRemoved(position);
    }

    //구현해야 되는 함수 끝
    @Override
    public int getItemCount(){
        return todos.size();
    }
}

