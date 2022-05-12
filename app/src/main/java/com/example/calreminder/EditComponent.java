package com.example.calreminder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TimePicker;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;

public class EditComponent extends Fragment {
    //리마인더 항목을 수정할때 나타나는 Fragment
    public EditComponent() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_component, container, false);

        // 시간 스위치를 눌렀을때 나오는 레이아웃에서 Radio버튼을 누르면 원하는 항목이 표시되도록 만듦
        RadioGroup timeRadioGroup = (RadioGroup) view.findViewById(R.id.editFragment_radioGroupTime);
        timeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                CalendarView calendarView = view.findViewById(R.id.editFragment_calendarView);
                TimePicker timePicker = view.findViewById(R.id.editFragment_timePicker);
                if (i == R.id.editFragment_radioDate){
                    calendarView.setVisibility(View.VISIBLE);
                    timePicker.setVisibility(View.GONE);
                }
                else if (i == R.id.editFragment_radioTime){
                    calendarView.setVisibility(View.GONE);
                    timePicker.setVisibility(View.VISIBLE);
                }
            }
        });

        // 시간 스위치
        Switch timeSwitch = (Switch) view.findViewById(R.id.editFragment_switch_time);
        timeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                LinearLayout timeLayout = (LinearLayout) view.findViewById(R.id.editFragment_timeLayout);
                if(isChecked){
                    timeLayout.setVisibility(View.VISIBLE);
                    RadioGroup timeRadioGroup = (RadioGroup) view.findViewById(R.id.editFragment_radioGroupTime);
                    timeRadioGroup.check(R.id.editFragment_radioDate);
                    RadioButton dateRadioButton = (RadioButton) view.findViewById(R.id.editFragment_radioDate);

                    final Calendar c = Calendar.getInstance();
                    dateRadioButton.setText(c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.DATE));
                }
                else {
                    timeLayout.setVisibility(View.GONE);
                }
            }
        });

        // 날짜를 선택했을때 캘린더 위젯의 작동을 구현
        CalendarView calendarView = (CalendarView) view.findViewById(R.id.editFragment_calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                RadioButton dateRadioButton = (RadioButton) view.findViewById(R.id.editFragment_radioDate);
                String date = Integer.toString(year) + '/' + Integer.toString(month+1) +'/'+ Integer.toString(dayOfMonth);
                dateRadioButton.setText(date);
            }
        });

        // 시간을 선택했을때 타입피커의 작동을 구현
        TimePicker timePicker = (TimePicker) view.findViewById(R.id.editFragment_timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker,  int hourOfDay, int minute) {
                RadioButton timeRadioButton = (RadioButton)view.findViewById(R.id.editFragment_radioTime);
                String time = Integer.toString(hourOfDay)+':'+ Integer.toString(minute);
                timeRadioButton.setText(time);
            }
        });

        // 취소 버튼
        Button cancelButton = (Button) view.findViewById(R.id.editFragment_button_cancle);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((ReminderActivity)getActivity()).getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(EditComponent.this).commit();
                fragmentManager.popBackStack();
            }
        });

        // 저장 버튼
        Button saveButton = (Button) view.findViewById(R.id.editFragment_button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) getActivity().findViewById(R.id.editFragment_editText_content);
                String text = editText.getText().toString();
                Integer id;
                Bundle args = getArguments();
                ArrayList<String> arrayList;
                JSONArray jsonArray;

                if (args.containsKey("ID")) {
                    // 이미 있는 항목을 편집한 경우
                    id = args.getInt("ID");
                    arrayList = CalreminderData.jsonToArrayList(CalreminderData.data.getString(id.toString(),null));
                    // 텍스트 설정
                    arrayList.set(0, editText.getText().toString());
                    // 날짜 설정
                    RadioButton dateRadioButton = ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_radioDate);
                    LinearLayout timeLayout = (LinearLayout) ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_timeLayout);
                    if(timeLayout.getVisibility() == View.VISIBLE){
                        RadioButton timeRadioButton = ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_radioTime);
                        arrayList.set(1, dateRadioButton.getText().toString());
                        if(!((RadioButton)((ReminderActivity)getActivity()).findViewById(R.id.editFragment_radioTime)).getText().equals("시간"))
                            arrayList.set(2,timeRadioButton.getText().toString());
                        else
                            arrayList.set(2,"0");
                    }
                    else {
                        arrayList.set(1,"0");
                        arrayList.set(2,"0");
                    }
                    jsonArray = CalreminderData.ArrayListToJson(arrayList);
                }
                else {
                    // 새로운 항목을 저장한 경우
                    id = CalreminderData.id.getInt("ID", -1);
                    CalreminderData.id.edit().putInt("ID", (CalreminderData.id.getInt("ID",-1)) + 1).apply();
                    jsonArray = new JSONArray();
                    // 텍스트 설정
                    jsonArray.put(editText.getText());
                    // 날짜 설정
                    RadioButton dateRadioButton = ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_radioDate);
                    LinearLayout timeLayout = (LinearLayout) ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_timeLayout);
                    if(timeLayout.getVisibility() == View.VISIBLE){
                        RadioButton timeRadioButton = ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_radioTime);
                        jsonArray.put(dateRadioButton.getText().toString());
                        if(!((RadioButton)((ReminderActivity)getActivity()).findViewById(R.id.editFragment_radioTime)).getText().equals("시간"))
                            jsonArray.put(timeRadioButton.getText().toString());
                        else
                            jsonArray.put("0");
                    }
                    else {
                        jsonArray.put("0");
                        jsonArray.put("0");
                    }
                }
                CalreminderData.data.edit().putString(id.toString(),jsonArray.toString()).apply();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(EditComponent.this).commit();
                fragmentManager.popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args.containsKey("ID")) {
            // 만약 새로 추가 버튼이 아닌 이미 존재하는 Component를 선택할 경우 값을 대입해줌
            
            //삭제버튼 생성
            Button buttonDelete = (Button) getActivity().findViewById(R.id.editFragment_button_delete);
            buttonDelete.setVisibility(View.VISIBLE);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //삭제버튼 클릭시 데이터와 리스트에서 해당하는 항목 삭제
                    Integer id = args.getInt("ID");
                    CalreminderData.data.edit().remove(id.toString()).apply();

                    FragmentManager fragmentManager = ((ReminderActivity)getActivity()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().remove(EditComponent.this).commit();
                    fragmentManager.popBackStack();
                }
            });

            // 기존 데이터 대입
            EditText editText = (EditText) getActivity().findViewById(R.id.editFragment_editText_content);
            ArrayList<String> arrayList = CalreminderData.jsonToArrayList(CalreminderData.data.getString(Integer.toString(args.getInt("ID")),null));
            // 텍스트 대입
            editText.setText(arrayList.get(0));
            // 날짜 대입
            if(!arrayList.get(1).equals("0")) {
                ((Switch)getActivity().findViewById(R.id.editFragment_switch_time)).setChecked(true);
                ((RadioButton)getActivity().findViewById(R.id.editFragment_radioDate)).setText(arrayList.get(1));
                if(!arrayList.get(2).equals("0")){
                    ((RadioButton)getActivity().findViewById(R.id.editFragment_radioTime)).setText(arrayList.get(2));
                }
            }
        }
        else {
            // 새로 추가버튼을 누른경우
            Button buttonDelete = (Button) getActivity().findViewById(R.id.editFragment_button_delete);
            buttonDelete.setVisibility(View.GONE);
        }
    }
}