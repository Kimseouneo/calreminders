package com.example.calreminder;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ReminderList extends Fragment {
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

        //리마인더 리스트 만들기
        LinearLayout listLayout = (LinearLayout) view.findViewById(R.id.listFragment_listLayout);
        for (ReminderComponentView componentView : CalreminderData.components){
            listLayout.addView((Button)componentView);
        }

        return view;
    }
}