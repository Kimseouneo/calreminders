package com.example.calreminder;

import android.content.res.Configuration;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

public class ReminderList extends Fragment {
    private RecyclerView recyclerView;
    public static reminderAdapter reminderAdapter;
    public Boolean isButtonClicked = false;
    private ItemTouchHelperCallback touchHelperCallback;
    private FloatingActionButton actionButtonMore, actionButtonAdd, actionButtonCalendar;
    private Animation animationActionButtonOpen, animationActionButtonClose,
            animationActionButtonMoreOpen, animationActionButtonMoreClose;
    private ItemTouchHelper helper;
    //리마인더의 Fragment에 관한 Class
    public ReminderList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setUpRecyclerView(){
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration(){
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state){
               helper.onDraw(c,parent,state);}
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder_list, container, false);

        // FloatingActionButton 애니메이션
        animationActionButtonOpen = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.action_button_open_animation);
        animationActionButtonClose = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.action_button_close_animation);
        animationActionButtonMoreOpen = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.action_button_rotate_start_animation);
        animationActionButtonMoreClose = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.action_button_rotate_end_animation);
        actionButtonMore = view.findViewById(R.id.listFragment_floatingActionButton);
        actionButtonAdd = view.findViewById(R.id.listFragment_floatingActionButton_add);
        actionButtonCalendar = view.findViewById(R.id.listFragment_floatingActionButton_calendar);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 세로 모드일경우 실행
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
                    ((ReminderActivity) getActivity()).onAddButtonClicked(view);
                }
            });
            // 캘린더 버튼
            actionButtonCalendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anim();
                    ((ReminderActivity) getActivity()).onCalendarButtonClicked(v);
                }
            });
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //가로 모드일 경우 실행
            // more 버튼
            actionButtonMore.setImageResource(R.drawable.ic_action_add);
            actionButtonMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ReminderActivity) getActivity()).onAddButtonClickedLand(view);
                }
            });
        }

        else {
            Log.d("EXCEPTION", "Can't get orientation");
        }

        // 리마인더 리스트 만들기
        List<Component> list = CalreminderData.componentDataDao.getAllComponent();
        Collections.reverse(list);

        recyclerView = view.findViewById(R.id.reminder_Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setComponent(list);

        // 검색기능
        SearchView searchView = (SearchView) view.findViewById(R.id.listFragment_SearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Component> searchList = CalreminderData.componentDataDao.getSearchedData(newText);
                Collections.reverse(searchList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                com.example.calreminder.reminderAdapter adapter = new reminderAdapter(searchList);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });

        return view;
    }

    //애니메이션 동작 함수
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

    public void setComponent(List<Component> list) {

        reminderAdapter = new reminderAdapter(list);
        recyclerView.setAdapter(reminderAdapter);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            touchHelperCallback = new ItemTouchHelperCallback(reminderAdapter, getResources().getDisplayMetrics().widthPixels / 4f);
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            touchHelperCallback = new ItemTouchHelperCallback(reminderAdapter, getResources().getDisplayMetrics().widthPixels / 8f);
            Log.d("LANDSCAPE", "SET 8f");
        }
        helper = new ItemTouchHelper(touchHelperCallback);
        helper.attachToRecyclerView(recyclerView);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touchHelperCallback.removePreviousClamp(recyclerView);
                return false;
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
    }

}