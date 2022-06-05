package com.example.calreminder;



import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CalendarListAdapter extends FragmentStateAdapter {
    //캘린더를 만들 때 사용하는 뷰페이저2와 연결할 FragmentStateAdapter
    public CalendarListAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        CalendarMonth calendarMonth = new CalendarMonth();
        Bundle args = new Bundle();
        args.putInt("month",position%12+1);
        calendarMonth.setArguments(args);
        return calendarMonth;
    }

    @Override
    public long getItemId(int position){
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public void refresh(){
        this.notifyDataSetChanged();
    }

}
