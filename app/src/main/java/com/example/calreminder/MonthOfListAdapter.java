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
import java.util.Calendar;
import java.util.List;


public class MonthOfListAdapter extends RecyclerView.Adapter<CalendarHolder> {
    ArrayList<String> list;
    View saveData = null;
    int month;
    int year;
    List<Component> componentArrayList;
    Calendar check = Calendar.getInstance();
    public MonthOfListAdapter(ArrayList<String> list, int month){
        this.list = list;
        this.month = month;
        this.year = CalendarFragment.year;
        componentArrayList = CalreminderData.componentDataDao.getHasDateComponent();
    }
    @NonNull
    @Override
    public CalendarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new CalendarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.day.setText(list.get(position));
        if(holder.day.getText().toString() == "")
            holder.setVisibility(2);
        Log.d("!!!!!!!!!!!!!!!!!!", "만들어짐");
        try {
            for (int i = 0; i < componentArrayList.size(); i++) {
                if (componentArrayList.get(i).date.equals(Integer.toString(year) + '/' + Integer.toString(month) + '/' + list.get(position))) {
                    //반복문 순회를 통해서 componentArrayList에 있는 date가 현재 날짜와 똑같으면 일정이라는 글자를 추가
                    Log.d("!!!!!!!!!!!!!!!!!!", "실행된다");
                    holder.schedule.setText("일정");
                    break;
                }
            }
        }catch(Exception e){

        }
        try {
            if (position % 7 == 0)
                holder.day.setTextColor(Color.BLUE);
            if ((position + 1) % 7 == 0)
                holder.day.setTextColor(Color.RED);
            if (Integer.parseInt(list.get(position)) == check.get(Calendar.DATE) && month == check.get(Calendar.MONTH)+1 && year == check.get(Calendar.YEAR))
                holder.day.setTextColor(Color.MAGENTA);
        }catch(NumberFormatException e){

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long btnPressTime = 0;
                if (System.currentTimeMillis() > btnPressTime + 1000) {
                    btnPressTime = System.currentTimeMillis();
                    if (saveData == null) {
                        saveData = v;
                        v.setBackgroundColor(Color.GREEN);
                    } else {
                        saveData.setBackgroundColor(Color.WHITE);
                        v.setBackgroundColor(Color.GREEN);
                        saveData = v;
                    }
                    Log.d("단일 클릭되었다.", holder.day.getText().toString());
                }
                if (System.currentTimeMillis() <= btnPressTime + 1000) {
                    Log.d("더블 클릭되었다.", holder.schedule.getText().toString());
                }
            }
            });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}

