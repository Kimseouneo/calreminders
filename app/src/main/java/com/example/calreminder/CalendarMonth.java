package com.example.calreminder;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarMonth extends Fragment {
    //뷰페이저2에 사용할 페이지
    View v;
    Calendar check = Calendar.getInstance();
    int[] dayOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    ArrayList<String> calendarMonthList = new ArrayList<>();
    MonthOfListAdapter adapter;
    RecyclerView recyclerView;
    TextView monthText;
    int month;
    int beforeMonthDays;
    int afterMonthDays = 1;
    public CalendarMonth() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int num = getArguments().getInt("number");
        }
    }
    @Override
    public void onPause(){
        super.onPause();
    }
    @Override
    public void onResume(){
        super.onResume();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.month = getArguments().getInt("month");
        if(Integer.parseInt(CalendarFragment.years.getText().toString()) % 4 == 0){
            dayOfMonth[1] = 29;
        }
        if(v==null) {
            v = inflater.inflate(R.layout.fragment_calendar_month, container, false);
            monthText = (TextView) v.findViewById(R.id.months);
            monthText.setText(month+"월");
            monthText.setTextSize(0, 150);
            if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT)
                monthText.setTextSize(0, 100);
            }
        int beforeMonth = month - 2;
        if(beforeMonth == -1)
            beforeMonth = 11;
        check.set(Integer.parseInt(CalendarFragment.years.getText().toString()), month-2, dayOfMonth[beforeMonth]);
        beforeMonthDays = check.get(Calendar.DAY_OF_WEEK);
        check.set(Integer.parseInt(CalendarFragment.years.getText().toString()), month-1, 1);
        for(int i = 1; i < check.get(Calendar.DAY_OF_WEEK); i++){
            calendarMonthList.add(dayOfMonth[beforeMonth] - beforeMonthDays + 1 +"");
            --beforeMonthDays;
        }
        for(int i = 1; i<= dayOfMonth[month-1]; i++)
            calendarMonthList.add(Integer.toString(i));
        if (calendarMonthList.size() <= 42){
            for(int i = calendarMonthList.size(); i < 42; i++){
                calendarMonthList.add(afterMonthDays+"");
                ++afterMonthDays;
            }
        }
        adapter = new MonthOfListAdapter(getActivity(), calendarMonthList, month, dayOfMonth, afterMonthDays);
        recyclerView = v.findViewById(R.id.monthList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        recyclerView.setAdapter(adapter);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}