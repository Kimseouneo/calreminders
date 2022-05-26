package com.example.calreminder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
//그리드뷰 어댑터
/*public class MonthOfListAdapter extends BaseAdapter {
    Context context;
    int lastDay;
    int dayOfWeek;//1일의 요일을 받기 위한 변수
    public MonthOfListAdapter(){
    }
    @Override
    public int getCount() {
        return MainActivity.dayslist.size();
    }

    @Override
    public String getItem(int i) {
        return MainActivity.dayslist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_list, viewGroup, false);
        }
        //달력 칸 비우기
        TextView text = view.findViewById(R.id.yesdw2);
        Thread thread = new Thread(){
            public void run(){
                if(i<dayOfWeek){
                    text.setTypeface(null, Typeface.NORMAL);
                    text.setText("");
                }
                //현재 달력을 출력하는 방법
                else if(i<lastDay+dayOfWeek){
                    text.setText(MainActivity.dayslist.get(i-dayOfWeek));
                }
                else
                    text.setText("");
            }
        };
        thread.start();

        return view;
    }
}*/


public class MonthOfListAdapter extends RecyclerView.Adapter<Holder> {
    ArrayList<String> list;
    public MonthOfListAdapter(ArrayList<String> list){
        this.list = list;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        holder.tx.setText(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    v.setBackgroundColor(Color.GRAY);
                    Thread.sleep(10);
                } catch (InterruptedException e) {

                }
                Log.d("클릳되었다.", list.get(position));
            }
            });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}

