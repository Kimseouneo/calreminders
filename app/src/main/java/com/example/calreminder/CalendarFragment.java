package com.example.calreminder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Calendar;

public class CalendarFragment extends Fragment {
    Calendar c;
    FragmentStateAdapter set;
    View v;
    static TextView years;
    Button yearMinus;
    Button yearPlus;
    ViewPager2 CalendarPager;
    static int year;
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


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        c = Calendar.getInstance();
        v = inflater.inflate(R.layout.fragment_calendar, container, false);
        years = (TextView)v.findViewById(R.id.years);
        yearMinus = (Button)v.findViewById(R.id.button_minus);
        yearPlus = (Button)v.findViewById(R.id.button_plus);
        CalendarPager = v.findViewById(R.id.Calendar_list);
        set = new CalendarListAdapter(getActivity());
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

}