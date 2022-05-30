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

//더블클릭 하면 리사이클러뷰 등장하는 걸 아직 못시킴
//현재 onbindviewholder에서 어레이리스트를 선언하고 거기에 데이터를 저장하면 값이 저장된다는 사실을 확인했음
//이걸 이용하여 리사이클러뷰를 하나 더 만들 예정
public class MonthOfListAdapter extends RecyclerView.Adapter<CalendarHolder> {
    ArrayList<String> list;
    static View plusButton = null;
    View saveData = null;
    int month;
    int year;
    List<Component> componentArrayList;
    Calendar checkCalendar = Calendar.getInstance();
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
        View view = inflater.inflate(R.layout.calendar_list, parent, false);
        return new CalendarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarHolder holder, @SuppressLint("RecyclerView") int position) {
        ArrayList<String> toDo = new ArrayList<>();
        holder.day.setText(list.get(position));
        if(holder.day.getText().toString() == "")
            holder.setVisibility(2);
        Log.d("!!!!!!!!!!!!!!!!!!", "만들어짐");
        try {
            for (int i = 0; i < componentArrayList.size(); i++) {
                if (componentArrayList.get(i).date.equals(Integer.toString(year) + '/' + Integer.toString(month) + '/' + list.get(position))) {
                    //반복문 순회를 통해서 componentArrayList에 있는 date가 현재 날짜와 똑같으면 일정이라는 글자를 추가
                    if(holder.schedule.getText() == "") {
                        Log.d("!!!!!!!!!!!!!!!!!!", "실행된다");
                        holder.schedule.setText("일정");
                        toDo.add(componentArrayList.get(i).text);
                    }
                    else{
                        toDo.add(componentArrayList.get(i).text);
                    }
                }
            }
        }catch(Exception e){

        }
        try {
            if (position % 7 == 0)
                holder.day.setTextColor(Color.BLUE);
            if ((position + 1) % 7 == 0)
                holder.day.setTextColor(Color.RED);
            if (Integer.parseInt(list.get(position)) == checkCalendar.get(Calendar.DATE) && month == checkCalendar.get(Calendar.MONTH)+1 && year == checkCalendar.get(Calendar.YEAR))
                holder.day.setTextColor(Color.MAGENTA);
        }catch(NumberFormatException e){

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saveData == null){
                    saveData = v;
                    v.setBackgroundColor(Color.GREEN);
                    plusButton = v;
                    CalendarFragment.plus.setEnabled(true);
                }
                else{
                    saveData.setBackgroundColor(Color.WHITE);
                    v.setBackgroundColor(Color.GREEN);
                    saveData = v;
                    plusButton = v;
                }
                if(holder.schedule.getText().toString() == "일정"){
                    for(int i = 0; i < toDo.size(); i++) {
                        Log.d("!!!!!!!!!!!!!!!!!!", toDo.get(i));
                    }
                }
            }
            });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

}

