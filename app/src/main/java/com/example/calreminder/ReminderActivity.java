package com.example.calreminder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.essam.simpleplacepicker.MapActivity;
import com.essam.simpleplacepicker.utils.SimplePlacePicker;

public class ReminderActivity extends AppCompatActivity{
    //Main Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        //데이터베이스 지정
        CalreminderData.db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-Component").
                allowMainThreadQueries().build();
        CalreminderData.componentDataDao = CalreminderData.db.componentDataDao();

        FragmentManager fm = getSupportFragmentManager();
        if(findViewById(R.id.mainActivity_fragmentContainerView) != null) {
            // 세로 모드일경우
            ReminderList listFragment = (ReminderList) fm.findFragmentByTag("listFragment_portrait");
            if(listFragment == null) {
                listFragment = new ReminderList();
                fm.beginTransaction().add(R.id.mainActivity_fragmentContainerView, listFragment, "listFragment_portrait").commit();
            }
        }
        else {
            // 가로 모드일 경우
            ReminderList listFragment = (ReminderList) fm.findFragmentByTag("listFragment_landScape");
            if(listFragment == null) {
                listFragment = new ReminderList();
                CalendarFragment calendarFragment = new CalendarFragment();
                fm.beginTransaction().add(R.id.reminderList, listFragment, "listFragment_landScape").
                        add(R.id.calendar, calendarFragment,"calendarFragment_landScape").commit();
            }
        }
    }

    public void onAddButtonClicked(View view) {
        //Reminder Fragment에서 +버튼을 눌렀을때 실행되는 코드

        EditComponent editEditComponentFragment = new EditComponent();
        Bundle args = new Bundle();
        editEditComponentFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainActivity_fragmentContainerView, editEditComponentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onComponentButtonClicked(int id) {
        // Reminder Fragment에서 항목을 눌렀을때 실행되는 코드

        EditComponent editEditComponentFragment = new EditComponent();
        Bundle args = new Bundle();
        args.putInt("ID", id);

        editEditComponentFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainActivity_fragmentContainerView, editEditComponentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onCheckClicked(View view) {
        // Calendar Fragment에서 check버튼을 눌렀을 때 실행되는 코드

        ReminderList reminderList = new ReminderList();
        Bundle args = new Bundle();
        reminderList.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainActivity_fragmentContainerView, reminderList);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onPlusClicked(View view, String date) {
        // Calendar Fragment에서 plus버튼을 눌렀을 때 실행되는 코드
        EditComponent editComponent = new EditComponent();
        Bundle args = new Bundle();
        args.putString("Date",date);
        editComponent.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainActivity_fragmentContainerView, editComponent);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onCalendarButtonClicked(View view) {
        // 캘린더 프레그먼트로 이동
        CalendarFragment calendarFragment = new CalendarFragment();
        Bundle args = new Bundle();
        calendarFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainActivity_fragmentContainerView, calendarFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void selectLocation() {
        // 장소 선택 activity를 실행하는 함수
        if (hasPermissionInManifest(ReminderActivity.this,1, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Intent intent = new Intent(this, MapActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString(SimplePlacePicker.API_KEY, getString(R.string.key_google_apis_android));
            bundle.putString(SimplePlacePicker.COUNTRY, "KR");
            bundle.putString(SimplePlacePicker.LANGUAGE, "Korea");
            bundle.putStringArray(SimplePlacePicker.SUPPORTED_AREAS, new String[0]);

            intent.putExtras(bundle);
            startActivityForResult(intent, SimplePlacePicker.SELECT_LOCATION_REQUEST_CODE);
        }
    }

    public static boolean hasPermissionInManifest(Activity activity, int requestCode, String permissionName) {
        // 장소 접근 권한이 있는지 확인하는 함수
        if (ContextCompat.checkSelfPermission(activity,
                permissionName)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(activity,
                    new String[]{permissionName},
                    requestCode);
        } else {
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SimplePlacePicker.SELECT_LOCATION_REQUEST_CODE && resultCode == RESULT_OK){
            if (data != null) {
                // 선택된 장소값이 존재하는 경우 실행
                TextView addressTextView = (TextView) this.findViewById(R.id.editFragment_textView_address);
                addressTextView.setText(data.getStringExtra(SimplePlacePicker.SELECTED_ADDRESS));
            }
        }
    }

    // 빈 공간 터치시 focus가 해제됨
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // 빈공간 터치시 search view와 edit text의 focus 해제
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Log.d("OUT","OUT FOCUS");
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            // 빈 공간 터치시 floacting action button의 애니메이션 동작
            try {
                ReminderList reminderList = (ReminderList) getSupportFragmentManager().findFragmentById(R.id.mainActivity_fragmentContainerView);

                Rect floatingActionButtonRect = new Rect();
                Rect floatingActionButtonAddRect = new Rect();
                Rect floatingActionButtonCalendarRect = new Rect();
                findViewById(R.id.listFragment_floatingActionButton_add).getGlobalVisibleRect(floatingActionButtonAddRect);
                findViewById(R.id.listFragment_floatingActionButton_calendar).getGlobalVisibleRect(floatingActionButtonCalendarRect);
                findViewById(R.id.listFragment_floatingActionButton).getGlobalVisibleRect(floatingActionButtonRect);
                if (reminderList.isButtonClicked &&
                        !floatingActionButtonRect.contains((int) event.getRawX(), (int) event.getY()) &&
                        !floatingActionButtonAddRect.contains((int)event.getRawX(), (int)event.getRawY()) &&
                        !floatingActionButtonCalendarRect.contains((int)event.getRawX(), (int)event.getRawY()))
                {
                    Log.d("OUT", "OUT");
                    reminderList.anim();
                }
            } catch (ClassCastException e) {
                Log.d("EXCEPTION", "ReminderList is not exist");
            }
        }
        return super.dispatchTouchEvent(event);
    }
}