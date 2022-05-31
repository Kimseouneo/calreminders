package com.example.calreminder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import com.essam.simpleplacepicker.MapActivity;
import com.essam.simpleplacepicker.utils.SimplePlacePicker;

import java.util.Calendar;
import java.util.Locale;

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
        ReminderList listFragment = new ReminderList();
        fm.beginTransaction().add(R.id.reminderListFragment, listFragment).commit();
    }

    public void onAddButtonClicked(View view) {
        //Reminder Fragment에서 +버튼을 눌렀을때 실행되는 코드

        EditComponent editEditComponentFragment = new EditComponent();
        Bundle args = new Bundle();
        editEditComponentFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.reminderListFragment, editEditComponentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onComponentButtonClicked(View view, int id) {
        // Reminder Fragment에서 항목을 눌렀을때 실행되는 코드

        EditComponent editEditComponentFragment = new EditComponent();
        Bundle args = new Bundle();
        args.putInt("ID", id);

        editEditComponentFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.reminderListFragment, editEditComponentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onCheckClicked(View view) {
        // Calendar Fragment에서 check버튼을 눌렀을 때 실행되는 코드

        ReminderList reminderList = new ReminderList();
        Bundle args = new Bundle();
        reminderList.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.reminderListFragment, reminderList);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onPlusClicked(View view) {
        // Calendar Fragment에서 plus버튼을 눌렀을 때 실행되는 코드
        EditComponent editComponent = new EditComponent();
        Bundle args = new Bundle();
        editComponent.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.reminderListFragment, editComponent);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onTestButtonClicked(View view) {
        // 테스트용, 실제 구현시 제거
        CalendarFragment calendarFragment = new CalendarFragment(this);
        Bundle args = new Bundle();
        calendarFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.reminderListFragment, calendarFragment);
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
}