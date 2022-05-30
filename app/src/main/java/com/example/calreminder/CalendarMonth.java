package com.example.calreminder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarMonth extends Fragment {
    //뷰페이저2에 사용할 페이지
    View v;
    Calendar check = Calendar.getInstance();
    int[] dayOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    ArrayList<String> b = new ArrayList<>();
    MonthOfListAdapter adapter;
    RecyclerView recyclerView;
    TextView monthText;
    int month;
    public CalendarMonth(int months){
        month = months;
        if(Integer.parseInt(CalendarFragment.years.getText().toString()) % 4 == 0){
            dayOfMonth[1] = 29;
        }
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
        adapter.notifyDataSetChanged();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(v==null) {
            CalendarFragment.plus.setEnabled(false);
            v = inflater.inflate(R.layout.fragment_calendar_month, container, false);
            monthText = (TextView) v.findViewById(R.id.months);
            monthText.setText(month+"월");
            }
        check.set(Integer.parseInt(CalendarFragment.years.getText().toString()), month-1, 1);
        for(int i = 1; i < check.get(Calendar.DAY_OF_WEEK); i++){
            b.add("");
        }
        for(int i = 1; i<= dayOfMonth[month-1]; i++){
            b.add(Integer.toString(i));
        }
        adapter = new MonthOfListAdapter(b, month);
        recyclerView = v.findViewById(R.id.monthList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
        recyclerView.setAdapter(adapter);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter.notifyDataSetChanged();
    }
}