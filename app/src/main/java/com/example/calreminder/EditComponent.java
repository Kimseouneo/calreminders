package com.example.calreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog;
import com.github.dhaval2404.colorpicker.listener.ColorListener;
import com.google.android.material.card.MaterialCardView;

import java.util.Calendar;
import java.util.StringTokenizer;

public class EditComponent extends Fragment {
    //리마인더 항목을 수정할때 나타나는 Fragment

    private String mText = "";
    private String mDate = "";
    private String mTime = "";
    private String mPlace = "";
    private String mColorHex = "#e0e0e0";
    private Integer mColor = -2039584;
    private Boolean isFromCalendar = false;
    public EditComponent() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_component, container, false);

        // colorPicker
        Button colorPicker = view.findViewById(R.id.editFragment_colorPicker);
        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialColorPickerDialog.Builder(getContext())
                        .setTitle("배경색을 선택해주세요")
                        .setDefaultColor(mColorHex)
                        .setColorListener(new ColorListener() {
                            @Override
                            public void onColorSelected(int color, String colorHex) {
                                mColor = color;
                                mColorHex = colorHex;
                                TextView titleTextView = (TextView) view.findViewById(R.id.editFragment_textView_title);
                                titleTextView.setBackgroundColor(mColor);
                            }
                        })
                        .show();
            }
        });
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
        Calendar calendar = Calendar.getInstance();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                RadioButton dateRadioButton = (RadioButton) view.findViewById(R.id.editFragment_radioDate);
                mDate = Integer.toString(year) + '/' + (month+1) +'/'+ dayOfMonth;
                dateRadioButton.setText(mDate);
            }
        });

        // 시간을 선택했을때 타입피커의 작동을 구현
        TimePicker timePicker = (TimePicker) view.findViewById(R.id.editFragment_timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker,  int hourOfDay, int minute) {
                RadioButton timeRadioButton = (RadioButton)view.findViewById(R.id.editFragment_radioTime);
                if (minute < 10)
                    mTime = Integer.toString(hourOfDay)+':'+ "0" + Integer.toString(minute);
                else
                    mTime = Integer.toString(hourOfDay)+':'+ Integer.toString(minute);
                timeRadioButton.setText(mTime);
            }
        });

        // 장소 스위치
        Switch locationSwitch = (Switch) view.findViewById(R.id.editFragment_switch_location);
        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                LinearLayout timeLayout = (LinearLayout) view.findViewById(R.id.editFragment_locationLayout);
                if(isChecked){
                    timeLayout.setVisibility(View.VISIBLE);
                }
                else {
                    timeLayout.setVisibility(View.GONE);
                }
            }
        });

        // 장소 선택 버튼
        MaterialCardView materialCardViewSelectPlace = (MaterialCardView) view.findViewById(R.id.editFragment_materialCardView_selectPlace);
        materialCardViewSelectPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ReminderActivity)getActivity()).selectLocation();
            }
        });

        // 취소 버튼
        Button cancelButton = (Button) view.findViewById(R.id.editFragment_button_cancle);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getActivity(),"취소되었습니다.",Toast.LENGTH_SHORT);
                toast.show();
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
                Integer id;
                Bundle args = getArguments();
                Component component = new Component();
                if (args.containsKey("ID")) {
                    // 이미 있는 항목을 편집한 경우
                    id = args.getInt("ID");
                    component.Id = id - CalreminderData.baseId;
                    // 텍스트 설정
                    component.text = mText;
                    // 날짜 설정
                    RadioButton dateRadioButton = ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_radioDate);
                    LinearLayout timeLayout = (LinearLayout) ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_timeLayout);
                    if(timeLayout.getVisibility() == View.VISIBLE){
                        mDate = dateRadioButton.getText().toString();
                        component.date = mDate;
                        component.time = mTime;
                    }
                    else {
                        component.date = "";
                        component.time = "";
                        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
                        notificationManagerCompat.cancel(id);
                    }
                    // 장소 설정
                    TextView addressTextView = (TextView) ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_textView_address);
                    LinearLayout locationLayout = (LinearLayout) ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_locationLayout);
                    if (locationLayout.getVisibility() == View.VISIBLE) {
                        mPlace = addressTextView.getText().toString();
                        component.place = mPlace;
                    }
                    else
                        component.place = "";
                    // 배경색 설정
                    component.colorHex = mColorHex;
                    component.color = mColor;
                    CalreminderData.componentDataDao.updateComponent(
                            component.Id,
                            component.text,
                            component.date,
                            component.time,
                            component.place,
                            component.colorHex,
                            component.color);
                }
                else {
                    // 새로운 항목을 저장한 경우

                    // 텍스트 설정
                    component.text = mText;
                    // 날짜 설정
                    RadioButton dateRadioButton = ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_radioDate);
                    LinearLayout timeLayout = (LinearLayout) ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_timeLayout);
                    if (timeLayout.getVisibility() == View.VISIBLE) {
                        mDate = dateRadioButton.getText().toString();
                        component.date = mDate;
                        component.time = mTime;

                    } else {
                        component.date = "";
                        component.time = "";
                    }
                    // 장소 설정
                    TextView addressTextView = (TextView) ((ReminderActivity) getActivity()).findViewById(R.id.editFragment_textView_address);
                    if (addressTextView.getVisibility() == View.VISIBLE){
                        mPlace = addressTextView.getText().toString();
                        component.place = mPlace;
                    }
                    else
                        component.place = "";
                    // 배경색 설정
                    component.colorHex = mColorHex;
                    component.color = mColor;
                    component.isNotified = false;
                    long rowId = CalreminderData.componentDataDao.insertComponent(new ComponentData(component));
                    id = CalreminderData.componentDataDao.getId(rowId);
                }

                Toast toast = Toast.makeText(getActivity(),"저장이 완료되었습니다.",Toast.LENGTH_SHORT);
                toast.show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if(isFromCalendar) {
                    fragmentManager.beginTransaction().remove(EditComponent.this).commit();
                    MonthOfListAdapter.componentArrayList = CalreminderData.componentDataDao.getHasDateComponent();
                    CalendarMonth.adapter.notifyDataSetChanged();
                    fragmentManager.popBackStack();
                }
                else{
                    fragmentManager.beginTransaction().remove(EditComponent.this).commit();
                    fragmentManager.popBackStack();
                }
            }
        });

        // 텍스트 입력 뷰의 입력 설정
        EditText editText = (EditText) view.findViewById(R.id.editFragment_editText_content);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length() == 0)
                    saveButton.setEnabled(false);
                else
                    saveButton.setEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0)
                    saveButton.setEnabled(false);
                else
                    saveButton.setEnabled(true);
            }
        });

        // 기존 데이터 여부에 따라 기존값 대입
        Bundle args = getArguments();
        if (args.containsKey("ID")) {
            // 만약 새로 추가 버튼이 아닌 이미 존재하는 Component를 선택할 경우 값을 대입해줌

            //삭제버튼 생성
            Button buttonDelete = (Button) view.findViewById(R.id.editFragment_button_delete);
            buttonDelete.setVisibility(View.VISIBLE);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //삭제버튼 클릭시 데이터와 리스트에서 해당하는 항목 삭제
                    Integer id = args.getInt("ID");
                    CalreminderData.componentDataDao.deleteComponent(id - CalreminderData.baseId);

                    Toast toast = Toast.makeText(getActivity(),"삭제되었습니다.",Toast.LENGTH_SHORT);
                    toast.show();
                    FragmentManager fragmentManager = ((ReminderActivity)getActivity()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().remove(EditComponent.this).commit();
                    fragmentManager.popBackStack();
                }
            });

            // 기존 데이터 대입
            Integer id = args.getInt("ID");
            Component component = CalreminderData.componentDataDao.getComponentById(id - CalreminderData.baseId);
            // 텍스트 대입
            mText = component.text;
            editText.setText(mText);
            // 날짜 대입
            if(!component.date.equals("")) {
                mDate = component.date;
                String[] date = mDate.split("/");
                calendar.set(Integer.parseInt(date[0]),Integer.parseInt(date[1]) - 1,Integer.parseInt(date[2]));
                calendarView.setDate(calendar.getTimeInMillis());
                ((Switch)view.findViewById(R.id.editFragment_switch_time)).setChecked(true);
                ((RadioButton)view.findViewById(R.id.editFragment_radioDate)).setText(mDate);
                if(!component.time.equals("")){
                    mTime = component.time;
                    String[] time = mTime.split(":");
                    timePicker.setHour(Integer.parseInt(time[0]));
                    timePicker.setMinute(Integer.parseInt(time[1]));
                    ((RadioButton)view.findViewById(R.id.editFragment_radioTime)).setText(mTime);
                }
            }
            // 장소 대입
            if(!component.place.equals("")){
                mPlace = component.place;
                ((Switch)view.findViewById(R.id.editFragment_switch_location)).setChecked(true);
                ((TextView)view.findViewById(R.id.editFragment_textView_address)).setText(mPlace);
            }
            // 배경색 대입
            mColorHex = component.colorHex;
            mColor = component.color;
            TextView titleTextView = (TextView) view.findViewById(R.id.editFragment_textView_title);
            titleTextView.setBackgroundColor(mColor);
        }
        else {
            // 새로 추가버튼을 누른경우
            Button buttonDelete = (Button) view.findViewById(R.id.editFragment_button_delete);
            buttonDelete.setVisibility(View.GONE);
            saveButton.setEnabled(false);

            if (args.containsKey("Date")) {
                mDate = args.getString("Date");
                isFromCalendar = true;
                if (!mDate.equals("")) {
                    ((Switch) view.findViewById(R.id.editFragment_switch_time)).setChecked(true);
                    ((RadioButton) view.findViewById(R.id.editFragment_radioDate)).setText(mDate);
                    String[] date = mDate.split("/");
                    calendar.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));
                    calendarView.setDate(calendar.getTimeInMillis());
                }
            }
            // 배경색 대입
            TextView titleTextView = (TextView) view.findViewById(R.id.editFragment_textView_title);
            titleTextView.setBackgroundColor(mColor);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}