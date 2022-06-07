package com.example.calreminder;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarFragment extends Fragment {
    //음... 구현을 어떻게 하지?
    //캘린더를 생성하는 프래그먼트
    private static ViewPager2 CalendarPager;
    FragmentTransaction ft;
    Calendar c;
    FragmentStateAdapter set;
    View v;
    String name = "";
    FragmentTransaction transaction;
    LinearLayout yearsList;
    static TextView years;
    ImageButton yearMinus;
    ImageButton yearPlus;
    FloatingActionButton button;
    // 일단 position을 현재 달로 설정할까 그리고 오른쪽 스와이프 왼쪽 스와이프 이벤트를 받아서 처리할까?
    static String selectedDate;
    static int year;
    static Context Calendarcontext;
    public Boolean isButtonClicked = false;
    private FloatingActionButton actionButtonMore, actionButtonAdd, actionButtonCalendar;
    private Animation animationActionButtonOpen, animationActionButtonClose,
            animationActionButtonMoreOpen, animationActionButtonMoreClose;

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
        ft = getFragmentManager().beginTransaction();
        c = Calendar.getInstance();
        v = inflater.inflate(R.layout.fragment_calendar, container, false);
        Calendarcontext = getActivity();
        years = (TextView)v.findViewById(R.id.years);
        yearMinus = (ImageButton)v.findViewById(R.id.button_minus);
        yearPlus = (ImageButton) v.findViewById(R.id.button_plus);
        yearsList = (LinearLayout)v.findViewById(R.id.calendarFragment_yearlist);
        button = (FloatingActionButton)v.findViewById(R.id.calendarFragment_floatingActionButton);
        yearsList.setVisibility(View.VISIBLE);
        years.setVisibility(View.VISIBLE);
        yearMinus.setVisibility(View.VISIBLE);
        yearPlus.setVisibility(View.VISIBLE);
        if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){
            button.setVisibility(View.GONE);
            years.setVisibility(View.GONE);
            yearMinus.setVisibility(View.GONE);
            yearPlus.setVisibility(View.GONE);
            yearsList.setVisibility(View.GONE);
        }
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
        name = ((ReminderActivity)getActivity()).setPositions(CalendarPager.getCurrentItem()%12);
        if(name == "연도+"){
            year = year+1;
            years.setText(year + "");
            set.notifyDataSetChanged();
            name = "";
        }
        else if(name == "연도-"){
            year = year-1;
            years.setText(year+"");

            set.notifyDataSetChanged();
            name = "";
        }


        yearMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                years.setText(year-1+"");
                CalendarPager.setCurrentItem(CalendarPager.getCurrentItem()-12);
                set.notifyDataSetChanged();
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


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 세로 모드일경우 실행

            // FloatingActionButton 애니메이션
            animationActionButtonOpen = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.action_button_open_animation);
            animationActionButtonClose = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.action_button_close_animation);
            animationActionButtonMoreOpen = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.action_button_rotate_start_animation);
            animationActionButtonMoreClose = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.action_button_rotate_end_animation);
            actionButtonMore = v.findViewById(R.id.calendarFragment_floatingActionButton);
            actionButtonAdd = v.findViewById(R.id.calendarFragment_floatingActionButton_add);
            actionButtonCalendar = v.findViewById(R.id.calendarFragment_floatingActionButton_reminder);

            // more 버튼
            actionButtonMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anim();
                }
            });
            // + 버튼
            actionButtonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    anim();
                    ((ReminderActivity) getActivity()).onPlusClicked(view,"");
                }
            });
            // 리마인더 버튼
            actionButtonCalendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anim();
                    FragmentManager fragmentManager = ((ReminderActivity)getActivity()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().remove(CalendarFragment.this).commit();
                    fragmentManager.popBackStack();
                }
            });
        }

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



    public void anim() {
        if (!isButtonClicked) {
            actionButtonMore.startAnimation(animationActionButtonMoreOpen);
            actionButtonAdd.startAnimation(animationActionButtonOpen);
            actionButtonCalendar.startAnimation(animationActionButtonOpen);
            actionButtonAdd.setClickable(true);
            actionButtonCalendar.setClickable(true);
            isButtonClicked = true;
        } else {
            actionButtonMore.startAnimation(animationActionButtonMoreClose);
            actionButtonAdd.startAnimation(animationActionButtonClose);
            actionButtonCalendar.startAnimation(animationActionButtonClose);
            actionButtonAdd.setClickable(false);
            actionButtonCalendar.setClickable(false);
            isButtonClicked = false;
        }
    }

}