package com.example.calreminder;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class ReminderList extends Fragment {
    private RecyclerView recyclerView;
    private AppDatabase db;
    //리마인더의 Fragment에 관한 Class
    public ReminderList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder_list, container, false);

        // + 버튼
        view.findViewById(R.id.listFragment_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ReminderActivity)getActivity()).onAddButtonClicked(view);
            }
        });

        // 리마인더 리스트 만들기
        List<Component> list = CalreminderData.componentDataDao.getAllComponent();

        recyclerView = view.findViewById(R.id.reminder_Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        reminderAdapter reminderAdapter = new reminderAdapter(list);
        recyclerView.setAdapter(reminderAdapter);

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

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                com.example.calreminder.reminderAdapter adapter = new reminderAdapter(searchList);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });

        // 테스트 버튼 구현, 나중에 제거할것
        Button testButton = view.findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ReminderActivity)getActivity()).onTestButtonClicked(v);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}