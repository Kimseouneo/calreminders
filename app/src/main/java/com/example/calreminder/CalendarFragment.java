package com.example.calreminder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

//디자인 완료
//다이알로그 스피너 구현
//뷰페이저의 슬라이드 움직임에 따라서 calendar_month값 증가
//캘린더 리스트 구현
public class CalendarFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        Spinner s = (Spinner) v.findViewById(R.id.calendar_year);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this.getContext(), R.array.years, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        setCalendarlist(v);
        return v;
    }

    private void setCalendarlist(View v) {
        ViewPager2 CalendarPager = v.findViewById(R.id.Calendar_list);

    }
}
