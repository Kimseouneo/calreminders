package com.example.calreminder;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.dhaval2404.colorpicker.ColorPickerDialog;
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog;
import com.github.dhaval2404.colorpicker.listener.ColorListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;

public class EditComponent extends Fragment {
    //리마인더 항목을 수정할때 나타나는 Fragment

    private String mText = "";
    private String mDate = "";
    private String mTime = "";
    private String mPlace = "";
    private String mColorHex = "";
    private Integer mColor = 0;

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

        Button colorPicker = view.findViewById(R.id.editFragment_colorPicker);
        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialColorPickerDialog.Builder(getContext())
                        .setDefaultColor(mColorHex)
                        .setColorListener(new ColorListener() {
                            @Override
                            public void onColorSelected(int color, String colorHex) {
                                LinearLayout textLayout = view.findViewById(R.id.editFragment_textLayout);
                                Drawable drawable = getResources().getDrawable(R.drawable.item_background);
                                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                                textLayout.setBackground(drawable);
                                mColor = color;
                                mColorHex = colorHex;
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
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                RadioButton dateRadioButton = (RadioButton) view.findViewById(R.id.editFragment_radioDate);
                mDate = Integer.toString(year) + '/' + Integer.toString(month+1) +'/'+ Integer.toString(dayOfMonth);
                dateRadioButton.setText(mDate);
            }
        });

        // 시간을 선택했을때 타입피커의 작동을 구현
        TimePicker timePicker = (TimePicker) view.findViewById(R.id.editFragment_timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker,  int hourOfDay, int minute) {
                RadioButton timeRadioButton = (RadioButton)view.findViewById(R.id.editFragment_radioTime);
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
        Button selectLocationButton = (Button)view.findViewById(R.id.editFragment_button_selectLocation);
        selectLocationButton.setOnClickListener(new View.OnClickListener() {
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
                    arrayList.set(0, mText);
                    // 날짜 설정
                    RadioButton dateRadioButton = ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_radioDate);
                    LinearLayout timeLayout = (LinearLayout) ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_timeLayout);
                    if(timeLayout.getVisibility() == View.VISIBLE){
                        RadioButton timeRadioButton = ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_radioTime);
                        arrayList.set(1, mDate);
                        arrayList.set(2, mTime);
                    }
                    else {
                        arrayList.set(1,"");
                        arrayList.set(2,"");
                    }
                    // 장소 설정
                    TextView addressTextView = (TextView) ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_textView_address);
                    LinearLayout locationLayout = (LinearLayout) ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_locationLayout);
                    if (locationLayout.getVisibility() == View.VISIBLE) {
                        mPlace = addressTextView.getText().toString();
                        arrayList.set(3, mPlace);
                    }
                    else
                        arrayList.set(3,"");
                    jsonArray = CalreminderData.ArrayListToJson(arrayList);
                }
                else {
                    // 새로운 항목을 저장한 경우
                    id = CalreminderData.id.getInt("ID", -1);
                    CalreminderData.id.edit().putInt("ID", (CalreminderData.id.getInt("ID", -1)) + 1).apply();
                    jsonArray = new JSONArray();
                    // 텍스트 설정
                    jsonArray.put(mText);
                    // 날짜 설정
                    RadioButton dateRadioButton = ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_radioDate);
                    LinearLayout timeLayout = (LinearLayout) ((ReminderActivity)getActivity()).findViewById(R.id.editFragment_timeLayout);
                    if (timeLayout.getVisibility() == View.VISIBLE) {
                        RadioButton timeRadioButton = ((ReminderActivity) getActivity()).findViewById(R.id.editFragment_radioTime);
                        mDate = dateRadioButton.getText().toString();
                        jsonArray.put(mDate);
                        jsonArray.put(mTime);
                    } else {
                        jsonArray.put("");
                        jsonArray.put("");
                    }
                    // 장소 설정
                    TextView addressTextView = (TextView) ((ReminderActivity) getActivity()).findViewById(R.id.editFragment_textView_address);
                    if (addressTextView.getVisibility() == View.VISIBLE){
                        mPlace = addressTextView.getText().toString();
                        jsonArray.put(mPlace);
                    }
                    else
                        jsonArray.put("");
                }
                CalreminderData.data.edit().putString(id.toString(),jsonArray.toString()).apply();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(EditComponent.this).commit();
                fragmentManager.popBackStack();
            }
        });

        // 텍스트 입력 뷰의 입력 설정
        EditText editText = (EditText) view.findViewById(R.id.editFragment_editText_content);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().equals(""))
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
                if(s.toString().equals(""))
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
                    CalreminderData.data.edit().remove(id.toString()).apply();

                    FragmentManager fragmentManager = ((ReminderActivity)getActivity()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().remove(EditComponent.this).commit();
                    fragmentManager.popBackStack();
                }
            });

            // 기존 데이터 대입
            ArrayList<String> arrayList = CalreminderData.jsonToArrayList(CalreminderData.data.getString(Integer.toString(args.getInt("ID")),null));

            // 텍스트 대입
            mText = arrayList.get(0);
            editText.setText(mText);
            // 날짜 대입
            if(!arrayList.get(1).equals("")) {
                mDate = arrayList.get(1);
                ((Switch)view.findViewById(R.id.editFragment_switch_time)).setChecked(true);
                ((RadioButton)view.findViewById(R.id.editFragment_radioDate)).setText(mDate);
                if(!arrayList.get(2).equals("")){
                    mTime = arrayList.get(2);
                    ((RadioButton)view.findViewById(R.id.editFragment_radioTime)).setText(mTime);
                }
            }
            // 장소 대입
            if(!arrayList.get(3).equals("")){
                mPlace = arrayList.get(3);
                ((Switch)view.findViewById(R.id.editFragment_switch_location)).setChecked(true);
                ((TextView)view.findViewById(R.id.editFragment_textView_address)).setText(mPlace);
            }
        }
        else {
            // 새로 추가버튼을 누른경우
            Button buttonDelete = (Button) view.findViewById(R.id.editFragment_button_delete);
            buttonDelete.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}