package com.example.calreminder;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

        // 리마인더 리스트 만들기
        Map<String, ?> mp = CalreminderData.data.getAll();
        LinearLayout.LayoutParams layoutLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        for(Map.Entry<String,?> entry : mp.entrySet()) {
            // 데이터에 저장된 항목으로 버튼들을 생성함, 현재 text와 id만 가능 수정 필요
            Button myButton = new Button(view.getContext());
            // Component의 내용 설정
            String json = entry.getValue().toString();
            ArrayList<String> arrayList = CalreminderData.jsonToArrayList(json);

            layoutLayoutParams.setMargins(10,10,10,10);
            myButton.setId(Integer.parseInt(entry.getKey()));
            myButton.setText(arrayList.get(0));
            myButton.setLayoutParams(layoutLayoutParams);
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ReminderActivity)getActivity()).onComponentButtonClicked(view, entry.getValue().toString(),
                            Integer.parseInt(entry.getKey()));
                }
            });
            Drawable drawable = getResources().getDrawable(R.drawable.item_background);
            if (!arrayList.get(5).equals(""))
                drawable.setColorFilter(Integer.parseInt(arrayList.get(5)), PorterDuff.Mode.SRC_ATOP);
            myButton.setBackground(drawable);

            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.listFragment_listLayout);
            linearLayout.addView(myButton);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}