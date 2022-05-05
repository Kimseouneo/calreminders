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

import java.util.Map;

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
        Map<String, ?> mp = CalreminderData.data.getAll();
        LinearLayout.LayoutParams layoutLayoutParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Log.d("!!!!!!!!!!!!!!!!!!!!!!!",CalreminderData.data.toString());
        for(Map.Entry<String,?> entry : mp.entrySet()) {
            Log.d("!!!!!!!!!!!!!!!!!!!!!!!",entry.getKey());
            Log.d("!!!!!!!!!!!!!!!!!!!!!!!",entry.getValue().toString());
            // 데이터에 저장된 항목으로 버튼들을 생성함, 현재 text와 id만 가능 수정 필요
            Button myButton = new Button(view.getContext());
            // Component의 내용 설정
            myButton.setId(Integer.parseInt(entry.getKey()));
            myButton.setText(entry.getValue().toString());
            myButton.setLayoutParams(layoutLayoutParams);
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ReminderActivity)getActivity()).onComponentButtonClicked(view, entry.getValue().toString(), Integer.parseInt(entry.getKey()));
                }
            });
            
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.listFragment_listLayout);
            linearLayout.addView(myButton);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(CalreminderData.currentId != -1) {
            String text = CalreminderData.text;
            CalreminderData.text = null;
            Integer id = CalreminderData.currentId;
            CalreminderData.currentId = -1;

            if(CalreminderData.data.getAll().containsKey(id.toString())){
                // 이미 있는 Component를 수정 한 경우
                CalreminderData.data.edit().putString(id.toString(), text).apply();
                Button button = (Button) getActivity().findViewById(id);
                button.setText(text);
            }
            else {
                // 새로운 Component가 추가되는 경우
                CalreminderData.data.edit().putString(id.toString(), text).apply();
                LinearLayout.LayoutParams layoutLayoutParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                Button button = new Button(getContext());
                button.setId(id);
                button.setText(text);
                button.setLayoutParams(layoutLayoutParams);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((ReminderActivity)getActivity()).onComponentButtonClicked(view, text, id);
                    }
                });

                LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.listFragment_listLayout);
                linearLayout.addView(button);
            }
        }
    }
}