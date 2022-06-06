package com.example.calreminder;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Calendar;

public class CalendarFragment extends Fragment {
    //캘린더를 생성하는 프래그먼트
    private static ViewPager2 CalendarPager;
    Calendar c;
    FragmentStateAdapter set;
    View v;
    FragmentTransaction transaction;
    static TextView years;
    Button yearMinus;
    Button yearPlus;
    Button check;
    static String selectedDate;
    static Button plus;
    static int year;
    static Context Calendarcontext;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        if(MonthOfListAdapter.plusButton != null)
            plus.setEnabled(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        c = Calendar.getInstance();
        v = inflater.inflate(R.layout.fragment_calendar, container, false);
        Calendarcontext = getActivity();
        years = (TextView)v.findViewById(R.id.years);
        yearMinus = (Button)v.findViewById(R.id.button_minus);
        yearPlus = (Button)v.findViewById(R.id.button_plus);
        check = (Button)v.findViewById(R.id.check);
        plus = (Button)v.findViewById(R.id.calendarFragment_button_plus);
        plus.setEnabled(false);
        CalendarPager = v.findViewById(R.id.Calendar_list);
        set = new CalendarListAdapter(getActivity());
        Button button = (Button) v.findViewById(R.id.calendarFragment_button_plus);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ReminderActivity)getActivity()).onPlusClicked(v,selectedDate);
            }
        });

        Thread thread = new Thread(){
            public void run(){
                try {
                    CalendarPager.setAdapter(set);
                    CalendarPager.setOffscreenPageLimit(1000);
                }catch(IllegalStateException e){

                }
            }
        };
        CalendarPager.post(new Runnable() {
            @Override
            public void run() {
                CalendarPager.setCurrentItem(c.get(Calendar.MONTH)+1200, false);
            }
        });
        year = c.get(Calendar.YEAR);
        years.setText(year + "");

        yearMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                years.setText(year-1+"");
                CalendarPager.setCurrentItem(CalendarPager.getCurrentItem()-12);
                year = year-1;
            }
        });
        yearPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                years.setText(year+1+"");
                CalendarPager.setCurrentItem(CalendarPager.getCurrentItem()+12);
                year = year+1;
            }
        });
        thread.start();
        return v;
    }
    //리사이클러뷰 내부에서 페이지를 넘길 수 있게 메소드를 구현
    static void leftPageMove(){
        CalendarPager.setCurrentItem(CalendarPager.getCurrentItem()-1);
    }

    static void rightPageMove(){
        CalendarPager.setCurrentItem(CalendarPager.getCurrentItem()+1);
    }
}